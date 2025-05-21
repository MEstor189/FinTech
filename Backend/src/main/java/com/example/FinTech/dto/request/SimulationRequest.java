package com.example.FinTech.dto.request;

import java.time.LocalDate;
import java.util.List;

public class SimulationRequest {

    private String symbol;
    private LocalDate startDate;
    private LocalDate endDate;
    private double investmentPerTrade;
    private List<SimulationRequestStrategy> requestStrategies;

    public SimulationRequest() {}

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public double getInvestmentPerTrade() { return investmentPerTrade; }
    public void setInvestmentPerTrade(double investmentPerTrade) { this.investmentPerTrade = investmentPerTrade; }

    public List<SimulationRequestStrategy> getRequestStrategies() { return requestStrategies; }
    public void setRequestStrategies(List<SimulationRequestStrategy> requestStrategies) { this.requestStrategies = requestStrategies; }
}
