package com.github.wisniewsky00.clean_energy_charging_optimizer.client;

public class GenerationMixEntry {
    private String fuel;
    private double perc;

    public GenerationMixEntry() {
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public double getPerc() {
        return perc;
    }

    public void setPerc(double perc) {
        this.perc = perc;
    }
}
