package com.example.FinTech.dto.response;

import java.util.List;
import java.util.Map;

import com.example.FinTech.engine.model.Trade;
import com.example.FinTech.engine.strategy.entry.EntryStrategyType;
import com.example.FinTech.engine.strategy.exit.ExitStrategyType;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
    "strategyName",
    "entryStrategyType",
    "exitStrategyType",
    "entryParams",
    "exitParams",
    "tradeCount",
    "totalProfit",
    "averageHoldingDays",
    "trades"
})
public class SimulationResponseStrategyData {

    private String strategyName;
    private EntryStrategyType entryStrategyType;
    private ExitStrategyType exitStrategyType;
    private Map<String, Double> entryParams;
    private Map<String, Double> exitParams;
    private List<Trade> trades;
    private double totalProfit;
    private double averageHoldingDays;
    private int tradeCount;

    public SimulationResponseStrategyData   (String sName, List<Trade> trades, double totalProfit, double averageHoldingDays, int tradeCount, 
                                            EntryStrategyType entryStrategyType, ExitStrategyType exitStrategyType,Map<String, Double> entryParams,
                                            Map<String, Double> exitParams) {
        this.strategyName=sName;
        this.trades = trades;
        this.totalProfit = totalProfit;
        this.averageHoldingDays = averageHoldingDays;
        this.tradeCount = tradeCount;
        this.entryStrategyType=entryStrategyType;
        this.exitStrategyType=exitStrategyType;
        this.entryParams=entryParams;
        this.exitParams=exitParams;

    }


    public List<Trade> getTrades() {
        return trades;
    }

    public void setTrades(List<Trade> trades) {
        this.trades = trades;
    }

    public double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(double totalProfit) {
        this.totalProfit = totalProfit;
    }

    public double getAverageHoldingDays() {
        return averageHoldingDays;
    }

    public void setAverageHoldingDays(double averageHoldingDays) {
        this.averageHoldingDays = averageHoldingDays;
    }

    public int getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(int tradeCount) {
        this.tradeCount = tradeCount;
    }

    public String getStrategyName() {
        return strategyName;
    }


    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }


    public EntryStrategyType getEntryStrategyType() {
        return entryStrategyType;
    }

    public void setEntryStrategyType(EntryStrategyType entryStrategyType) {
        this.entryStrategyType = entryStrategyType;
    }

    public ExitStrategyType getExitStrategyType() {
        return exitStrategyType;
    }

    public void setExitStrategyType(ExitStrategyType exitStrategyType) {
        this.exitStrategyType = exitStrategyType;
    }

    public Map<String, Double> getEntryParams() {
        return entryParams;
    }

    public void setEntryParams(Map<String, Double> entryParams) {
        this.entryParams = entryParams;
    }

    public Map<String, Double> getExitParams() {
        return exitParams;
    }

    public void setExitParams(Map<String, Double> exitParams) {
        this.exitParams = exitParams;
    }

}
