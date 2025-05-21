package com.example.FinTech.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.example.FinTech.dto.stockDataDto.StockDataDTO;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
    "symbol",
    "startDate",
    "endDate",
    "investmentPerTrade",
    "strategies",
    "stockData"
})
public class SimulationResponse {

    private String symbol;
    private LocalDate startDate;
    private LocalDate endDate;
    private List <SimulationResponseStrategyData> strategies;
    private double investmentPerTrade;
    private List <StockDataDTO> stockData;



    public SimulationResponse() {
    }

    public SimulationResponse(List<SimulationResponseStrategyData> strategies, LocalDate startDate, LocalDate enDate, String symbol, double investmentPerTrade, List<StockDataDTO> stockdata) {
        this.symbol=symbol;
        this.endDate=enDate;
        this.startDate=startDate;
        this.strategies=strategies;  
        this.investmentPerTrade=investmentPerTrade;
        this.stockData=stockdata;
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

    public List<SimulationResponseStrategyData> getStrategies() {
        return strategies;
    }

    public void setStrategies(List<SimulationResponseStrategyData> strategies) {
        this.strategies = strategies;
    }

    public double getInvestmentPerTrade() {
        return investmentPerTrade;
    }

    public void setInvestmentPerTrade(double investmentPerTrade) {
        this.investmentPerTrade = investmentPerTrade;
    }

    public List<StockDataDTO> getStockData() {
        return stockData;
    }

    public void setStockData(List<StockDataDTO> stockData) {
        this.stockData = stockData;
    }


}
