package com.example.FinTech.simulation.strategy.entry;

import com.example.FinTech.persistence.entity.StockData;

public interface EntryStrategy {
    
    boolean shouldEnter(StockData data);
}
