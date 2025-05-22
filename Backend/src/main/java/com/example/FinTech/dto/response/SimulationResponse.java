package com.example.FinTech.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.example.FinTech.dto.stock.StockDataDTO;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonPropertyOrder({
    "symbol",
    "startDate",
    "endDate",
    "investmentPerTrade",
    "strategies",
    "stockData"
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SimulationResponse {

    private String symbol;
    private LocalDate startDate;
    private LocalDate endDate;
    private List <SimulationResponseStrategyData> strategies;
    private double investmentPerTrade;
    private List <StockDataDTO> stockData;

}
