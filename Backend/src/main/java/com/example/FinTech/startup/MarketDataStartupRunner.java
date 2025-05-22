package com.example.FinTech.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.FinTech.alpaca.MarketDataService;


@Component
@Order(2)
public class MarketDataStartupRunner implements CommandLineRunner {

    private final MarketDataService marketDataService;
    private static final Logger logger = LoggerFactory.getLogger(MarketDataStartupRunner.class);


    public MarketDataStartupRunner(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    @Override
    public void run(String... args) throws Exception {

        logger.info("Starte MarketData Abruf...");

        //marketDataService.getAndSaveAllBars();
        
        logger.info("MarketData Abruf abgeschlossen!");
    }
}
