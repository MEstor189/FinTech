package com.example.FinTech.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
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

