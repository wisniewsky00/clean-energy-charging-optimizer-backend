package com.github.wisniewsky00.clean_energy_charging_optimizer.dto;

import java.time.OffsetDateTime;

public class EnergyMixSummary {
    private OffsetDateTime from;
    private OffsetDateTime to;

    private double avgGas;
    private double avgCoal;
    private double avgBiomass;
    private double avgNuclear;
    private double avgHydro;
    private double avgImports;
    private double avgOther;
    private double avgWind;
    private double avgSolar;

    private double avgCleanEnergy;

    public EnergyMixSummary() {
    }

    public EnergyMixSummary(OffsetDateTime from, OffsetDateTime to, double avgGas, double avgCoal, double avgBiomass, double avgNuclear, double avgHydro, double avgImports, double avgOther, double avgWind, double avgSolar, double avgCleanEnergy) {
        this.from = from;
        this.to = to;
        this.avgGas = avgGas;
        this.avgCoal = avgCoal;
        this.avgBiomass = avgBiomass;
        this.avgNuclear = avgNuclear;
        this.avgHydro = avgHydro;
        this.avgImports = avgImports;
        this.avgOther = avgOther;
        this.avgWind = avgWind;
        this.avgSolar = avgSolar;
        this.avgCleanEnergy = avgCleanEnergy;
    }

    public OffsetDateTime getFrom() {
        return from;
    }

    public void setFrom(OffsetDateTime from) {
        this.from = from;
    }

    public OffsetDateTime getTo() {
        return to;
    }

    public void setTo(OffsetDateTime to) {
        this.to = to;
    }

    public double getAvgGas() {
        return avgGas;
    }

    public void setAvgGas(double avgGas) {
        this.avgGas = avgGas;
    }

    public double getAvgCoal() {
        return avgCoal;
    }

    public void setAvgCoal(double avgCoal) {
        this.avgCoal = avgCoal;
    }

    public double getAvgBiomass() {
        return avgBiomass;
    }

    public void setAvgBiomass(double avgBiomass) {
        this.avgBiomass = avgBiomass;
    }

    public double getAvgNuclear() {
        return avgNuclear;
    }

    public void setAvgNuclear(double avgNuclear) {
        this.avgNuclear = avgNuclear;
    }

    public double getAvgHydro() {
        return avgHydro;
    }

    public void setAvgHydro(double avgHydro) {
        this.avgHydro = avgHydro;
    }

    public double getAvgImports() {
        return avgImports;
    }

    public void setAvgImports(double avgImports) {
        this.avgImports = avgImports;
    }

    public double getAvgOther() {
        return avgOther;
    }

    public void setAvgOther(double avgOther) {
        this.avgOther = avgOther;
    }

    public double getAvgWind() {
        return avgWind;
    }

    public void setAvgWind(double avgWind) {
        this.avgWind = avgWind;
    }

    public double getAvgSolar() {
        return avgSolar;
    }

    public void setAvgSolar(double avgSolar) {
        this.avgSolar = avgSolar;
    }

    public double getAvgCleanEnergy() {
        return avgCleanEnergy;
    }

    public void setAvgCleanEnergy(double avgCleanEnergy) {
        this.avgCleanEnergy = avgCleanEnergy;
    }

    @Override
    public String toString() {
        return "EnergyMixSummary{" +
                "from=" + from +
                ", to=" + to +
                ", avgGas=" + avgGas +
                ", avgCoal=" + avgCoal +
                ", avgBiomass=" + avgBiomass +
                ", avgNuclear=" + avgNuclear +
                ", avgHydro=" + avgHydro +
                ", avgImports=" + avgImports +
                ", avgOther=" + avgOther +
                ", avgWind=" + avgWind +
                ", avgSolar=" + avgSolar +
                ", avgCleanEnergy=" + avgCleanEnergy +
                '}';
    }
}
