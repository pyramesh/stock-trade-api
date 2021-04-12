package com.hackerrank.stocktrade.dto;

import com.hackerrank.stocktrade.model.Trade;
import com.hackerrank.stocktrade.model.User;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

@Component
public class TradeConverter implements Function<TradeDomain, Trade> {

    @Override
    public Trade apply(TradeDomain tradeDomain) {
      Trade trade = new Trade();
      trade.setShares(tradeDomain.getShares());
        trade.setId(tradeDomain.getId());
        trade.setPrice(tradeDomain.getPrice());
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String strDate = sm.format(tradeDomain.getTimestamp());
        Date dt = null;
        try {
             dt = sm.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        trade.setTimestamp(dt);
        trade.setSymbol(tradeDomain.getSymbol());
        trade.setType(tradeDomain.getType());
        User user= new User();
        user.setId(tradeDomain.getUser().getId());
        user.setName(tradeDomain.getUser().getName());
        trade.setUser(user);
        return trade;

    }
}
