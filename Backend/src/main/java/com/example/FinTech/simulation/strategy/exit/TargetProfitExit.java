package com.example.FinTech.simulation.strategy.exit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.FinTech.persistence.entity.StockData;
import com.example.FinTech.simulation.model.Portfolio;

public class TargetProfitExit implements ExitStrategy{

    private static final Logger logger = LoggerFactory.getLogger(TargetProfitExit.class);
    private final double profitTargetPercent; // z. B. 5.0 = +5%

    public TargetProfitExit(double profitTargetPercent){
        this.profitTargetPercent=profitTargetPercent;
    }

    @Override
    public boolean shouldExit(StockData today, Portfolio portfolio) {
        return portfolio.getOpenPositions().stream().anyMatch(pos -> {
            if (!pos.getSymbol().equals(today.getSymbol()))
                return false;

            double entry = pos.getEntryPrice();
            double current = today.getClosePrice().doubleValue();
            double change = ((current - entry) / entry) * 100.0;
            logger.debug("Sell signal: {} reached {:.2f}% profit on {}", pos.getSymbol(), change, today.getTradeDate());

            return change >= profitTargetPercent;
        });
    }
}
