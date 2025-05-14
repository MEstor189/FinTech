package com.example.FinTech.alpaca;

import net.jacobpeterson.alpaca.AlpacaAPI;

public class MarketDataService {
    
    private AlpacaAPI alpacaAPI;

    public MarketDataService() {
        this.alpacaAPI = AlpacaClientFactory.getAlpacaAPI();
    }
}
