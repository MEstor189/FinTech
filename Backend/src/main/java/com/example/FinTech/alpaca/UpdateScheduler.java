package com.example.FinTech.alpaca;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class UpdateScheduler {
        private final MarketDataService marketDataService;
        private static final Logger logger = LoggerFactory.getLogger(UpdateScheduler.class);

    public UpdateScheduler(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    @Scheduled(cron = "0 0 22 * * ?")
    public void checkForNewData() {
        logger.info("Check for new Bars");
        marketDataService.updateAndSaveNewBars("AAPL");
    } 
}
