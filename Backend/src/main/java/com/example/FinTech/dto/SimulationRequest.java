package com.example.FinTech.dto;

import com.example.FinTech.engine.strategy.StrategyType;

import java.time.LocalDate;
import java.util.Map;

public class SimulationRequest {

    private String symbol;
    private LocalDate startDate;
    private LocalDate endDate;

    private StrategyType strategyType;
    private Map<String, Double> params;  
    private double investmentPerTrade;

    public SimulationRequest() {
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public StrategyType getStrategyType() {
        return strategyType;
    }

    public void setStrategyType(StrategyType strategyType) {
        this.strategyType = strategyType;
    }

    public Map<String, Double> getParams() {
        return params;
    }

    public void setParams(Map<String, Double> params) {
        this.params = params;
    }

    public double getInvestmentPerTrade() {
        return investmentPerTrade;
    }

    public void setInvestmentPerTrade(double investmentPerTrade) {
        this.investmentPerTrade = investmentPerTrade;
    }
}
