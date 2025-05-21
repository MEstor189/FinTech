package com.example.FinTech.dto.request;

import java.util.Map;

import com.example.FinTech.engine.strategy.entry.EntryStrategyType;
import com.example.FinTech.engine.strategy.exit.ExitStrategyType;



public class SimulationRequestStrategy {

    private String strategyName;
    private EntryStrategyType entryStrategyType;
    private ExitStrategyType exitStrategyType;
    private Map<String, Double> entryParams;
    private Map<String, Double> exitParams;

    public SimulationRequestStrategy() {}

    public String getStrategyName() { return strategyName; }
    public void setStrategyName(String strategyName) { this.strategyName = strategyName; }

    public EntryStrategyType getEntryStrategyType() { return entryStrategyType; }
    public void setEntryStrategyType(EntryStrategyType entryStrategyType) { this.entryStrategyType = entryStrategyType; }

    public ExitStrategyType getExitStrategyType() { return exitStrategyType; }
    public void setExitStrategyType(ExitStrategyType exitStrategyType) { this.exitStrategyType = exitStrategyType; }

    public Map<String, Double> getEntryParams() { return entryParams; }
    public void setEntryParams(Map<String, Double> entryParams) { this.entryParams = entryParams; }

    public Map<String, Double> getExitParams() { return exitParams; }
    public void setExitParams(Map<String, Double> exitParams) { this.exitParams = exitParams; }
}

