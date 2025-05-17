package com.example.FinTech.engine.strategy.exit;

import com.example.FinTech.alpaca.entity.StockData;
import com.example.FinTech.engine.model.Portfolio;

public interface ExitStrategy {
    
    boolean shouldExit(StockData data, Portfolio portfolio);
}
