package com.example.FinTech.engine.strategy;

import com.example.FinTech.alpaca.entity.StockData;
import com.example.FinTech.engine.model.Portfolio;

public interface Strategy {

    boolean shouldEnter(StockData data);

    boolean shouldExit(StockData data, Portfolio portfolio);
}
