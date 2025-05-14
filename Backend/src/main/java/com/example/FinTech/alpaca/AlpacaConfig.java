package com.example.FinTech.alpaca;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.jacobpeterson.alpaca.model.util.apitype.MarketDataWebsocketSourceType;
import net.jacobpeterson.alpaca.model.util.apitype.TraderAPIEndpointType;

@Component
public class AlpacaConfig {

    @Value("${alpaca.api.key}")
    public static final String API_KEY_ID = "your_key_id";

    @Value("${alpaca.api.secret}")
    public static final String API_SECRET_KEY = "your_secret_key";

    public static final TraderAPIEndpointType endpointType = TraderAPIEndpointType.PAPER;
    
    public static final MarketDataWebsocketSourceType sourceType = MarketDataWebsocketSourceType.IEX;
}
