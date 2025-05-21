package com.example.FinTech.dto.stockDataDto;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public BigDecimal getClosePrice() {
        return closePrice;
    }
    public void setClosePrice(BigDecimal closePrice) {
        this.closePrice = closePrice;
    }
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
    }

    public BigDecimal getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(BigDecimal highPrice) {
        this.highPrice = highPrice;
    }

    public BigDecimal getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(BigDecimal lowPrice) {
        this.lowPrice = lowPrice;
    }

    public Long getVolumen() {
        return volumen;
    }

    public void setVolumen(Long volumen) {
        this.volumen = volumen;
    }
    
}
