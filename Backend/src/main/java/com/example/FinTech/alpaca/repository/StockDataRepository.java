package com.example.FinTech.alpaca.repository;

import com.example.FinTech.alpaca.entity.StockData;
import com.example.FinTech.alpaca.entity.StockDataId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StockDataRepository extends JpaRepository<StockData, StockDataId> {

    // alle Daten ein Symbol
    List<StockData> findAllBySymbol(String symbol);

    // alle Daten ein Symbol innerhalb Zeitraum
    List<StockData> findAllBySymbolAndTradeDateBetween(String symbol, LocalDate startDate, LocalDate endDate);

    // einen Tag ein Symbol
    Optional<StockData> findBySymbolAndTradeDate(String symbol, LocalDate tradeDate);

    Optional<StockData> findTopBySymbolOrderByTradeDateDesc(String symbol);

}
