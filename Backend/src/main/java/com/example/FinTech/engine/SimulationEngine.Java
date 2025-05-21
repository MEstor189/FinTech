package com.example.FinTech.engine;

import com.example.FinTech.alpaca.entity.StockData;
import com.example.FinTech.engine.model.*;
import com.example.FinTech.engine.strategy.entry.EntryStrategy;
import com.example.FinTech.engine.strategy.exit.ExitStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SimulationEngine {

    private static final Logger logger = LoggerFactory.getLogger(SimulationEngine.class);

    private final EntryStrategy entryStrategy;
    private final ExitStrategy exitStrategy;
    private final double investmentPerTrade;

    private final Portfolio portfolio = new Portfolio();
    private final List<Trade> tradeHistory = new ArrayList<>();

    public SimulationEngine(EntryStrategy entryStrategy, ExitStrategy exitStrategy, double investmentPerTrade) {
        this.entryStrategy = entryStrategy;
        this.exitStrategy=exitStrategy;
        this.investmentPerTrade = investmentPerTrade;
    }

    public SimulationResult runSimulation(List<StockData> stockDataList) {
        logger.info("Simulation started using EntryStrategy: {} and ExitStrategy {}", entryStrategy.getClass().getSimpleName(), exitStrategy.getClass().getSimpleName());

        for (StockData stockdata : stockDataList) {
            checkAndExecuteEntry(stockdata);
            checkAndExecuteExit(stockdata);
        }

        if (!portfolio.isEmpty()) {
            logger.info("Simulation ended with open positions → Closing all remaining positions");
            LocalDate endDate = stockDataList.get(stockDataList.size() - 1).getTradeDate();
            double finalPrice = stockDataList.get(stockDataList.size() - 1).getClosePrice().doubleValue();
            closeRemainingPositions(endDate, finalPrice);
        }

        logger.info("Simulation finished. Total trades executed: {}", tradeHistory.size());
        return new SimulationResult(tradeHistory);
    }

    private void checkAndExecuteEntry(StockData stockdata) {
        LocalDate date = stockdata.getTradeDate();
        String symbol = stockdata.getSymbol();
        double currentPrice = stockdata.getClosePrice().doubleValue();

        if (entryStrategy.shouldEnter(stockdata)) {
            double quantity = investmentPerTrade / currentPrice;
            Position position = new Position(
                    symbol, currentPrice, quantity, date, entryStrategy.getClass().getSimpleName()
            );
            portfolio.addPosition(position);
            logger.info("Entry signal triggered → Opened position: {} at ${} ({} shares)", symbol, currentPrice, quantity);
        }
    }

    private void checkAndExecuteExit(StockData stockdata) {
        if (exitStrategy.shouldExit(stockdata, portfolio)) {
            logger.info("Exit signal triggered → Closing all open positions");
            LocalDate date = stockdata.getTradeDate();
            double currentPrice = stockdata.getClosePrice().doubleValue();

            for (Position pos : portfolio.getOpenPositions()) {
                Trade trade = createTradeFromPosition(pos, currentPrice, date);
                tradeHistory.add(trade);
                logTrade(trade);
            }
            portfolio.clearPositions();
        }
    }

    private void closeRemainingPositions(LocalDate exitDate, double currentPrice) {
        for (Position pos : portfolio.getOpenPositions()) {
            Trade trade = createTradeFromPosition(pos, currentPrice, exitDate);
            tradeHistory.add(trade);
            logTrade(trade);
        }
        portfolio.clearPositions();
    }

    private Trade createTradeFromPosition(Position pos, double exitPrice, LocalDate exitDate) {
        return new Trade(
                pos.getSymbol(),
                pos.getEntryPrice(),
                exitPrice,
                pos.getQuantity(),
                pos.getEntryDate(),
                exitDate,
                pos.getStrategyName()
        );
    }

    private void logTrade(Trade trade) {
        logger.info("Trade closed: {} | Entry: ${} | Exit: ${} | Profit: {}%",
                trade.getSymbol(),
                trade.getEntryPrice(),
                trade.getExitPrice(),
                String.format("%.2f", trade.getProfitPercent()));
    }
}
