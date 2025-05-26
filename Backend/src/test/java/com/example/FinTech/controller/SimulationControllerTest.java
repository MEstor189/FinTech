package com.example.FinTech.controller;

import com.example.FinTech.dto.request.SimulationRequest;
import com.example.FinTech.dto.request.SimulationRequestStrategy;
import com.example.FinTech.service.SimulationService;
import com.example.FinTech.simulation.model.SimulationResult;
import com.example.FinTech.simulation.strategy.entry.EntryStrategyType;
import com.example.FinTech.simulation.strategy.exit.ExitStrategyType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SimulationControllerTest {
    @Mock
    private SimulationService simulationService;
    
    @InjectMocks
    private SimulationController simulationController;
    
    @Test
    void testRunSimulation_ValidRequest() {
        // Arrange
        SimulationRequest request = new SimulationRequest();
        request.setSymbol("AAPL");
        request.setStartDate(LocalDate.now().minusDays(10));
        request.setEndDate(LocalDate.now());
        request.setInvestmentPerTrade(1000.0);
        
        SimulationRequestStrategy strategy = new SimulationRequestStrategy();
        strategy.setStrategyName("Test Strategy");
        strategy.setEntryStrategyType(EntryStrategyType.MOVING_AVERAGE);
        strategy.setExitStrategyType(ExitStrategyType.TARGET_PROFIT);
        request.setRequestStrategies(Arrays.asList(strategy));
        
        SimulationResult mockResult = new SimulationResult(new ArrayList<>());
        when(simulationService.runSimulation(any(), anyString(), any(), any(), anyDouble()))
            .thenReturn(mockResult);
        
        // Act
        ResponseEntity<?> response = simulationController.runSimulation(
            request, new BeanPropertyBindingResult(request, "request"));
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(simulationService).runSimulation(any(), anyString(), any(), any(), anyDouble());
    }
} 