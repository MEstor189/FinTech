package com.example.FinTech.simulation.engine;

import com.example.FinTech.persistence.entity.StockData;
import com.example.FinTech.simulation.model.Portfolio;
import com.example.FinTech.simulation.model.SimulationResult;
import com.example.FinTech.simulation.model.Trade;
import com.example.FinTech.simulation.strategy.entry.EntryStrategy;
import com.example.FinTech.simulation.strategy.exit.ExitStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class SimulationEngineTest {

    @Mock
    private EntryStrategy entryStrategy;

    @Mock
    private ExitStrategy exitStrategy;

    private SimulationEngine simulationEngine;
    private static final double INVESTMENT_PER_TRADE = 1000.0;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        simulationEngine = new SimulationEngine(entryStrategy, exitStrategy, INVESTMENT_PER_TRADE);
    }

    @Test
    void testRunSimulation_WithEntryAndExit() {
        // Arrange
        String symbol = "AAPL";
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = date1.plusDays(1);
        LocalDate date3 = date2.plusDays(1);

        List<StockData> stockDataList = Arrays.asList(
            createStockData(symbol, date1, 100.0),
            createStockData(symbol, date2, 105.0),
            createStockData(symbol, date3, 110.0)
        );

        // Mock Entry Strategy
        when(entryStrategy.shouldEnter(eq(stockDataList.get(0)))).thenReturn(true);
        when(entryStrategy.shouldEnter(eq(stockDataList.get(1)))).thenReturn(false);
        when(entryStrategy.shouldEnter(eq(stockDataList.get(2)))).thenReturn(false);

        // Mock Exit Strategy
        when(exitStrategy.shouldExit(any(StockData.class), any(Portfolio.class))).thenReturn(false);
        when(exitStrategy.shouldExit(eq(stockDataList.get(2)), any(Portfolio.class))).thenReturn(true);

        // Act
        SimulationResult result = simulationEngine.runSimulation(stockDataList);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getTrades());
        assertEquals(1, result.getTrades().size());
        
        Trade trade = result.getTrades().get(0);
        assertEquals(symbol, trade.getSymbol());
        assertEquals(102.0, trade.getEntryPrice());
        assertEquals(112.0, trade.getExitPrice());
        assertEquals(INVESTMENT_PER_TRADE / 102.0, trade.getQuantity());
    }

    @Test
    void testRunSimulation_WithNoEntry() {
        // Arrange
        String symbol = "AAPL";
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = date1.plusDays(1);

        List<StockData> stockDataList = Arrays.asList(
            createStockData(symbol, date1, 100.0),
            createStockData(symbol, date2, 105.0)
        );

        // Mock Entry Strategy - keine Einstiege
        when(entryStrategy.shouldEnter(any(StockData.class))).thenReturn(false);
        when(exitStrategy.shouldExit(any(StockData.class), any(Portfolio.class))).thenReturn(false);

        // Act
        SimulationResult result = simulationEngine.runSimulation(stockDataList);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getTrades());
        assertTrue(result.getTrades().isEmpty());
    }

    @Test
    void testRunSimulation_WithMultipleEntriesAndExits() {
        // Arrange
        String symbol = "AAPL";
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = date1.plusDays(1);
        LocalDate date3 = date2.plusDays(1);
        LocalDate date4 = date3.plusDays(1);

        List<StockData> stockDataList = Arrays.asList(
            createStockData(symbol, date1, 100.0),
            createStockData(symbol, date2, 105.0),
            createStockData(symbol, date3, 110.0),
            createStockData(symbol, date4, 115.0)
        );

        // Mock Entry Strategy - Einstieg bei Tag 1 und 3
        when(entryStrategy.shouldEnter(eq(stockDataList.get(0)))).thenReturn(true);
        when(entryStrategy.shouldEnter(eq(stockDataList.get(1)))).thenReturn(false);
        when(entryStrategy.shouldEnter(eq(stockDataList.get(2)))).thenReturn(true);
        when(entryStrategy.shouldEnter(eq(stockDataList.get(3)))).thenReturn(false);

        // Mock Exit Strategy - Ausstieg bei Tag 2 und 4
        when(exitStrategy.shouldExit(eq(stockDataList.get(1)), any(Portfolio.class))).thenReturn(true);
        when(exitStrategy.shouldExit(eq(stockDataList.get(3)), any(Portfolio.class))).thenReturn(true);

        // Act
        SimulationResult result = simulationEngine.runSimulation(stockDataList);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getTrades());
        assertEquals(2, result.getTrades().size());
        
        // Überprüfe ersten Trade
        Trade trade1 = result.getTrades().get(0);
        assertEquals(102.0, trade1.getEntryPrice());
        assertEquals(107.0, trade1.getExitPrice());
        
        // Überprüfe zweiten Trade
        Trade trade2 = result.getTrades().get(1);
        assertEquals(112.0, trade2.getEntryPrice());
        assertEquals(117.0, trade2.getExitPrice());
    }

    private StockData createStockData(String symbol, LocalDate date, double closePrice) {
        return new StockData(
            symbol,
            date,
            BigDecimal.valueOf(closePrice - 2),
            BigDecimal.valueOf(closePrice + 2),
            BigDecimal.valueOf(closePrice + 5),
            BigDecimal.valueOf(closePrice - 5),
            1000L
        );
    }
} 