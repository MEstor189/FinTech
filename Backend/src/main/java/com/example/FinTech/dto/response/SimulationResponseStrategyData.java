package com.example.FinTech.dto.response;

import java.util.List;
import java.util.Map;

import com.example.FinTech.simulation.model.Trade;
import com.example.FinTech.simulation.strategy.entry.EntryStrategyType;
import com.example.FinTech.simulation.strategy.exit.ExitStrategyType;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}
