package com.github.wisniewsky00.clean_energy_charging_optimizer.service;

import com.github.wisniewsky00.clean_energy_charging_optimizer.client.NesoClient;
import com.github.wisniewsky00.clean_energy_charging_optimizer.dto.EnergyMixSummary;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class ChargingOptimizationService {

    private final NesoClient nesoClient;

    public ChargingOptimizationService(NesoClient nesoClient) {
        this.nesoClient = nesoClient;
    }

    public EnergyMixSummary calculateOptimizedChargingWindow(int chargingHoursLength) {

        int totalHours = 48;
        int totalSlots = totalHours * 2;
        int windowSlots = chargingHoursLength * 2;
        int windows = totalSlots - windowSlots + 1;

        EnergyMixSummary bestEnergyMixSummary = new EnergyMixSummary();
        EnergyMixSummary energyMixSummary;
        OffsetDateTime actualDate = ;
        EnergyMixAggregator energyMixAggregator = new EnergyMixAggregator(actualDate, actualDate.plusHours(chargingHoursLength));

        for (int i = 1; i <= windows; i++) {

            energyMixAggregator.addSlot(nesoClient.fetchGenerationMixBetweenDates(actualDate, actualDate.plusMinutes(30)).getData());
            if (i % (windowSlots) == 0) {
                energyMixSummary = energyMixAggregator.toSummary();

                if (energyMixSummary.getAvgCleanEnergy() > bestEnergyMixSummary.getAvgCleanEnergy()) {
                    bestEnergyMixSummary = energyMixSummary;
                }
                energyMixAggregator = new EnergyMixAggregator(actualDate, actualDate.plusHours(chargingHoursLength));
            }

            actualDate = actualDate.plusMinutes(30);
        }

        return bestEnergyMixSummary;
    }
}