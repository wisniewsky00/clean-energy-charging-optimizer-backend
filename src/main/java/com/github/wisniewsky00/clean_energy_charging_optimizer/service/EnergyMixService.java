package com.github.wisniewsky00.clean_energy_charging_optimizer.service;

import com.github.wisniewsky00.clean_energy_charging_optimizer.client.GenerationMixResponse;
import com.github.wisniewsky00.clean_energy_charging_optimizer.client.NesoClient;
import com.github.wisniewsky00.clean_energy_charging_optimizer.dto.EnergyMixSummary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class EnergyMixService {

    private final NesoClient nesoClient;

    public EnergyMixService(NesoClient nesoClient) {
        this.nesoClient = nesoClient;
    }

    public List<EnergyMixSummary> calculateEnergyMixSummary(int days) {

        OffsetDateTime startingDate = getStartingDate();
        List<EnergyMixSummary> dailyEnergyMixSummaryList = new ArrayList<>();

        for (int i = 1; i <= days; i++) {
            OffsetDateTime endingDate = getEndingDate(startingDate);
            GenerationMixResponse response = nesoClient.fetchGenerationMixBetweenDates(startingDate.plusMinutes(30).toString(), endingDate.toString());

            EnergyMixAggregator energyMixAggregator = new EnergyMixAggregator();
            response.getData().forEach(generationMixSlot -> {
                energyMixAggregator.addSlot(generationMixSlot);
            });

            dailyEnergyMixSummaryList.add(energyMixAggregator.toSummary(startingDate, endingDate));

            startingDate = startingDate.plusDays(1);
        }

        return dailyEnergyMixSummaryList;
    }

    private OffsetDateTime getStartingDate() {
        OffsetDateTime startOfDayUtc = LocalDate.now(ZoneOffset.UTC).atStartOfDay().atOffset(ZoneOffset.UTC);
        startOfDayUtc.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'"));
        return startOfDayUtc;
    }

    private OffsetDateTime getEndingDate(OffsetDateTime startOfDayUtc) {
        return startOfDayUtc.plusDays(1);
    }
}