package com.example.FinTech.alpaca;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;



@Component
public class MarketDataStartupRunner implements CommandLineRunner {

    private final MarketDataService marketDataService;

    public MarketDataStartupRunner(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starte MarketData Abruf...");
        // Hier rufst du deine Methode auf
        marketDataService.getDailyBars();
        
        System.out.println("MarketData Abruf abgeschlossen!");
    }
}
