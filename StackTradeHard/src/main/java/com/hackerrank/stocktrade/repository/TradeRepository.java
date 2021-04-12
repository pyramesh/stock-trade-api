package com.hackerrank.stocktrade.repository;

import com.hackerrank.stocktrade.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
	
	List<Trade> findByUserId(@Param("userId") Long userId);
	List<Trade> findBySymbol(@Param("symbol") String symbol);
	
	List<Trade> findBySymbolAndTimestampGreaterThanEqualAndTimestampLessThanEqual(String symbol, Date startDate, Date endDate);

	List<Trade> findByTimestampBetween(Date startDate, Date endDate);
}
