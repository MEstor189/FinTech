package com.example.FinTech.alpaca;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import net.jacobpeterson.alpaca.AlpacaAPI;
import net.jacobpeterson.alpaca.openapi.marketdata.model.StockBarsRespSingle;
@Service
public class MarketDataService {
    
    private AlpacaAPI alpacaAPI;
    private final AlpacaClientFactory alpacaClientFactory;

    public MarketDataService(AlpacaClientFactory alpacaClientFactory) {
        
        this.alpacaClientFactory = alpacaClientFactory;
        this.alpacaAPI = alpacaClientFactory.getAlpacaAPI();
    }
    public void getDailyBars() {
        LocalDate today = LocalDate.now();
        LocalDate threeYearsAgo = today.minusYears(3);

        // Umwandeln in OffsetDateTime
        OffsetDateTime startDateTime = threeYearsAgo.atStartOfDay().atOffset(ZoneOffset.UTC);
        OffsetDateTime endDateTime = today.atStartOfDay().atOffset(ZoneOffset.UTC);

        try {
            // API Call
            StockBarsRespSingle response = alpacaAPI.marketData().stock().stockBarSingle("AAPL", "1Day", startDateTime, endDateTime, null, null, null, null, null, null, null);
            if (response.getBars() != null) {
                    response.getBars().forEach(bar -> {
/*                         System.out.println("Date: " + bar.getT()
                                + " Open: " + bar.getO()
                                + " Close: " + bar.getC()
                                + " High: " + bar.getH()
                                + " Low: " + bar.getL()
                                + " Volume: " + bar.getV()); */
                    });
            } else {
                System.out.println("Keine Bars erhalten.");
            }
        } catch (Exception e) {
            System.out.println("exeptioN::" + e);
        }

    }




}
