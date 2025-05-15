package com.example.FinTech.alpaca;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

//Scheduler für Später um immer aktuelle Daten zu haben

@Service
public class UpdateScheduler {
        private final MarketDataService marketDataService;

    public UpdateScheduler(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    @Scheduled(cron = "0 0 22 * * ?")
    public void checkForNewData() {
        marketDataService.updateAndSaveNewBars("AAPL");
    } 
}
