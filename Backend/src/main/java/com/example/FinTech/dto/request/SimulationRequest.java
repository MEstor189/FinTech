package com.example.FinTech.dto.request;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimulationRequest {

    private String symbol;
    private LocalDate startDate;
    private LocalDate endDate;
    private double investmentPerTrade;
    private List<SimulationRequestStrategy> requestStrategies;

    public SimulationRequest() {}
}
