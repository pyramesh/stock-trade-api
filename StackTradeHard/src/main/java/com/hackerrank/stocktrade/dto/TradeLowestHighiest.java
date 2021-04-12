package com.hackerrank.stocktrade.dto;

/**
 * @author Ramesh.Yaleru on 10/4/2019
 */
public class TradeLowestHighiest {
    private String symbol;
    private double lowest;
    private double highest;

    public TradeLowestHighiest(String symbol, double lowest, double highest) {
        this.symbol = symbol;
        this.lowest = lowest;
        this.highest = highest;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getLowest() {
        return lowest;
    }

    public void setLowest(double lowest) {
        this.lowest = lowest;
    }

    public double getHighest() {
        return highest;
    }

    public void setHighest(double highest) {
        this.highest = highest;
    }
}
