package com.example.FinTech.alpaca;

import net.jacobpeterson.alpaca.AlpacaAPI;

public class AlpacaClientFactory {

    private static AlpacaAPI alpacaAPI = null;

    public static AlpacaAPI getAlpacaAPI() {
        if (alpacaAPI == null) {
            alpacaAPI = new AlpacaAPI(
                AlpacaConfig.API_KEY_ID,
                AlpacaConfig.API_SECRET_KEY,
                AlpacaConfig.endpointType,
                AlpacaConfig.sourceType
            );
        }
        return alpacaAPI;
    }
    
}
