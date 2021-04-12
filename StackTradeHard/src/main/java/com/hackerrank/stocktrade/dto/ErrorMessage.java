package com.hackerrank.stocktrade.dto;

/**
 * @author Ramesh.Yaleru on 10/4/2019
 */
public class ErrorMessage {
    private String message;

    public ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
