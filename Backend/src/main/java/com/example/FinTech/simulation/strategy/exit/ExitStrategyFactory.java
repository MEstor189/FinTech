package com.example.FinTech.simulation.strategy.exit;

import java.time.LocalDate;
import java.util.Map;

import com.example.FinTech.persistence.entity.StockData;

public class ExitStrategyFactory {

    public static ExitStrategy createExitStrategy(
            ExitStrategyType exitStrategyType,
            Map<LocalDate, StockData> historicalData,
            Map<String, Double> params) {
        switch (exitStrategyType) {
            case TARGET_PROFIT:
                double profitTarget = params.getOrDefault("profitTarget", 5.0);
                return new TargetProfitExit(profitTarget);

            case TRAILING_STOP:
                double trailingStopPercent = params.getOrDefault("trailingStopPercent", 5.0);
                return new TrailingStopExit(trailingStopPercent);
            case MOVING_AVERAGE_CROSS:
                int shortPeriod = params.getOrDefault("shortPeriod", 3.0).intValue();
                int longPeriod = params.getOrDefault("longPeriod", 10.0).intValue();
                return new MovingAverageCrossExit(shortPeriod, longPeriod, historicalData);

            default:
                throw new IllegalArgumentException("Unsupported Exit Strategy: " + exitStrategyType);
        }
    }
}
