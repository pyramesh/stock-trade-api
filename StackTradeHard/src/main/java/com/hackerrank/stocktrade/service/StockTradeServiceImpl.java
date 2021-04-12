package com.hackerrank.stocktrade.service;

import com.hackerrank.stocktrade.model.Trade;
import com.hackerrank.stocktrade.repository.TradeRepository;
import com.hackerrank.stocktrade.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class StockTradeServiceImpl implements StockTradeService {

	@Autowired
    private TradeRepository tradeRepo;

	@Autowired
	private UserRepository userRepository;
	@Override
	public void eraseTrade() {
		tradeRepo.deleteAll();
		
	}

	@Override
	@Transactional
	public Trade saveTrade(Trade trade) {
		/*Trade trade = covertDtoDomain(tradeDTO);*/
		Trade t = null;
		try{
			t= tradeRepo.save(trade);
		}catch (Exception e){
			e.printStackTrace();
		}
	return  t;
	}

	/*private Trade covertDtoDomain(TradeDTO tradeDTO) {
		Trade trade = new Trade();
		if(tradeDTO.getId() !=null){
			trade.setId(tradeDTO.getId());
		}
		trade.setStockSymbol(tradeDTO.getSymbol() != null ? tradeDTO.getSymbol() : "");
		trade.setStockPrice(tradeDTO.getPrice()!= null ? tradeDTO.getPrice() : null);
		trade.setStockQuantity(tradeDTO.getShares() != null ? tradeDTO.getShares() : null);
		trade.setTradeTimestamp(tradeDTO.getTimestamp() != null ? Timestamp.valueOf(tradeDTO.getTimestamp()): null);
		trade.setType(tradeDTO.getType() != null ? tradeDTO.getType() : null);
		if(tradeDTO.getUser() != null){
			trade.setUser(new User());
			trade.getUser().setId(tradeDTO.getUser().getId() != null ? tradeDTO.getUser().getId() : null);
			trade.getUser().setName(tradeDTO.getUser().getName() != null ? tradeDTO.getUser().getName(): "");
			//trade.getUser().setTrade(trade);
		}
		return trade;
	}*/

	@Override
	public Trade findTradeById(Long tradeId) {
		return tradeRepo.findById(tradeId).orElse(null);
	}

	@Override
	public List<Trade> findTradeByUserID(Long userId) {
		return tradeRepo.findByUserId(userId);
	}

	@Override
	public List<Trade> getHiiestAndLowest(String symbol, String startDate, String endDate) {
		
		Date startDt = null;
		Date endDt = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();

		try {
			calendar.setTime(formatter.parse(endDate));
			calendar.add(Calendar.DATE, 1);
			String modDate = formatter.format(calendar.getTime());
			startDt = formatter.parse(startDate);
			endDt=formatter.parse(modDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return tradeRepo.findBySymbolAndTimestampGreaterThanEqualAndTimestampLessThanEqual(symbol,startDt, endDt);
	}

	@Override
	public List<Trade> fetchAllTrades() {
		return tradeRepo.findAll();
	}

	@Override
	public List<Trade> findBySymbol(String symbol) {
		return tradeRepo.findBySymbol(symbol);
	}

	@Override
	public List<Trade> fetchAggrigateTrades(String startDate, String endDate) {
		Date startDt = null;
		Date endDt = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(formatter.parse(endDate));
			calendar.add(Calendar.DATE, 1);
			String modDate = formatter.format(calendar.getTime());
			startDt = formatter.parse(startDate);
			endDt=formatter.parse(modDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return tradeRepo.findByTimestampBetween(startDt, endDt);

	}

}
