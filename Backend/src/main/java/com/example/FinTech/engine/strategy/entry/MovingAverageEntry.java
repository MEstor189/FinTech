package com.example.FinTech.engine.strategy.entry;

import java.time.LocalDate;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.FinTech.alpaca.entity.StockData;

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

        Double shortAvgToday = calculateAverage(currentDate, shortPeriod);
        Double longAvgToday = calculateAverage(currentDate, longPeriod);
        Double shortAvgYesterday = calculateAverage(yesterday, shortPeriod);
        Double longAvgYesterday = calculateAverage(yesterday, longPeriod);

        if (shortAvgToday == null || longAvgToday == null || shortAvgYesterday == null || longAvgYesterday == null) {
            return false;
        }
        logger.info("DAY:    {}     short: {} long: {} shorttoday: {} longtoday{}", currentDate, shortAvgYesterday,
                longAvgYesterday, shortAvgToday, longAvgToday);
        return shortAvgYesterday <= longAvgYesterday && shortAvgToday > longAvgToday;
    }

private Double calculateAverage(LocalDate endDate, int period) {
    return historicalData.entrySet().stream()
        .filter(e -> !e.getKey().isAfter(endDate))
        .sorted((e1, e2) -> e2.getKey().compareTo(e1.getKey())) // rückwärts sortiert
        .limit(period)
        .mapToDouble(e -> e.getValue().getClosePrice().doubleValue())
        .average()
        .orElse(Double.NaN);
}

}
