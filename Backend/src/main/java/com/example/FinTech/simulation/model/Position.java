package com.example.FinTech.simulation.model;

import java.time.LocalDate;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Position {
    private String symbol;
    private double entryPrice;
    private double quantity;
    private LocalDate entryDate;

    private String strategyName;
    private double maxPriceSinceEntry;
    private String positionId;

    public Position(String symbol, double entryPrice, double quantity, LocalDate entryDate, String strategyName) {
        this.symbol = symbol;
        this.entryPrice = entryPrice;
        this.quantity = quantity;
        this.entryDate = entryDate;
        this.strategyName = strategyName;
        this.positionId = generatePositionId();
        this.maxPriceSinceEntry=entryPrice;
    }

    private String generatePositionId() {
        return symbol + "-" + entryDate.toString() + "-" + strategyName;
    }

    public void updateMaxPrice(double currentPrice) {
        maxPriceSinceEntry = Math.max(maxPriceSinceEntry, currentPrice);
    }

    public Optional<Double> getMaxPriceSinceEntry() {
        return Optional.of(maxPriceSinceEntry);
    }

    public double getTotalInvested() {
        return entryPrice * quantity;
    }
}
