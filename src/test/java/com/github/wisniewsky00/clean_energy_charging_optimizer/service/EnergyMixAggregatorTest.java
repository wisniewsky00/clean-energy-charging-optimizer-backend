package com.github.wisniewsky00.clean_energy_charging_optimizer.service;

import com.github.wisniewsky00.clean_energy_charging_optimizer.client.GenerationMixEntry;
import com.github.wisniewsky00.clean_energy_charging_optimizer.client.GenerationMixSlot;
import com.github.wisniewsky00.clean_energy_charging_optimizer.dto.EnergyMixSummary;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class EnergyMixAggregatorTest {

    @Test
    void shouldCalculateAveragesAndCleanEnergyCorrectly() {

        OffsetDateTime from = OffsetDateTime.parse("2025-12-14T00:00Z");
        OffsetDateTime to = OffsetDateTime.parse("2025-12-14T01:00Z");

        EnergyMixAggregator aggregator = new EnergyMixAggregator(from, to);

        GenerationMixSlot slot1 = new GenerationMixSlot(
                List.of(
                        new GenerationMixEntry("wind", 60),
                        new GenerationMixEntry("gas", 40)
                )
        );
        GenerationMixSlot slot2 = new GenerationMixSlot(
                List.of(
                        new GenerationMixEntry("wind", 40),
                        new GenerationMixEntry("gas", 60)
                )
        );

        aggregator.addSlot(slot1);
        aggregator.addSlot(slot2);
        EnergyMixSummary summary = aggregator.toSummary();

        assertThat(summary.getFrom()).isEqualTo(from);
        assertThat(summary.getTo()).isEqualTo(to);

        assertThat(summary.getAvgWind()).isEqualTo(50.0);
        assertThat(summary.getAvgGas()).isEqualTo(50.0);

        assertThat(summary.getAvgCleanEnergy()).isEqualTo(50.0);
    }

    @Test void shouldReturnEmptySummaryWhenNoSlotsAdded() {
        EnergyMixAggregator aggregator = new EnergyMixAggregator();
        EnergyMixSummary summary = aggregator.toSummary();

        assertThat(summary.getAvgWind()).isEqualTo(0.0);
        assertThat(summary.getAvgGas()).isEqualTo(0.0);
        assertThat(summary.getAvgBiomass()).isEqualTo(0.0);
        assertThat(summary.getAvgCoal()).isEqualTo(0.0);
        assertThat(summary.getAvgImports()).isEqualTo(0.0);
        assertThat(summary.getAvgNuclear()).isEqualTo(0.0);
        assertThat(summary.getAvgOther()).isEqualTo(0.0);
        assertThat(summary.getAvgSolar()).isEqualTo(0.0);
        assertThat(summary.getAvgHydro()).isEqualTo(0.0);
        assertThat(summary.getAvgCleanEnergy()).isEqualTo(0.0);
    }
}
