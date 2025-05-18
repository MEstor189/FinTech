package com.example.FinTech.engine.strategy.exit;

import com.example.FinTech.alpaca.entity.StockData;

import java.time.LocalDate;
import java.util.Map;

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

            default:
                throw new IllegalArgumentException("Unsupported Exit Strategy: " + exitStrategyType);
        }
    }
}
