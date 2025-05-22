package com.example.FinTech.dto.stock;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockDataDTO {

    private String symbol;
    private LocalDate date;
    private BigDecimal openPrice;
    private BigDecimal closePrice;
    private BigDecimal highPrice;
    private BigDecimal lowPrice;
    private Long volumen;

    public StockDataDTO(){
    }

    public StockDataDTO (String symbol, LocalDate date, BigDecimal openPrice, BigDecimal closePrice,
                        BigDecimal highPrice, BigDecimal lowPrice, Long volumen){
        this.symbol=symbol;
        this.date=date;
        this.openPrice=openPrice;
        this.closePrice=closePrice;
        this.highPrice=highPrice;
        this.lowPrice=lowPrice;
        this.volumen=volumen;
    }
    
}
