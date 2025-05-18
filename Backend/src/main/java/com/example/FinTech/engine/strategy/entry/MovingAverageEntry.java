package com.example.FinTech.engine.strategy.entry;

import java.time.LocalDate;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.FinTech.alpaca.entity.StockData;
import com.example.FinTech.engine.strategy.util.MovingAverageUtil;

public class MovingAverageEntry implements EntryStrategy {

    private static final Logger logger = LoggerFactory.getLogger(MovingAverageEntry.class);

    private final Map<LocalDate, StockData> historicalData;
    private final int shortPeriod;
    private final int longPeriod;

    public MovingAverageEntry(Map<LocalDate, StockData> historicalData, int shortPeriod, int longPeriod) {
        this.historicalData = historicalData;
        this.shortPeriod = shortPeriod;
        this.longPeriod = longPeriod;
    }

    @Override
    public boolean shouldEnter(StockData today) {
        LocalDate currentDate = today.getTradeDate();

        LocalDate yesterday = currentDate.minusDays(1);

        double shortAvgToday = MovingAverageUtil.calculateAverage(currentDate, shortPeriod, historicalData);
        double longAvgToday = MovingAverageUtil.calculateAverage(currentDate, longPeriod, historicalData);
        double shortAvgYesterday = MovingAverageUtil.calculateAverage(yesterday, shortPeriod, historicalData);
        double longAvgYesterday = MovingAverageUtil.calculateAverage(yesterday, longPeriod, historicalData);

/*         if (shortAvgToday == null || longAvgToday == null || shortAvgYesterday == null || longAvgYesterday == null) {
            return false;
        } */

        logger.info("DAY: {} | shortY: {} longY: {} | shortT: {} longT: {}", 
                    currentDate, shortAvgYesterday, longAvgYesterday, shortAvgToday, longAvgToday);

        return shortAvgYesterday <= longAvgYesterday && shortAvgToday > longAvgToday;
    }

}
