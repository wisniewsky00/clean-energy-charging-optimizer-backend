package com.github.wisniewsky00.clean_energy_charging_optimizer.service;

import com.github.wisniewsky00.clean_energy_charging_optimizer.client.GenerationMixResponse;
import com.github.wisniewsky00.clean_energy_charging_optimizer.client.GenerationMixSlot;
import com.github.wisniewsky00.clean_energy_charging_optimizer.client.NesoClient;
import com.github.wisniewsky00.clean_energy_charging_optimizer.dto.EnergyMixSummary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class ChargingOptimizationService {

    private final NesoClient nesoClient;

    public ChargingOptimizationService(NesoClient nesoClient) {
        this.nesoClient = nesoClient;
    }

    public EnergyMixSummary calculateOptimizedChargingWindow(int chargingHoursLength) {

        if (chargingHoursLength < 1 || chargingHoursLength > 6) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "chargingHoursLength must be between 1 and 6"
            );
        }

        int windowSlots = chargingHoursLength * 2;

        OffsetDateTime start = LocalDate.now(ZoneOffset.UTC)
                .plusDays(1)
                .atStartOfDay()
                .atOffset(ZoneOffset.UTC);

        OffsetDateTime end = start.plusDays(2);

        GenerationMixResponse response =
                nesoClient.fetchGenerationMixBetweenDates(start.plusMinutes(30).toString(), end.toString());
        List<GenerationMixSlot> generationMixSlots = response.getData();

        List<GenerationMixSlot> slots = response.getData();

        if (slots.size() < windowSlots) {
            throw new IllegalStateException("Not enough forecast data to calculate optimized charging window");
        }

        EnergyMixSummary bestSummary = new EnergyMixSummary();

        for (int i = 0; i <= slots.size() - windowSlots; i++) {

            EnergyMixAggregator aggregator = new EnergyMixAggregator(
                    slots.get(i).getFrom(),
                    slots.get(i + windowSlots - 1).getTo()
            );

            for (int j = i; j < i + windowSlots; j++) {
                aggregator.addSlot(slots.get(j));
            }

            EnergyMixSummary summary = aggregator.toSummary();

            if (summary.getAvgCleanEnergy() > bestSummary.getAvgCleanEnergy()) {
                bestSummary = summary;
            }
        }

        return bestSummary;
    }
}