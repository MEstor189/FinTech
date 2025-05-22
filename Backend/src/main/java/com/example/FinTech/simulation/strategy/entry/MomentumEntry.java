package com.example.FinTech.simulation.strategy.entry;

import java.time.LocalDate;
import java.util.Map;

import com.example.FinTech.persistence.entity.StockData;

public class MomentumEntry implements EntryStrategy {

    private final Map<LocalDate, StockData> historicalData;
    private final int daysInARow;

    public MomentumEntry(Map<LocalDate, StockData> historicalData, int daysInARow) {
        this.historicalData = historicalData;
        this.daysInARow = daysInARow;
    }

    @Override
    public boolean shouldEnter(StockData today) {
        LocalDate currentDate = today.getTradeDate();

        for (int i = 1; i <= daysInARow; i++) {
            LocalDate d1 = currentDate.minusDays(i);
            LocalDate d2 = currentDate.minusDays(i + 1);

            StockData bar1 = historicalData.get(d1);
            StockData bar2 = historicalData.get(d2);

            if (bar1 == null || bar2 == null)
                return false;

            double close1 = bar1.getClosePrice().doubleValue();
            double close2 = bar2.getClosePrice().doubleValue();

            if (close1 <= close2) {
                return false;
            }
        }

        return true;
    }
}
