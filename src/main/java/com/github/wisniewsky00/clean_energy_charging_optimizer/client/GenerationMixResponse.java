package com.github.wisniewsky00.clean_energy_charging_optimizer.client;

import java.util.List;

public class GenerationMixResponse {
    private List<GenerationMixSlot> data;

    public GenerationMixResponse() {
    }

    public List<GenerationMixSlot> getData() {
        return data;
    }

    public void setData(List<GenerationMixSlot> data) {
        this.data = data;
    }
}
