package com.example.FinTech.alpaca;

import org.springframework.stereotype.Component;

import net.jacobpeterson.alpaca.AlpacaAPI;

@Component
public class AlpacaClientFactory {

    private final AlpacaAPI alpacaAPI;

    public AlpacaClientFactory(AlpacaConfig alpacaConfig) {
        this.alpacaAPI = new AlpacaAPI(
            alpacaConfig.getApiKey(),
            alpacaConfig.getApiSecret(),
            AlpacaConfig.endpointType,
            AlpacaConfig.sourceType
        );
    }

    public AlpacaAPI getAlpacaAPI() {
        return alpacaAPI;
    }
}
