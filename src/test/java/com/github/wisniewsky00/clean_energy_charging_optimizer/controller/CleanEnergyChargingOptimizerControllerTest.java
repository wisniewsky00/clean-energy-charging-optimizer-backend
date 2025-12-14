package com.github.wisniewsky00.clean_energy_charging_optimizer.controller;

import com.github.wisniewsky00.clean_energy_charging_optimizer.dto.EnergyMixSummary;
import com.github.wisniewsky00.clean_energy_charging_optimizer.service.ChargingOptimizationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CleanEnergyChargingOptimizerController.class)
public class CleanEnergyChargingOptimizerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ChargingOptimizationService chargingOptimizationService;

    @Test
    void shouldReturnOptimizedChargingWindow() throws Exception {
        EnergyMixSummary result = new EnergyMixSummary(
                OffsetDateTime.parse("2025-12-14T00:00Z"),
                OffsetDateTime.parse("2025-12-14T03:00Z"),
                20, 0, 10, 15, 0, 5, 0, 50, 0,
                75.0
        );

        Mockito.when(chargingOptimizationService.calculateOptimizedChargingWindow(3))
                .thenReturn(result);

        mockMvc.perform(get("/api/optimizer/clean/energy/optimize/window")
                    .param("chargingHoursLength", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.from").value("2025-12-14T00:00:00Z"))
                .andExpect(jsonPath("$.to").value("2025-12-14T03:00:00Z"))
                .andExpect(jsonPath("$.avgCleanEnergy").value(75.0));
    }

    @Test
    void shouldReturnBadRequestWhenMissingParameter() throws Exception {
        mockMvc.perform(get("/api/optimizer/clean/energy/optimize/window"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestForInvalidHours() throws Exception {
        Mockito.when(chargingOptimizationService.calculateOptimizedChargingWindow(0))
                .thenThrow(new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "chargingHoursLength must be between 1 and 6"
                ));

        mockMvc.perform(get("/api/optimizer/clean/energy/optimize/window")
                        .param("chargingHoursLength", "0"))
                .andExpect(status().isBadRequest());
    }
}
