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
        LocalDate currentdate = data.getTradeDate();
        LocalDate yesterday = MovingAverageUtil.getPreviousTradingDay(currentdate, historicalData);

        if (yesterday == null) return false;

        double shortMA_today = MovingAverageUtil.calculateAverage(currentdate, shortPeriod, historicalData);
        double longMA_today = MovingAverageUtil.calculateAverage(currentdate, longPeriod, historicalData);
        double shortMA_yesterday = MovingAverageUtil.calculateAverage(yesterday, shortPeriod, historicalData);
        double longMA_yesterday = MovingAverageUtil.calculateAverage(yesterday, longPeriod, historicalData);

        return shortMA_yesterday >= longMA_yesterday && shortMA_today < longMA_today;
    }
}
