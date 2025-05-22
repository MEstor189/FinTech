package com.example.FinTech.dto.request;

import java.util.Map;

import com.example.FinTech.simulation.strategy.entry.EntryStrategyType;
import com.example.FinTech.simulation.strategy.exit.ExitStrategyType;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SimulationRequestStrategy {

    private String strategyName;
    private EntryStrategyType entryStrategyType;
    private ExitStrategyType exitStrategyType;
    private Map<String, Double> entryParams;
    private Map<String, Double> exitParams;

    public SimulationRequestStrategy() {}
    
}

