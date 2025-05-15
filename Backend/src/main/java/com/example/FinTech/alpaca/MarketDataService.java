package com.example.FinTech.alpaca;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.FinTech.alpaca.entity.StockData;
import com.example.FinTech.alpaca.repository.StockDataRepository;

import net.jacobpeterson.alpaca.AlpacaAPI;
import net.jacobpeterson.alpaca.openapi.marketdata.model.StockBarsRespSingle;

@Service
public class MarketDataService {

    private AlpacaAPI alpacaAPI;
    private final AlpacaClientFactory alpacaClientFactory;
    private final StockDataRepository stockDataRepository;

    public MarketDataService(AlpacaClientFactory alpacaClientFactory, StockDataRepository stockDataRepository) {
        this.stockDataRepository = stockDataRepository;
        this.alpacaClientFactory = alpacaClientFactory;
        this.alpacaAPI = alpacaClientFactory.getAlpacaAPI();
    }

    public void getAndSaveAllBars() {
        try {
            LocalDate today = LocalDate.now();
            LocalDate threeYearsAgo = today.minusYears(3);

            OffsetDateTime startDateTime = threeYearsAgo.atStartOfDay().atOffset(ZoneOffset.UTC);
            OffsetDateTime endDateTime = today.atStartOfDay().atOffset(ZoneOffset.UTC);

            List<StockData> stockDataList = new ArrayList<>();
            StockBarsRespSingle response = alpacaAPI.marketData().stock().stockBarSingle("AAPL", "1Day", startDateTime,
                    endDateTime, null, null, null, null, null, null, null);
            if (response.getBars() != null) {
                response.getBars().forEach(bar -> {
                    StockData stockData = new StockData(
                            "AAPL",
                            bar.getT().toLocalDate(),
                            new BigDecimal(bar.getO().toString()),
                            new BigDecimal(bar.getC().toString()),
                            new BigDecimal(bar.getH().toString()),
                            new BigDecimal(bar.getL().toString()),
                            bar.getV().longValue());
                    stockDataList.add(stockData);
                });

                stockDataRepository.saveAll(stockDataList);
                System.out.println(stockDataList.size() + " Bars saved in MySQL.");
            } else {
                System.out.println("No Bars.");
            }
        } catch (Exception e) {
            System.out.println("exeption:" + e);
        }
    }

    public void updateAndSaveNewBars(String symbol) {
        try {

            Optional<StockData> latestEntryOptional = stockDataRepository.findTopBySymbolOrderByTradeDateDesc(symbol);

            LocalDate startDate;
            if (latestEntryOptional.isPresent()) {
                startDate = latestEntryOptional.get().getTradeDate().plusDays(1);
            } else {
                startDate = LocalDate.now().minusYears(3);
            }
            LocalDate today = LocalDate.now();
            if (!startDate.isBefore(today)) {
                System.out.println("🔎 No new data to update for " + symbol);
                return;
            }

            OffsetDateTime startDateTime = startDate.atStartOfDay().atOffset(ZoneOffset.UTC);
            OffsetDateTime endDateTime = today.atStartOfDay().atOffset(ZoneOffset.UTC);

            StockBarsRespSingle response = alpacaAPI.marketData().stock().stockBarSingle(symbol, "1Day", startDateTime,
                    endDateTime, null, null, null, null, null, null, null);
            List<StockData> newBars = new ArrayList<>();

            if (response.getBars() != null) {
                response.getBars().forEach(bar -> {
                    StockData stockData = new StockData(
                            "AAPL",
                            bar.getT().toLocalDate(),
                            new BigDecimal(bar.getO().toString()),
                            new BigDecimal(bar.getC().toString()),
                            new BigDecimal(bar.getH().toString()),
                            new BigDecimal(bar.getL().toString()),
                            bar.getV().longValue());
                    newBars.add(stockData);
                });

                stockDataRepository.saveAll(newBars);
                System.out.println(newBars.size() + " Bars saved in MySQL.");
            } else {
                System.out.println("No Bars.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Error while updating data for " + symbol);
        }
    }
}
