package com.hackerrank.stocktrade.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Ramesh.Yaleru on 9/30/2019
 */
public class OddEvenTest {
    public static void main(String[] args) {

       /* List<Integer> inputlIst = Arrays.asList(1,2,3,4,5,6,7,8,9);
        List<Integer> addList = inputlIst.stream().filter(i-> i%2!=0).collect(Collectors.toList());

        System.out.println("addList"+addList);
    }*/


        List<Integer> inputlIst = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        Map<Boolean, List<Integer>> oddEvenStream =
                inputlIst.stream().collect(
                        Collectors.partitioningBy(num -> num % 2 ==0 ));

        List<Integer> odd = oddEvenStream.get(true);
        List<Integer> even = oddEvenStream.get(false);
        System.out.println("Odd numbers"+odd);
        System.out.println("even numbers"+even);
    }
}
