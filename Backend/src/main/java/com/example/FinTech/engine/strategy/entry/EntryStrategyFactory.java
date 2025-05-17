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

            // case MOMENTUM:
            //     return new MomentumEntryStrategy(...);

            default:
                throw new IllegalArgumentException("Unsupported Entry Strategy: " + type);
        }
    }
}
