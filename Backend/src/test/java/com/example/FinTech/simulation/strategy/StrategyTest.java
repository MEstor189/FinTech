package com.example.FinTech.simulation.strategy;

import com.example.FinTech.persistence.entity.StockData;
import com.example.FinTech.simulation.model.Portfolio;
import com.example.FinTech.simulation.model.Position;
import com.example.FinTech.simulation.strategy.entry.*;
import com.example.FinTech.simulation.strategy.exit.*;
import com.example.FinTech.util.MovingAverageUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class StrategyTest {

    private Map<LocalDate, StockData> historicalData;
    private static final String SYMBOL = "AAPL";

    @BeforeEach
    void setUp() {
        historicalData = new HashMap<>();
        // Erstelle Testdaten für 30 Tage
        for (int i = 0; i < 30; i++) {
            LocalDate date = LocalDate.now().minusDays(30 - i);
            double basePrice = 100.0 + i; // Steigender Trend
            historicalData.put(date, createStockData(SYMBOL, date, basePrice));
        }
    }

    @Test
    void testMovingAverageEntry() {
        // Arrange
        Map<LocalDate, StockData> testData = new HashMap<>();
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(19); // 20 Tage inkl. heute

        double[] prices = {
                120.0, 120.0, 120.0, 120.0, 120.0, // T-19 bis T-15
                120.0, 120.0, 120.0, 120.0, 120.0, // T-14 bis T-10
                118.0, 116.0, 114.0, 112.0, 110.0, // T-9 bis T-5 → downward shortMA
                112.0, 114.0, 116.0, 118.0, 124.0 // T-4 bis T → Up-Cross garantiert 💥
        };
        // Testdaten setzen
        for (int i = 0; i < prices.length; i++) {
            LocalDate date = startDate.plusDays(i);
            testData.put(date, createStockData(SYMBOL, date, prices[i]));
        }

        EntryStrategy strategy = EntryStrategyFactory.createEntryStrategy(
                EntryStrategyType.MOVING_AVERAGE,
                testData,
                Map.of("shortPeriod", 5.0, "longPeriod", 10.0));

        // Optional: Debug-Ausgabe
        double shortY = MovingAverageUtil.calculateAverage(today.minusDays(1), 5, testData);
        double longY = MovingAverageUtil.calculateAverage(today.minusDays(1), 10, testData);
        double shortT = MovingAverageUtil.calculateAverage(today, 5, testData);
        double longT = MovingAverageUtil.calculateAverage(today, 10, testData);

        System.out.printf("MA DEBUG: shortY=%.2f | longY=%.2f | shortT=%.2f | longT=%.2f%n", shortY, longY, shortT,
                longT);
        System.out.println("🔍 today in testData? " + testData.containsKey(today));
        System.out.println("📈 letzte 10 Preise:");
        testData.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .skip(testData.size() - 10)
                .forEach(e -> System.out.printf("%s: %.2f%n", e.getKey(), e.getValue().getClosePrice()));

        // Act
        StockData currentData = createStockData(SYMBOL, today, prices[prices.length - 1]);

        boolean shouldEnter = strategy.shouldEnter(currentData);
        System.out.println("💡 Ergebnis shouldEnter: " + shouldEnter);
        assertTrue(shouldEnter, "Expected entry signal due to upward MA cross.");
    }

    @Test
    void testMomentumEntry() {
        // Arrange
        EntryStrategy strategy = EntryStrategyFactory.createEntryStrategy(
                EntryStrategyType.MOMENTUM,
                historicalData,
                Map.of("daysInARow", 3.0));

        // Act & Assert
        // Test mit aufsteigendem Trend
        StockData today = createStockData(SYMBOL, LocalDate.now(), 130.0);
        assertTrue(strategy.shouldEnter(today));
    }

    @Test
    void testBuyTheDipEntry() {
        // Arrange
        EntryStrategy strategy = EntryStrategyFactory.createEntryStrategy(
                EntryStrategyType.BUY_THE_DIP,
                historicalData,
                Map.of("dipThreshold", 3.0));

        // Act & Assert
        // Test mit signifikantem Preisrückgang
        StockData today = createStockData(SYMBOL, LocalDate.now(), 95.0);
        assertTrue(strategy.shouldEnter(today));
    }

    @Test
    void testTargetProfitExit() {
        // Arrange
        ExitStrategy strategy = ExitStrategyFactory.createExitStrategy(
                ExitStrategyType.TARGET_PROFIT,
                historicalData,
                Map.of("profitTarget", 5.0));

        Portfolio portfolio = new Portfolio();
        Position position = new Position(SYMBOL, 100.0, 10.0, LocalDate.now().minusDays(1), "Test");
        portfolio.addPosition(position);

        // Act & Assert
        // Test mit erreichtem Gewinnziel
        StockData today = createStockData(SYMBOL, LocalDate.now(), 106.0);
        assertTrue(strategy.shouldExit(today, portfolio));
    }

    @Test
    void testTrailingStopExit() {
        // Arrange
        ExitStrategy strategy = ExitStrategyFactory.createExitStrategy(
                ExitStrategyType.TRAILING_STOP,
                historicalData,
                Map.of("trailingStopPercent", 5.0));

        Portfolio portfolio = new Portfolio();
        Position position = new Position(SYMBOL, 100.0, 10.0, LocalDate.now().minusDays(1), "Test");
        portfolio.addPosition(position);

        // Simuliere Preisbewegung
        StockData highPrice = createStockData(SYMBOL, LocalDate.now().minusDays(1), 110.0);
        position.updateMaxPrice(highPrice.getClosePrice().doubleValue());

        // Act & Assert
        // Test mit Unterschreitung des Trailing Stops
        StockData today = createStockData(SYMBOL, LocalDate.now(), 104.0);
        assertTrue(strategy.shouldExit(today, portfolio));
    }

    @Test
    void testMovingAverageCrossExit() {
        Map<LocalDate, StockData> testData = new HashMap<>();
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(19);

        // 💥 Diese Preise erzeugen garantiert einen CrossDown!
        double[] prices = {
                137.0, 137.0, 137.0, 137.0, 137.0, // T-19 bis T-15
                137.0, 137.0, 137.0, 137.0, 137.0, // T-14 bis T-10
                145.0, 145.0, 145.0, 145.0, 145.0, // T-9 bis T-5
                145.0, 144.0, 143.0, 140.0, 130.0 // T-4 bis T (weniger stark abfallend)
        };
        for (int i = 0; i < prices.length; i++) {
            LocalDate date = startDate.plusDays(i);
            testData.put(date, createStockData("AAPL", date, prices[i]));
        }

        ExitStrategy strategy = ExitStrategyFactory.createExitStrategy(
                ExitStrategyType.MOVING_AVERAGE_CROSS,
                testData,
                Map.of("shortPeriod", 5.0, "longPeriod", 10.0));

        Portfolio portfolio = new Portfolio();
        Position position = new Position("AAPL", 100.0, 10.0, today.minusDays(1), "Test");
        portfolio.addPosition(position);

        LocalDate yesterday = MovingAverageUtil.getPreviousTradingDay(today, testData);
        // Debug-Ausgabe zur Verifikation
        Double shortY = MovingAverageUtil.calculateAverage(yesterday, 5, testData);
        Double longY = MovingAverageUtil.calculateAverage(yesterday, 10, testData);
        Double shortT = MovingAverageUtil.calculateAverage(today, 5, testData);
        Double longT = MovingAverageUtil.calculateAverage(today, 10, testData);
        System.out.printf("MA DEBUG: shortY=%.2f | longY=%.2f | shortT=%.2f | longT=%.2f%n",
                shortY, longY, shortT, longT);

        StockData currentData = createStockData("AAPL", today, prices[19]);
        boolean shouldExit = strategy.shouldExit(currentData, portfolio);
        System.out.println("💡 shouldExit = " + shouldExit);
        System.out.println("shortY >= longY? " + (shortY >= longY));
        System.out.println("shortT < longT? " + (shortT < longT));

        System.out.println("strategieklasse:" + strategy.getClass().getName());
        assertTrue(shouldExit, "Expected exit signal due to downward moving average crossover");
    }

    private StockData createStockData(String symbol, LocalDate date, double closePrice) {
        return new StockData(
                symbol,
                date,
                BigDecimal.valueOf(closePrice - 2),
                BigDecimal.valueOf(closePrice + 2),
                BigDecimal.valueOf(closePrice + 5),
                BigDecimal.valueOf(closePrice - 5),
                1000L);
    }

    public static Double calculateAverageDebug(LocalDate endDate, int period, Map<LocalDate, StockData> data,
            String label) {
        List<LocalDate> sortedDates = data.keySet().stream()
                .filter(d -> !d.isAfter(endDate))
                .sorted()
                .collect(Collectors.toList());

        if (sortedDates.size() < period) {
            System.out.println("❌ Zu wenig Daten für MA " + label + " (" + period + ")");
            return null;
        }

        List<LocalDate> usedDates = sortedDates.subList(sortedDates.size() - period, sortedDates.size());
        List<Double> prices = usedDates.stream()
                .map(d -> data.get(d).getClosePrice().doubleValue())
                .collect(Collectors.toList());

        System.out.println("🔍 " + label + " (letzte " + period + " Tage bis " + endDate + "):");
        for (int i = 0; i < usedDates.size(); i++) {
            System.out.printf("   %s → %.2f%n", usedDates.get(i), prices.get(i));
        }

        double avg = prices.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        System.out.printf("➡️ %s AVG = %.2f%n", label, avg);
        return avg;
    }
}