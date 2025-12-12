package com.github.wisniewsky00.clean_energy_charging_optimizer.client;

import java.util.List;

public class GenerationMixSlot {
    private String from;
    private String to;
    private List<GenerationMixEntry> generationmix;

    public GenerationMixSlot() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<GenerationMixEntry> getGenerationmix() {
        return generationmix;
    }

    public void setGenerationmix(List<GenerationMixEntry> generationmix) {
        this.generationmix = generationmix;
    }
}
