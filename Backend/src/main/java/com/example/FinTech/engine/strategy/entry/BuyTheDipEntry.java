package com.example.FinTech.engine.strategy.entry;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.FinTech.alpaca.entity.StockData;

public class BuyTheDipEntry implements EntryStrategy{

        private static final Logger logger = LoggerFactory.getLogger(BuyTheDipEntry.class);

    private final Map<LocalDate, StockData> historicalData;
    private final double dipThresholdPercent; // z. B. 3.0 = -3%
    public BuyTheDipEntry(Map<LocalDate, StockData> historicalData, double dipThresholdPercent) {
        this.historicalData = historicalData;
        this.dipThresholdPercent = dipThresholdPercent;
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
}
