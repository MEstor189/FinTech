package com.example.FinTech.alpaca.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "stock_data")
@IdClass(StockDataId.class)
public class StockData {

    @Id
    @Column(name = "symbol", length = 10, nullable = false)
    private String symbol;

    @Id
    @Column(name = "trade_date", nullable = false)
    private LocalDate tradeDate;

    @Column(name = "open_price", precision = 15, scale = 6, nullable = false)
    private BigDecimal openPrice;

    @Column(name = "close_price", precision = 15, scale = 6, nullable = false)
    private BigDecimal closePrice;

    @Column(name = "high_price", precision = 15, scale = 6, nullable = false)
    private BigDecimal highPrice;

    @Column(name = "low_price", precision = 15, scale = 6, nullable = false)
    private BigDecimal lowPrice;

    @Column(name = "volume", nullable = false)
    private Long volume;

    public StockData() {}

    public StockData(String symbol, LocalDate tradeDate, BigDecimal openPrice, BigDecimal closePrice, BigDecimal highPrice, BigDecimal lowPrice, Long volume) {
        this.symbol = symbol;
        this.tradeDate = tradeDate;
        this.openPrice = openPrice;
        this.closePrice = closePrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.volume = volume;
    }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public LocalDate getTradeDate() { return tradeDate; }
    public void setTradeDate(LocalDate tradeDate) { this.tradeDate = tradeDate; }

    public BigDecimal getOpenPrice() { return openPrice; }
    public void setOpenPrice(BigDecimal openPrice) { this.openPrice = openPrice; }

    public BigDecimal getClosePrice() { return closePrice; }
    public void setClosePrice(BigDecimal closePrice) { this.closePrice = closePrice; }

    public BigDecimal getHighPrice() { return highPrice; }
    public void setHighPrice(BigDecimal highPrice) { this.highPrice = highPrice; }

    public BigDecimal getLowPrice() { return lowPrice; }
    public void setLowPrice(BigDecimal lowPrice) { this.lowPrice = lowPrice; }

    public Long getVolume() { return volume; }
    public void setVolume(Long volume) { this.volume = volume; }

    //wichtig für zusammengesetzte Schlüssel!!!!!
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockData)) return false;
        StockData that = (StockData) o;
        return Objects.equals(symbol, that.symbol) && Objects.equals(tradeDate, that.tradeDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, tradeDate);
    }
}

