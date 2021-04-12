package com.hackerrank.stocktrade.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class TradeDomain {

    private String symbol;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date timestamp;
    private Double price;
    private Long shares;
    private Long id;

    private UserDomain user;
    private String type;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getShares() {
        return shares;
    }

    public void setShares(Long shares) {
        this.shares = shares;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDomain getUser() {
        return user;
    }

    public void setUser(UserDomain user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



}
