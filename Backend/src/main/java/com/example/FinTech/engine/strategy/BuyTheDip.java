package com.example.FinTech.engine.strategy;

import com.example.FinTech.engine.model.Portfolio;
import com.example.FinTech.alpaca.entity.StockData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BuyTheDip implements Strategy {

    private static final Logger logger = LoggerFactory.getLogger(BuyTheDip.class);

    private final Map<LocalDate, StockData> historicalData;
    private final double dipThresholdPercent; // z. B. 3.0 = -3%
    private final double profitTargetPercent; // z. B. 5.0 = +5%

    public BuyTheDip(Map<LocalDate, StockData> historicalData,
            double dipThresholdPercent,
            double profitTargetPercent) {
        this.historicalData = historicalData;
        this.dipThresholdPercent = dipThresholdPercent;
        this.profitTargetPercent = profitTargetPercent;
    }

    @Override
    public boolean shouldEnter(StockData today) {
        LocalDate yesterday = today.getTradeDate().minusDays(1);
        StockData previous = historicalData.get(yesterday);

        if (previous == null)
            return false;

        BigDecimal yesterdayClose = previous.getClosePrice();
        BigDecimal todayClose = today.getClosePrice();

        if (yesterdayClose.compareTo(BigDecimal.ZERO) <= 0)
            return false;

        double changePercent = (yesterdayClose.subtract(todayClose))
                .divide(yesterdayClose, 10, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100)).doubleValue();

        if (changePercent >= dipThresholdPercent) {
            logger.debug("Buy signal: {} dropped {:.2f}% on {}", today.getSymbol(), changePercent, today.getTradeDate());
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean shouldExit(StockData today, Portfolio portfolio) {
        return portfolio.getOpenPositions().stream().anyMatch(pos -> {
            if (!pos.getSymbol().equals(today.getSymbol()))
                return false;

            double entry = pos.getEntryPrice();
            double current = today.getClosePrice().doubleValue();
            double change = ((current - entry) / entry) * 100.0;
            logger.debug("Sell signal: {} reached {:.2f}% profit on {}", pos.getSymbol(), change, today.getTradeDate());

            return change >= profitTargetPercent;
        });
    }
}