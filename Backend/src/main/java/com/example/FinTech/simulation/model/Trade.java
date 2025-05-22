package com.example.FinTech.simulation.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Trade {
    private String symbol;
    private double entryPrice;
    private double exitPrice;
    private double quantity;
    private LocalDate entryDate;
    private LocalDate exitDate;
    private String strategyName;

    public Trade(String symbol, double entryPrice, double exitPrice, double quantity,
                 LocalDate entryDate, LocalDate exitDate, String strategyName) {
        this.symbol = symbol;
        this.entryPrice = entryPrice;
        this.exitPrice = exitPrice;
        this.quantity = quantity;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
        this.strategyName = strategyName;
    }

    public double getProfitAbsolute() {
        return (exitPrice - entryPrice) * quantity;
    }

    public double getProfitPercent() {
        return ((exitPrice - entryPrice) / entryPrice) * 100.0;
    }

    public long getHoldingDays() {
        return java.time.temporal.ChronoUnit.DAYS.between(entryDate, exitDate);
    }

}
