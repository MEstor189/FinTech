package com.example.FinTech.engine.model;

import java.time.LocalDate;

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

    // Getter
    public String getSymbol() {
        return symbol;
    }

    public double getEntryPrice() {
        return entryPrice;
    }

    public double getExitPrice() {
        return exitPrice;
    }

    public double getQuantity() {
        return quantity;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public LocalDate getExitDate() {
        return exitDate;
    }

    public String getStrategyName() {
        return strategyName;
    }

    // Hilfsmethoden
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
