package com.github.wisniewsky00.clean_energy_charging_optimizer.service;

import com.github.wisniewsky00.clean_energy_charging_optimizer.client.GenerationMixEntry;
import com.github.wisniewsky00.clean_energy_charging_optimizer.client.GenerationMixSlot;
import com.github.wisniewsky00.clean_energy_charging_optimizer.dto.EnergyMixSummary;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EnergyMixAggregator {

    Map<String, Double> sums;
    int slotCount;

    public EnergyMixAggregator() {
        this.sums = new HashMap<>();
        slotCount = 0;
    }

    public void addSlot(GenerationMixSlot generationMixSlot) {

        generationMixSlot.getGenerationmix().forEach(this::addEntry);
        slotCount++;
    }

    private void addEntry(GenerationMixEntry generationMixEntry) {
        sums.merge(generationMixEntry.getFuel().toLowerCase(), generationMixEntry.getPerc(), Double::sum);
    }

    public EnergyMixSummary toSummary(OffsetDateTime start, OffsetDateTime end) {

        if (slotCount == 0) {
            return new EnergyMixSummary();
        }

        double avgGas = round(sums.getOrDefault("gas", 0.0) / slotCount);
        double avgCoal =  round(sums.getOrDefault("coal", 0.0) / slotCount);
        double avgBiomass = round(sums.getOrDefault("biomass", 0.0) / slotCount);
        double avgNuclear = round(sums.getOrDefault("nuclear", 0.0) / slotCount);
        double avgHydro =  round(sums.getOrDefault("hydro", 0.0) / slotCount);
        double avgImports  = round(sums.getOrDefault("imports", 0.0) / slotCount);
        double avgOther = round(sums.getOrDefault("other", 0.0) / slotCount);
        double avgWind = round(sums.getOrDefault("wind", 0.0) / slotCount);
        double avgSolar = round(sums.getOrDefault("solar", 0.0) / slotCount);

        return new EnergyMixSummary(
                start,
                end,
                avgGas,
                avgCoal,
                avgBiomass,
                avgNuclear,
                avgHydro,
                avgImports,
                avgOther,
                avgWind,
                avgSolar,
                round(calculateCleanEnergyPercent()));
    }

    private double round(double value) {
        return Math.round(value * 10.0) / 10.0;
    }

    private double calculateCleanEnergyPercent() {
        List<String> cleanEnergies =  List.of("biomass", "nuclear", "hydro", "wind", "solar");
        Double totalEnergySum = 0.0;
        Double cleanEnergySum = 0.0;

        for (Map.Entry<String, Double> entry : sums.entrySet()) {
            if (cleanEnergies.contains(entry.getKey())) {
                cleanEnergySum +=  entry.getValue();
            }
            totalEnergySum += entry.getValue();
        }

        return (cleanEnergySum / totalEnergySum) * 100;
    }
}
