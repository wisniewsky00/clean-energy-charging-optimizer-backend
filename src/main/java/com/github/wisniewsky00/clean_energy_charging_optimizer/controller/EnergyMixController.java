package com.github.wisniewsky00.clean_energy_charging_optimizer.controller;

import com.github.wisniewsky00.clean_energy_charging_optimizer.dto.EnergyMixSummary;
import com.github.wisniewsky00.clean_energy_charging_optimizer.service.EnergyMixService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/energy/mix")
public class EnergyMixController {

    private final EnergyMixService energyMixService;

    public EnergyMixController(EnergyMixService energyMixService) {
        this.energyMixService = energyMixService;
    }

    @GetMapping("/summary")
    public List<EnergyMixSummary> getDailyEnergyMixSummaries(@RequestParam(value = "days", defaultValue = "3") int days) {
        return energyMixService.calculateEnergyMixSummary(days);
    }
}
