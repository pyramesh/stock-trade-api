package com.hackerrank.stocktrade.controller;

import java.util.regex.Pattern;

/**
 * @author Ramesh.Yaleru on 9/30/2019
 */
public class ReverseStringTest {

    public static void main(String[] args) {
        String name="I am back to office";



        String outputString ="";
        String[] strtoken = name.split("\\s");
        for(int i=strtoken.length-1; i>=0; i--){
            outputString += strtoken[i]+" ";

        }
        System.out.println("outputString ###"+outputString);
    }
}
