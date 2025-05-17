package com.example.FinTech.engine.strategy.entry;

import com.example.FinTech.alpaca.entity.StockData;

public interface EntryStrategy {
    
    boolean shouldEnter(StockData data);
}
