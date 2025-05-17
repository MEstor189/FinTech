package com.example.FinTech.service;

import com.example.FinTech.alpaca.entity.StockData;
import com.example.FinTech.alpaca.repository.StockDataRepository;
import com.example.FinTech.dto.SimulationRequest;
import com.example.FinTech.engine.SimulationEngine;
import com.example.FinTech.engine.model.SimulationResult;
import com.example.FinTech.engine.strategy.entry.EntryStrategy;
import com.example.FinTech.engine.strategy.entry.EntryStrategyFactory;
import com.example.FinTech.engine.strategy.exit.ExitStrategy;
import com.example.FinTech.engine.strategy.exit.ExitStrategyFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class SimulationService {

    private static final Logger logger = LoggerFactory.getLogger(SimulationService.class);

    private final StockDataRepository stockDataRepository;

    public SimulationService(StockDataRepository stockDataRepository) {
        this.stockDataRepository = stockDataRepository;
    }

    public SimulationResult runSimulation(SimulationRequest request) {
        logger.info("Starting simulation for symbol {} using EntryStrategy {} and ExitStrategy {}", request.getSymbol(),
                request.getEntryStrategyType(),request.getExitStrategyType());

        // 1. get data from DB
        List<StockData> dataList = stockDataRepository.findAllBySymbolAndTradeDateBetweenOrderByTradeDateAsc(
                request.getSymbol(),
                request.getStartDate(),
                request.getEndDate());

        if (dataList.isEmpty()) {
            logger.warn("No stock data found for {} between {} and {}", request.getSymbol(), request.getStartDate(),
                    request.getEndDate());
            throw new IllegalArgumentException("No data available for the selected period and symbol.");
        }

        // 2. Data in Map<LocalDate, StockData>
        Map<LocalDate, StockData> historicalData = new HashMap<>();
        for (StockData data : dataList) {
            historicalData.put(data.getTradeDate(), data);
        }

        // 3. create Entrystrategie w enum & Factory
        EntryStrategy entryStrategy = EntryStrategyFactory.createEntryStrategy(
                request.getEntryStrategyType(),
                historicalData,
                request.getEntryParams() != null ? request.getEntryParams() : new HashMap<>());

        ExitStrategy exitStrategy = ExitStrategyFactory.createExitStrategy(
                request.getExitStrategyType(),
                historicalData,
                request.getExitParams() != null ? request.getExitParams() : new HashMap<>());

        // 4. SimulationEngine start
        SimulationEngine engine = new SimulationEngine(entryStrategy, exitStrategy, request.getInvestmentPerTrade());
        SimulationResult result = engine.runSimulation(dataList);

        logger.info("Simulation completed. Trades executed: {}", result.getTradeCount());
        return result;
    }
}