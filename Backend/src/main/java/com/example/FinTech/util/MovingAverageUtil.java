package com.example.FinTech.util;

import java.time.LocalDate;
import java.util.Map;

import com.example.FinTech.persistence.entity.StockData;

public class MovingAverageUtil {
public static double calculateAverage(LocalDate endDate, int period, Map<LocalDate, StockData> data) {
    return data.entrySet().stream()
        .filter(e -> !e.getKey().isAfter(endDate))
        .sorted((e1, e2) -> e2.getKey().compareTo(e1.getKey())) // Neueste zuerst
        .limit(period)
        .mapToDouble(e -> e.getValue().getClosePrice().doubleValue())
        .average()
        .orElse(0.0);
}

    public static LocalDate getPreviousTradingDay(LocalDate current, Map<LocalDate, StockData> data) {
        return data.keySet().stream()
                .filter(d -> d.isBefore(current))
                .sorted()
                .reduce((a, b) -> b)
                .orElse(null);
    }
}
