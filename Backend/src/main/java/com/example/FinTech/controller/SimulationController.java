package com.example.FinTech.controller;

import com.example.FinTech.dto.request.SimulationRequest;
import com.example.FinTech.dto.request.SimulationRequestStrategy;
import com.example.FinTech.dto.response.SimulationResponse;
import com.example.FinTech.dto.response.SimulationResponseStrategyData;
import com.example.FinTech.service.SimulationService;
import com.example.FinTech.simulation.model.SimulationResult;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
    public ResponseEntity<?> runSimulation(@Valid @RequestBody SimulationRequest request, BindingResult result) {
        logger.info("POST /api/simulation called for symbol {} with Strategys {} ", request.getSymbol(),
                request.getRequestStrategies());
        if (request.getStartDate() != null && request.getEndDate() != null &&
                request.getStartDate().isAfter(request.getEndDate())) {

            result.rejectValue("startDate", "invalid.range", "Startdatum darf nicht nach dem Enddatum liegen");
        }

        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError err : result.getFieldErrors()) {
                errors.put(err.getField(), err.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        List<SimulationResponseStrategyData> responseDatas = new ArrayList<>();

        for (SimulationRequestStrategy sRequestStrategy : request.getRequestStrategies()) {
            SimulationResult sResult = simulationService.runSimulation(sRequestStrategy, request.getSymbol(),
                    request.getStartDate(), request.getEndDate(), request.getInvestmentPerTrade());
            SimulationResponseStrategyData strategy = new SimulationResponseStrategyData(
                    sRequestStrategy.getStrategyName(),
                    sRequestStrategy.getEntryStrategyType(),
                    sRequestStrategy.getExitStrategyType(),
                    sRequestStrategy.getEntryParams(),
                    sRequestStrategy.getExitParams(),
                    sResult.getTrades(),
                    sResult.getTotalProfit(),
                    sResult.getAverageHoldingDays(),
                    sResult.getTradeCount());
            logger.info("Strategy Name: {}", strategy.getStrategyName());
            responseDatas.add(strategy);
        }

        return ResponseEntity.ok(new SimulationResponse(
                request.getSymbol(),
                request.getStartDate(),
                request.getEndDate(),
                responseDatas,
                request.getInvestmentPerTrade(),
                simulationService.getStockDataDtoForSymbol(request.getSymbol(), request.getStartDate(),
                        request.getEndDate())));

    }

}
