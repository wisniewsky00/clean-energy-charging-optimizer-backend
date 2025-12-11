package com.github.wisniewsky00.clean_energy_charging_optimizer.service;

import com.github.wisniewsky00.clean_energy_charging_optimizer.dto.DailyEnergyMixSummary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnergyMixService {

    public List<DailyEnergyMixSummary> calculateEnergyMixSummary(int days) {
        return List.copyOf(null);
    }
}
