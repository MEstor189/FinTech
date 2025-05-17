package com.example.FinTech.engine.strategy.entry;

import com.example.FinTech.alpaca.entity.StockData;

import java.time.LocalDate;
import java.util.Map;

public class EntryStrategyFactory {

    public static EntryStrategy createEntryStrategy(
            EntryStrategyType type,
            Map<LocalDate, StockData> historicalData,
            Map<String, Double> params
    ) {
        switch (type) {
            case BUY_THE_DIP:
                double dipThreshold = params.getOrDefault("dipThreshold", 3.0);
                return new BuyTheDipEntry(historicalData, dipThreshold);

            case MOMENTUM:
                int days = params.getOrDefault("daysInARow", 3.0).intValue();
                return new MomentumEntry(historicalData, days);

            case MOVING_AVERAGE:
                int shortPeriod = params.getOrDefault("shortPeriod", 5.0).intValue();
                int longPeriod = params.getOrDefault("longPeriod", 20.0).intValue();
                return new MovingAverageEntry(historicalData, shortPeriod,longPeriod);

            default:
                throw new IllegalArgumentException("Unsupported Entry Strategy: " + type);
        }
    }
}
