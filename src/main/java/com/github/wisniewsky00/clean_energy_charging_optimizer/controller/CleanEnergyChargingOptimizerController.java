package com.github.wisniewsky00.clean_energy_charging_optimizer.controller;

import com.github.wisniewsky00.clean_energy_charging_optimizer.dto.EnergyMixSummary;
import com.github.wisniewsky00.clean_energy_charging_optimizer.service.ChargingOptimizationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("api/optimizer")
public class CleanEnergyChargingOptimizerController {

    private final ChargingOptimizationService chargingOptimizationService;

    public CleanEnergyChargingOptimizerController(ChargingOptimizationService chargingOptimizationService) {
        this.chargingOptimizationService = chargingOptimizationService;
    }

    @GetMapping("/clean/energy/optimize/window")
    public EnergyMixSummary getOptimizedChargingWindow(@RequestParam(value="chargingHoursLength") int chargingHoursLength) {
        return chargingOptimizationService.calculateOptimizedChargingWindow(chargingHoursLength);
    }
}
