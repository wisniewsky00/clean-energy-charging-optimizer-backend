package com.github.wisniewsky00.clean_energy_charging_optimizer.controller;

import com.github.wisniewsky00.clean_energy_charging_optimizer.dto.EnergyMixSummary;
import com.github.wisniewsky00.clean_energy_charging_optimizer.service.EnergyMixService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnergyMixController.class)
public class EnergyMixControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EnergyMixService energyMixService;

    @Test
    void shouldReturnEnergyMixSummaryList() throws Exception {
        Mockito.when(energyMixService.calculateEnergyMixSummary(3))
                .thenReturn(List.of(new EnergyMixSummary(sampleSummary())));

        mockMvc.perform(get("/api/energy/mix/summary")
                    .param("days", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].avgCleanEnergy").value(85.0));
    }

    private EnergyMixSummary sampleSummary() {
        return new EnergyMixSummary(
                OffsetDateTime.parse("2025-12-14T00:00Z"),
                OffsetDateTime.parse("2025-12-15T00:00Z"),
                10, 0, 20, 15, 0, 5, 0, 40, 10, 85
        );
    }

    @Test
    void shouldUseDefaultDaysWhenParameterNotProvided() throws Exception {
        Mockito.when(energyMixService.calculateEnergyMixSummary(3))
                .thenReturn(List.of(sampleSummary()));

        mockMvc.perform(get("/api/energy/mix/summary"))
                .andExpect(status().isOk());

        Mockito.verify(energyMixService).calculateEnergyMixSummary(3);
    }

    @Test
    void shouldReturnCorrectJsonStructure() throws Exception {
        Mockito.when(energyMixService.calculateEnergyMixSummary(3))
                .thenReturn(List.of(sampleSummary()));

        mockMvc.perform(get("/api/energy/mix/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].from").exists())
                .andExpect(jsonPath("$[0].to").exists())
                .andExpect(jsonPath("$[0].avgGas").exists())
                .andExpect(jsonPath("$[0].avgWind").exists())
                .andExpect(jsonPath("$[0].avgCoal").exists())
                .andExpect(jsonPath("$[0].avgHydro").exists())
                .andExpect(jsonPath("$[0].avgImports").exists())
                .andExpect(jsonPath("$[0].avgOther").exists())
                .andExpect(jsonPath("$[0].avgSolar").exists())
                .andExpect(jsonPath("$[0].avgBiomass").exists())
                .andExpect(jsonPath("$[0].avgNuclear").exists())
                .andExpect(jsonPath("$[0].avgCleanEnergy").exists());
    }
}
