package com.example.FinTech.startup;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.FinTech.alpaca.MarketDataService;


@Component
@Order(2)
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
