package com.hackerrank.stocktrade.dto;

/**
 * @author Ramesh.Yaleru on 10/5/2019
 */
public class TradeStatsDto {
    private String symbol;
    private double max_fall;
    private double max_rise;
    private int fluctuations;

    public TradeStatsDto(String symbol, double max_fall, double max_rise, int fluctuations) {
        this.symbol = symbol;
        this.max_fall = max_fall;
        this.max_rise = max_rise;
        this.fluctuations = fluctuations;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getMax_fall() {
        return max_fall;
    }

    public void setMax_fall(double max_fall) {
        this.max_fall = max_fall;
    }

    public double getMax_rise() {
        return max_rise;
    }

    public void setMax_rise(double max_rise) {
        this.max_rise = max_rise;
    }

    public int getFluctuations() {
        return fluctuations;
    }

    public void setFluctuations(int fluctuations) {
        this.fluctuations = fluctuations;
    }
}
