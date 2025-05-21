package com.example.FinTech.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.example.FinTech.alpaca.entity.StockData;
import com.example.FinTech.dto.stockDataDto.StockDataDTO;

public class StockDataMapper {
        public static List<StockDataDTO> toDtoList(List<StockData> stockDataList) {
        return stockDataList.stream()
            .map(data -> new StockDataDTO(                
            data.getSymbol(),
            data.getTradeDate(),
            data.getOpenPrice(),
            data.getClosePrice(),
            data.getHighPrice(),
            data.getLowPrice(),
            data.getVolume()
            ))
            .collect(Collectors.toList());
    }
}
