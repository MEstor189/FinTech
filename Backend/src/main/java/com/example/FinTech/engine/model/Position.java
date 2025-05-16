package com.example.FinTech.engine.model;

import java.time.LocalDate;

public class Position {
    private String symbol;
    private double entryPrice;      
    private double quantity;           
    private LocalDate entryDate;       


    private String strategyName;

    private String positionId;

    public Position(String symbol, double entryPrice, double quantity, LocalDate entryDate, String strategyName) {
        this.symbol = symbol;
        this.entryPrice = entryPrice;
        this.quantity = quantity;
        this.entryDate = entryDate;
        this.strategyName = strategyName;
        this.positionId = generatePositionId();
    }

    private String generatePositionId() {
        return symbol + "-" + entryDate.toString() + "-" + strategyName;
    }

    // Getter & Setter
    public String getSymbol() {
        return symbol;
    }

    public double getEntryPrice() {
        return entryPrice;
    }

    public double getQuantity() {
        return quantity;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public String getPositionId() {
        return positionId;
    }

    // Optional: Hilfsmethoden
    public double getTotalInvested() {
        return entryPrice * quantity;
    }
}
