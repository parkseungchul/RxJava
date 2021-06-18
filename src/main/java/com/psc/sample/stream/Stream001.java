package com.psc.sample.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Stream001 {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);

        List<Integer> result = new ArrayList<>();

        // 3보다 크고 9보다 작은 수의 모음
        result = list.stream()
                .filter(x -> x > 3)
                .filter(x -> x < 9)
                .map(x -> x * x)
                .collect(Collectors.toList());
        System.out.println("=> " + result);

        Optional<Integer> optional = list.stream()
                .filter(x -> x > 3)
                .filter(x -> x < 9)
                .map(x -> x * x )
                .findFirst();

        System.out.println("=> " + optional.orElse(null));
    }


}
