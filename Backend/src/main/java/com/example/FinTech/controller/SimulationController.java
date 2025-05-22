package com.example.FinTech.controller;


import com.example.FinTech.dto.request.SimulationRequest;
import com.example.FinTech.dto.request.SimulationRequestStrategy;
import com.example.FinTech.dto.response.SimulationResponse;
import com.example.FinTech.dto.response.SimulationResponseStrategyData;
import com.example.FinTech.service.SimulationService;
import com.example.FinTech.simulation.model.SimulationResult;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/simulation")
public class SimulationController {

    private static final Logger logger = LoggerFactory.getLogger(SimulationController.class);

    private final SimulationService simulationService;

    public SimulationController(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    @PostMapping
    public SimulationResponse runSimulation(@RequestBody SimulationRequest request) {
         logger.info("POST /api/simulation called for symbol {} with Strategys {} ", request.getSymbol(),
                request.getRequestStrategies()); 
        
        List<SimulationResponseStrategyData> responseDatas = new ArrayList<>();
        
        for (SimulationRequestStrategy sRequestStrategy : request.getRequestStrategies()) {
            SimulationResult result = simulationService.runSimulation(sRequestStrategy, request.getSymbol(), request.getStartDate(), request.getEndDate(), request.getInvestmentPerTrade());
            SimulationResponseStrategyData strategy= new SimulationResponseStrategyData(
                sRequestStrategy.getStrategyName(),
                sRequestStrategy.getEntryStrategyType(),
                sRequestStrategy.getExitStrategyType(),
                sRequestStrategy.getEntryParams(),
                sRequestStrategy.getExitParams(),
                result.getTrades(),
                result.getTotalProfit(),
                result.getAverageHoldingDays(),
                result.getTradeCount()
                );
            logger.info("Strategy Name: {}",strategy.getStrategyName());
            responseDatas.add(strategy);     
        }
   
        return new SimulationResponse(
            request.getSymbol(),
            request.getStartDate(),
            request.getEndDate(),
            responseDatas,
            request.getInvestmentPerTrade(),
            simulationService.getStockDataDtoForSymbol(request.getSymbol(), request.getStartDate(), request.getEndDate())
        );

    }

}
