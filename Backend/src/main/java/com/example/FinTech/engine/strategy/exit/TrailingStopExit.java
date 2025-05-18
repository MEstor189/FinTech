package com.example.FinTech.engine.strategy.exit;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.FinTech.alpaca.entity.StockData;
import com.example.FinTech.engine.model.Portfolio;
import com.example.FinTech.engine.model.Position;

public class TrailingStopExit implements ExitStrategy {

    private final static Logger logger = LoggerFactory.getLogger(TrailingStopExit.class);

    private final double trailingStopPercent;

    public TrailingStopExit(double trailingStopPercent) {
        this.trailingStopPercent = trailingStopPercent / 100.0;
    }

    @Override
    public boolean shouldExit(StockData data, Portfolio portfolio) {
        for (Position position : portfolio.getOpenPositions()) {

            position.updateMaxPrice(data.getClosePrice().doubleValue());
            Optional<Double> maxPriceOpt = position.getMaxPriceSinceEntry();

            if (maxPriceOpt.isPresent()) {
                double maxPrice = maxPriceOpt.get();
                double stopPrice = maxPrice * (1 - trailingStopPercent);

                if (data.getClosePrice().doubleValue() <= stopPrice) {
                    logger.info("Exit because closePrice: {} <= stopPrice {} ", data.getClosePrice(),stopPrice);
                    return true; 
                }
            }
        }
        return false;
    }
}
