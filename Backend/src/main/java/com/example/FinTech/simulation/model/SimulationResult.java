package com.example.FinTech.simulation.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimulationResult {

    private final List<Trade> trades;
    private final double totalProfit;
    private final double averageHoldingDays;
    private final int tradeCount;

    public SimulationResult(List<Trade> trades) {
        this.trades = trades;
        this.tradeCount = trades.size();
        this.totalProfit = calculateTotalProfit(trades);
        this.averageHoldingDays = calculateAverageHoldingDays(trades);
    }

    private double calculateTotalProfit(List<Trade> trades) {
        return trades.stream()
                .mapToDouble(Trade::getProfitAbsolute)
                .sum();
    }

    private double calculateAverageHoldingDays(List<Trade> trades) {
        if (trades.isEmpty()) return 0;
        return trades.stream()
                .mapToLong(Trade::getHoldingDays)
                .average()
                .orElse(0.0);
    }
}

