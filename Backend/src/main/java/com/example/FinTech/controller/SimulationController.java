package com.example.FinTech.controller;

import com.example.FinTech.dto.SimulationRequest;
import com.example.FinTech.dto.SimulationResponse;
import com.example.FinTech.engine.model.SimulationResult;
import com.example.FinTech.service.SimulationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

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
        logger.info("POST /api/simulation called for symbol {} with EntryStrategy {} and ExitStrategy {}", request.getSymbol(),
                request.getEntryStrategyType(),request.getExitStrategyType());

        SimulationResult result = simulationService.runSimulation(request);

        return new SimulationResponse(
                result.getTrades(),
                result.getTotalProfit(),
                result.getAverageHoldingDays(),
                result.getTradeCount());
    }

}
