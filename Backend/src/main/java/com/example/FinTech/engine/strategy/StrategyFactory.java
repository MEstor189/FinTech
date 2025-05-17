package com.example.FinTech.engine.strategy;

import com.example.FinTech.alpaca.entity.StockData;
import com.example.FinTech.engine.model.Portfolio;

import java.time.LocalDate;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StrategyFactory {
    private static final Logger logger = LoggerFactory.getLogger(StrategyFactory.class);

    public static Strategy createStrategy(
            StrategyType type,
            Map<LocalDate, StockData> historicalData,
            Map<String, Double> params
    ) {
        switch (type) {
            case BUY_THE_DIP:
                double dip = params.getOrDefault("dipThreshold", 3.0);
                double profit = params.getOrDefault("profitTarget", 5.0);
                logger.info("Creating strategy: {} with params {}", type, params);
                return new BuyTheDip(historicalData, dip, profit);

            case MOMENTUM:
                throw new UnsupportedOperationException("MOMENTUM strategy not implemented yet");

            case MOVING_AVERAGE:
                throw new UnsupportedOperationException("MOVING_AVERAGE strategy not implemented yet");

            default:
                logger.error("Unknown strategy type requested: {}", type);
                throw new IllegalArgumentException("Unknown strategy type: " + type);
        }
    }
}
