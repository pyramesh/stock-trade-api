package com.hackerrank.stocktrade.service;

import com.hackerrank.stocktrade.model.Trade;

import java.util.List;

public interface StockTradeService {

	public void eraseTrade();
    
    public Trade saveTrade(Trade tradeDTO);
    
    Trade findTradeById(Long id);
    
    List<Trade> findTradeByUserID(Long userId);
    
    List<Trade> getHiiestAndLowest(String symbol, String startDate, String endDate);

    public List<Trade>fetchAllTrades();

    List<Trade> findBySymbol(String symbol);

    List<Trade> fetchAggrigateTrades(String startDate, String endDate);

}
