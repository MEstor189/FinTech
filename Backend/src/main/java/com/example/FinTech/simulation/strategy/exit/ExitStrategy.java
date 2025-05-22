package com.example.FinTech.simulation.strategy.exit;

import com.example.FinTech.persistence.entity.StockData;
import com.example.FinTech.simulation.model.Portfolio;

public interface ExitStrategy {
    
    boolean shouldExit(StockData data, Portfolio portfolio);
}
