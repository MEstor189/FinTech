package com.example.FinTech.service;

import com.example.FinTech.dto.request.SimulationRequestStrategy;
import com.example.FinTech.exceptions.NotFoundException;
import com.example.FinTech.persistence.entity.StockData;
import com.example.FinTech.persistence.repository.StockDataRepository;
import com.example.FinTech.simulation.model.SimulationResult;
import com.example.FinTech.simulation.strategy.entry.EntryStrategyType;
import com.example.FinTech.simulation.strategy.exit.ExitStrategyType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SimulationServiceTest {
    @Mock
    private StockDataRepository stockDataRepository;
    
    @InjectMocks
    private SimulationService simulationService;
    
    @Test
    void testRunSimulation_WithValidData() {
        // Arrange
        String symbol = "AAPL";
        LocalDate startDate = LocalDate.now().minusDays(10);
        LocalDate endDate = LocalDate.now();
        double investmentPerTrade = 1000.0;
        
        // Erstelle Testdaten
        List<StockData> mockData = Arrays.asList(
            new StockData(symbol, startDate, BigDecimal.valueOf(100), BigDecimal.valueOf(105), 
                         BigDecimal.valueOf(110), BigDecimal.valueOf(95), 1000L),
            new StockData(symbol, startDate.plusDays(1), BigDecimal.valueOf(105), BigDecimal.valueOf(110), 
                         BigDecimal.valueOf(115), BigDecimal.valueOf(100), 1200L),
            new StockData(symbol, startDate.plusDays(2), BigDecimal.valueOf(110), BigDecimal.valueOf(115), 
                         BigDecimal.valueOf(120), BigDecimal.valueOf(105), 1500L)
        );
        
        // Konfiguriere die Strategie
        SimulationRequestStrategy request = new SimulationRequestStrategy();
        request.setEntryStrategyType(EntryStrategyType.MOVING_AVERAGE);
        request.setExitStrategyType(ExitStrategyType.TARGET_PROFIT);
        Map<String, Double> entryParams = new HashMap<>();
        entryParams.put("period", 2.0);
        request.setEntryParams(entryParams);
        
        // Mock Repository
        when(stockDataRepository.findAllBySymbolAndTradeDateBetweenOrderByTradeDateAsc(
            eq(symbol), eq(startDate), eq(endDate)))
            .thenReturn(mockData);
        
        // Act
        SimulationResult result = simulationService.runSimulation(
            request, symbol, startDate, endDate, investmentPerTrade);
        
        // Assert
        assertNotNull(result);
        assertNotNull(result.getTrades());
        verify(stockDataRepository).findAllBySymbolAndTradeDateBetweenOrderByTradeDateAsc(
            eq(symbol), eq(startDate), eq(endDate));
    }
    
    @Test
    void testRunSimulation_WithEmptyData() {
        // Arrange
        String symbol = "AAPL";
        LocalDate startDate = LocalDate.now().minusDays(10);
        LocalDate endDate = LocalDate.now();
        double investmentPerTrade = 1000.0;
        
        SimulationRequestStrategy request = new SimulationRequestStrategy();
        request.setEntryStrategyType(EntryStrategyType.MOVING_AVERAGE);
        request.setExitStrategyType(ExitStrategyType.TARGET_PROFIT);
        
        // Mock Repository mit leerer Liste
        when(stockDataRepository.findAllBySymbolAndTradeDateBetweenOrderByTradeDateAsc(
            eq(symbol), eq(startDate), eq(endDate)))
            .thenReturn(List.of());
        
        // Act & Assert
        assertThrows(NotFoundException.class, () -> 
            simulationService.runSimulation(request, symbol, startDate, endDate, investmentPerTrade)
        );
    }
    
    @Test
    void testRunSimulation_WithInvalidDateRange() {
        // Arrange
        String symbol = "AAPL";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().minusDays(10); // EndDate vor StartDate
        double investmentPerTrade = 1000.0;
        
        SimulationRequestStrategy request = new SimulationRequestStrategy();
        request.setEntryStrategyType(EntryStrategyType.MOVING_AVERAGE);
        request.setExitStrategyType(ExitStrategyType.TARGET_PROFIT);
        
        // Act & Assert
        assertThrows(NotFoundException.class, () -> 
            simulationService.runSimulation(request, symbol, startDate, endDate, investmentPerTrade)
        );
    }
} 