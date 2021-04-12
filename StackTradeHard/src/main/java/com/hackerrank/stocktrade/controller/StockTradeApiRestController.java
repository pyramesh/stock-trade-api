package com.hackerrank.stocktrade.controller;

import com.hackerrank.stocktrade.dto.*;
import com.hackerrank.stocktrade.model.Trade;
import com.hackerrank.stocktrade.service.StockTradeService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class StockTradeApiRestController {

    @Autowired
    private StockTradeService stockService;

    @Autowired
    private TradeConverter tradeConverter;

    @DeleteMapping("/erase")
    public ResponseEntity<?> eraseTrade() {

        stockService.eraseTrade();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/trades")
    public ResponseEntity<?> saveTrade(@Valid @RequestBody TradeDomain tradeDto) {
        Trade tradeObj = tradeConverter.apply(tradeDto);
        if (tradeDto.getId() != null) {
            Trade existTrade = stockService.findTradeById(tradeDto.getId());
            if (existTrade != null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                System.out.println("##### trade Domain ########" + tradeDto);
                Trade t = stockService.saveTrade(tradeObj);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

    }

    @GetMapping("/trades/users/{id}")
    public ResponseEntity<?> getAllTradesByUserId(@RequestBody(required = false) Trade trade, @PathVariable("id") Long id) {
        List<Trade> trades = new ArrayList<>();

        trades = stockService.findTradeByUserID(id);
        if (trades != null && trades.size() > 0) {
            return new ResponseEntity<>(trades, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(trades, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/trades")
    public ResponseEntity<?> getAllTrades() {
        List<Trade> trades = stockService.fetchAllTrades();
        //List<TradeDTO> dtos = convertDomaintoDto(trades);
        return new ResponseEntity<>(trades, HttpStatus.OK);
    }

	/*private List<TradeDTO> convertDomaintoDto(List<Trade> trades) {
	    	List<TradeDTO> dtoList = new ArrayList<>();

		trades.stream().forEach(trade ->{
			TradeDTO dto = new TradeDTO();
			dto.setSymbol(trade.getStockSymbol());
			dto.setTimestamp(String.valueOf(trade.getTradeTimestamp()));
			dto.setPrice(trade.getStockPrice());
			dto.setShares(trade.getStockQuantity());
			dto.setType(trade.getType());
			dto.setId(trade.getId());
			if(trade.getUser() != null)
				dto.setUser(new UserDTO());
			dto.getUser().setId(trade.getUser().getId());
			dto.getUser().setName(trade.getUser().getName());
			dtoList.add(dto);
		});
	    	return dtoList;
	}*/

    @GetMapping("/stocks/{symbol}/price")
    public ResponseEntity<?> getHiiestAndLowest(@PathVariable("symbol") String symbol, @RequestParam(name = "start") String start, @RequestParam("end") String end) {
        List<Trade> tradeList = stockService.findBySymbol(symbol);
        if (tradeList != null && tradeList.size() > 0) {
            List<Trade> trades = stockService.getHiiestAndLowest(symbol, start, end);
            if (trades != null && trades.size() > 0) {
                Trade tradeMin = trades.stream().min(Comparator.comparing(Trade::getPrice)).orElse(null);
                Trade tradeHigh = trades.stream().max(Comparator.comparing(Trade::getPrice)).orElse(null);
                TradeLowestHighiest tr = new TradeLowestHighiest(tradeMin.getSymbol(), tradeMin.getPrice(), tradeHigh.getPrice());
                return new ResponseEntity<>(tr, HttpStatus.OK);
            } else {
                ErrorMessage error = new ErrorMessage("There are no trades in the given date range");
                return new ResponseEntity<>(error, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }


    @GetMapping("/stocks/stats")
    public ResponseEntity<?> fetchAggrigateTrades(@RequestParam(name = "start") String start, @RequestParam("end") String end) {
        List<Trade> trades  = stockService.fetchAggrigateTrades(start,end);
        List<Trade> entryMapTrades = null;
List<TradeStatsDto> dtos = new ArrayList<>();

        Map<String, List> tradeMap = new HashMap<>();
        final Map<String, List<Trade>> groupBySymbolMap =
                trades.stream().sorted(Comparator.comparingDouble(Trade::getId)).collect(Collectors.groupingBy(Trade::getSymbol));
        for (Map.Entry<String, List<Trade>> entry : groupBySymbolMap.entrySet()) {
            List priceList = new ArrayList();
            List<Double> finalpriceList = new ArrayList();
            List<Trade> trades1 = entry.getValue();
            for(Trade t : trades1){
                priceList.add(t.getPrice());
            }
           if(priceList.size()>1){
               for(int i=0; i<priceList.size()-1;i++){
                   double a = (double)priceList.get(i);
                   double b = (double)priceList.get(i+1);
                   double result = a-b;
                   if(result<0){
                       result =-result;
                   }
                       finalpriceList.add(result);

               }
           }
           boolean isMaxFal = false;
           boolean isMasRise = false;
            DecimalFormat df = new DecimalFormat("#.##");
           Map<String,Double> finalMap = new HashedMap();
           for( int j=0; j<finalpriceList.size()-1;j++){
                    if(finalpriceList.get(j) > finalpriceList.get(j+1)){
                        isMaxFal=true;
                        finalMap.put("max_fall",Double.valueOf(df.format(finalpriceList.get(j).doubleValue())));
                    }else{
                        isMasRise=true;
                        finalMap.put("max_rise",Double.valueOf(df.format(finalpriceList.get(j).doubleValue())) );
                    }
                    if(isMaxFal && isMasRise)
                    break;
           }
            double maxFalVal = (finalMap.get("max_fall") != null ? finalMap.get("max_fall").doubleValue() : 0.00);
            double maxRiseVal = (finalMap.get("max_rise") !=null ? finalMap.get("max_rise").doubleValue() : 0.00);
            TradeStatsDto dto = new TradeStatsDto(entry.getKey(),maxFalVal,maxRiseVal,1 );
            dtos.add(dto);
        }
        //List<Trade> trades =                stockService.fetchAggrigateTrades(startDate, endDate);


        if (dtos != null && dtos.size() > 0) {
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } else {
            ErrorMessage error = new ErrorMessage("There are no trades in the given date range");
            return new ResponseEntity<>(error, HttpStatus.OK);
        }

    }
}
