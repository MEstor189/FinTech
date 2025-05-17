package com.example.FinTech.dto;

import com.example.FinTech.engine.model.Trade;

import java.util.List;

public class SimulationResponse {

    private List<Trade> trades;
    private double totalProfit;
    private double averageHoldingDays;
    private int tradeCount;

    public SimulationResponse() {
    }

    public SimulationResponse(List<Trade> trades, double totalProfit, double averageHoldingDays, int tradeCount) {
        this.trades = trades;
        this.totalProfit = totalProfit;
        this.averageHoldingDays = averageHoldingDays;
        this.tradeCount = tradeCount;
    }

    // Getter & Setter
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
}
