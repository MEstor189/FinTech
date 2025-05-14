package com.example.FinTech.alpaca;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


import net.jacobpeterson.alpaca.model.util.apitype.MarketDataWebsocketSourceType;
import net.jacobpeterson.alpaca.model.util.apitype.TraderAPIEndpointType;

@Configuration
@ConfigurationProperties(prefix = "alpaca")
public class AlpacaConfig {

    private String apiKey;
    private String apiSecret;

    // Getter und Setter
    public String getApiKey() { 
        System.out.println(apiKey);
        return apiKey; 
    }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; }
    public String getApiSecret() { return apiSecret; }
    public void setApiSecret(String apiSecret) { this.apiSecret = apiSecret; }

    

    public static final TraderAPIEndpointType endpointType = TraderAPIEndpointType.PAPER;

    public static final MarketDataWebsocketSourceType sourceType = MarketDataWebsocketSourceType.IEX;
}
