package com.example.FinTech.util;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.FinTech.persistence.entity.StockData;

public class MovingAverageUtil {
    
    public static Double calculateAverage(LocalDate endDate, int period, Map<LocalDate, StockData> data) {
        List<Double> values = data.entrySet().stream()
            .filter(e -> !e.getKey().isAfter(endDate))
            .sorted(Map.Entry.comparingByKey()) // Älteste zuerst (für Klarheit, optional)
            .map(e -> e.getValue().getClosePrice().doubleValue())
            .collect(Collectors.toList());

        if (values.size() < period) {
            return null; // ❗️Nicht genug Daten vorhanden
        }

        // Die letzten X Werte nehmen
        List<Double> lastN = values.subList(values.size() - period, values.size());
        return lastN.stream().mapToDouble(Double::doubleValue).average().orElseThrow();
    }

    public static LocalDate getPreviousTradingDay(LocalDate current, Map<LocalDate, StockData> data) {
        return data.keySet().stream()
                .filter(d -> d.isBefore(current))
                .sorted()
                .reduce((a, b) -> b)
                .orElse(null);
    }
}
