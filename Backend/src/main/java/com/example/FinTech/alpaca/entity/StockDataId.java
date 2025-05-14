package com.example.FinTech.alpaca.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class StockDataId implements Serializable {

    private String symbol;
    private LocalDate tradeDate;

    // Standard-Konstruktor
    public StockDataId() {}

    public StockDataId(String symbol, LocalDate tradeDate) {
        this.symbol = symbol;
        this.tradeDate = tradeDate;
    }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public LocalDate getTradeDate() { return tradeDate; }
    public void setTradeDate(LocalDate tradeDate) { this.tradeDate = tradeDate; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockDataId)) return false;
        StockDataId that = (StockDataId) o;
        return Objects.equals(symbol, that.symbol) && Objects.equals(tradeDate, that.tradeDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, tradeDate);
    }
}
