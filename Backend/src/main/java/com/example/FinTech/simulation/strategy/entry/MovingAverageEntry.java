package com.example.FinTech.simulation.strategy.entry;

import java.time.LocalDate;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.FinTech.persistence.entity.StockData;
import com.example.FinTech.util.MovingAverageUtil;

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

        Double shortAvgToday = MovingAverageUtil.calculateAverage(currentDate, shortPeriod, historicalData);
        Double longAvgToday = MovingAverageUtil.calculateAverage(currentDate, longPeriod, historicalData);
        Double shortAvgYesterday = MovingAverageUtil.calculateAverage(yesterday, shortPeriod, historicalData);
        Double longAvgYesterday = MovingAverageUtil.calculateAverage(yesterday, longPeriod, historicalData);

        if (shortAvgToday == null || longAvgToday == null || shortAvgYesterday == null || longAvgYesterday == null) {
            logger.warn("MA not computable – missing historical data");
            return false;
        }

        logger.info("📊 MA DEBUG | {} | shortY: {} | longY: {} | shortT: {} | longT: {}",
                currentDate, shortAvgYesterday, longAvgYesterday, shortAvgToday, longAvgToday);
        System.out.println("🧮 Vergleich:");
        System.out.println("shortY <= longY: " + (shortAvgYesterday <= longAvgYesterday));
        System.out.println("shortT > longT: " + (shortAvgToday > longAvgToday));

        return shortAvgYesterday <= longAvgYesterday && shortAvgToday > longAvgToday;
    }

}
