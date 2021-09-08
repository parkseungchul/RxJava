package com.psc.sample.rx2;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class Stream002_필터_정렬 {


    public static void main(String[] args) {
        int[] numbers = StreamData.numbers;
        String[] stringNumbers = StreamData.stringNumbers;

        Stream002_필터_정렬 stream002 = new Stream002_필터_정렬();
        //stream002.intEvenNumberSquare(numbers);
        //stream002.intOrder(numbers);


        stream002.stringsOrder(stringNumbers);
    }


    /**
     * 1. 짝수 만들기
     * 2. 제곱 만들기
     * @param numbers
     */
    public void intEvenNumberSquare(int[] numbers){
        int[] after = Arrays.stream(numbers).filter(s -> s%2==0).toArray();
        StreamData.intPrint(after);

        int[] after2 = Arrays.stream(numbers).filter(s -> s%2==0).map(s -> s*s).toArray();
        StreamData.intPrint(after2);
    }

    /**
     * int[] 정렬
     * @param numbers
     */
    public void intOrder(int[] numbers){

        // int 원본파일을 정렬
        // int 에서는 내림차순이 구현되지 않음
        // 하단의 integer 에서는 가능하고 대안으로 for 문 이용해서 역으로 읽으면 됨
        Arrays.sort(numbers);
        StreamData.intPrint(numbers);

        // 소트 람다식을 양수면 내림 차순
        Integer[] after = Arrays.stream(numbers).mapToObj(s -> (Integer)s).sorted((a,b) -> b - a).toArray(Integer[]::new);
        StreamData.integerPrint(after);

        Arrays.sort(after);
        StreamData.integerPrint(after);

        Arrays.sort(after, Collections.reverseOrder());
        StreamData.integerPrint(after);

    }

    /**
     * String[] 정렬
     */
    public void stringsOrder(String[] stringNumbers){

        // 스트링으로서 오름차순
        Arrays.sort(stringNumbers);
        //StreamData.objectPrint(stringNumbers);

        // 스트링으로서 내림차순 (10 이 뒤에 가 있음)
        Arrays.sort(stringNumbers, Collections.reverseOrder());
        StreamData.objectPrint(stringNumbers);

        // 숫자 스타일로 비교해서 내림차순 (양수 내림차순)
        String [] after = Arrays.stream(stringNumbers).sorted((s1, s2) -> Integer.parseInt(s2) - Integer.parseInt(s1)).toArray(String[]::new);
        StreamData.objectPrint(after);

        // 숫자 스타일로 비교해서 내림차순 (음수 오름차순)
        String [] after2 = Arrays.stream(stringNumbers).sorted((s1, s2) -> Integer.parseInt(s1) - Integer.parseInt(s2)).toArray(String[]::new);
        StreamData.objectPrint(after2);


    }
}
