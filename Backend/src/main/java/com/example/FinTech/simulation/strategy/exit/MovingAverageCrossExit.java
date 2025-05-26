package com.example.FinTech.simulation.strategy.exit;

import java.time.LocalDate;
import java.util.Map;

import com.example.FinTech.persistence.entity.StockData;
import com.example.FinTech.simulation.model.Portfolio;
import com.example.FinTech.util.MovingAverageUtil;

public class MovingAverageCrossExit implements ExitStrategy {

    private final int shortPeriod;
    private final int longPeriod;
    private final Map<LocalDate, StockData> historicalData;

    public MovingAverageCrossExit(int shortPeriod, int longPeriod, Map<LocalDate, StockData> historicalData) {
        this.shortPeriod = shortPeriod;
        this.longPeriod = longPeriod;
        this.historicalData = historicalData;
    }

@Override
public boolean shouldExit(StockData data, Portfolio portfolio) {
    LocalDate currentDate = data.getTradeDate();
    LocalDate yesterday = MovingAverageUtil.getPreviousTradingDay(currentDate, historicalData);

    if (yesterday == null) return false;

    Double shortMA_today = MovingAverageUtil.calculateAverage(currentDate, shortPeriod, historicalData);
    Double longMA_today = MovingAverageUtil.calculateAverage(currentDate, longPeriod, historicalData);
    Double shortMA_yesterday = MovingAverageUtil.calculateAverage(yesterday, shortPeriod, historicalData);
    Double longMA_yesterday = MovingAverageUtil.calculateAverage(yesterday, longPeriod, historicalData);

    System.out.printf("📢 Intern: shortY=%.2f | longY=%.2f | shortT=%.2f | longT=%.2f%n",
        shortMA_yesterday, longMA_yesterday, shortMA_today, longMA_today);
    System.out.printf("📢 Vergleich: %b && %b → %b%n",
        shortMA_yesterday >= longMA_yesterday,
        shortMA_today < longMA_today,
        shortMA_yesterday >= longMA_yesterday && shortMA_today < longMA_today);

    if (shortMA_today == null || longMA_today == null || shortMA_yesterday == null || longMA_yesterday == null) {
        return false;
    }

    return shortMA_yesterday >= longMA_yesterday && shortMA_today < longMA_today;
}
}
