package com.example.FinTech.dto;

import java.time.LocalDate;
import java.util.Map;

import com.example.FinTech.engine.strategy.entry.EntryStrategyType;
import com.example.FinTech.engine.strategy.exit.ExitStrategyType;

public class SimulationRequest {

    private String symbol;
    private LocalDate startDate;
    private LocalDate endDate;

    private EntryStrategyType entryStrategyType;
    private ExitStrategyType exitStrategyType;
    private Map<String, Double> entryParams;
    private Map<String, Double> exitParams;
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

    public double getInvestmentPerTrade() {
        return investmentPerTrade;
    }

    public void setInvestmentPerTrade(double investmentPerTrade) {
        this.investmentPerTrade = investmentPerTrade;
    }
}
