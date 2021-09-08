package com.psc.sample.rx2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Stream001_형변환 {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] numbers = StreamData.numbers;
        String[] stringNumbers = StreamData.stringNumbers;

        Stream001_형변환 stream001 = new Stream001_형변환();
        //stream001.int2String(numbers);
        //stream001.int2Long(numbers);
        //stream001.int2Character(numbers);
        //stream001.int2long(numbers);
        stream001.int2Vo(numbers);

        //stream001.str2int(stringNumbers);



    }

    /**
     * int[] -> String[]
     * @param numbers
     */
    public void int2String(int [] numbers){
      String []after = Arrays.stream(numbers).mapToObj(s -> String.valueOf(s)).toArray(String[]::new);
      //String []after = Arrays.stream(numbers).mapToObj(String::valueOf).toArray(String[]::new);
      StreamData.objectPrint(after);

        List<String> after2 = Arrays.stream(numbers).mapToObj(String::valueOf).collect(Collectors.toList());
        StreamData.listPrint(after2);
    }

    /**
     * int[] -> Long[]
     * @param numbers
     */
    public void int2Long(int [] numbers){
//      Long []after = Arrays.stream(numbers).mapToObj(s -> Long.valueOf(s)).toArray(Long[]::new);
//      Long []after = Arrays.stream(numbers).mapToObj(Long::valueOf).toArray(Long[]::new);
//      StreamData.objectPrint(after);

        List after2 =  Arrays.stream(numbers).mapToObj(Long::valueOf).collect(Collectors.toList());
        StreamData.listPrint(after2);
    }

    /**
     * int[] -> long[]
     * @param numbers
     */
    public void int2long(int [] numbers){
        long []after = Arrays.stream(numbers).mapToLong(Long::valueOf).toArray();
        StreamData.longPrint(after);

        List<Long> after2 = Arrays.stream(numbers).mapToObj(Long::valueOf).collect(Collectors.toList());
        StreamData.listPrint(after2);
    }

    /**
     *  int[] -> Character[]
     * @param number
     */
    public void int2Character(int [] number){
        Character[] after = Arrays.stream(number).mapToObj(s -> (char)s).toArray(Character[]::new);
        StreamData.objectPrint(after);
    }

    /**
     * String[] -> int[]
     * @param strings
     */
    public void str2int(String [] strings) {
        int[] after = Arrays.stream(strings).mapToInt(Integer::parseInt).toArray();
        StreamData.intPrint(after);
        //Integer[] after2 = Arrays.stream(strings).map(s -> Integer.parseInt(s)).toArray(Integer[]::new);
        Integer[] after2 = Arrays.stream(strings).map(Integer::parseInt).toArray(Integer[]::new);
        StreamData.integerPrint(after2);

    }

    /**
     * int -> Data002 VO
     * @param ints
     */
    public void int2Vo(int [] ints){
        StreamData.Data[] after = Arrays.stream(ints).mapToObj(i ->{
            return new StreamData.Data(i, (long)i, String.valueOf(i));
        }).toArray(StreamData.Data[]::new);
        StreamData.dataPrint(after);

        List<StreamData.Data> after2 = Arrays.stream(ints).mapToObj(i ->{
            return new StreamData.Data(i, (long)i, String.valueOf(i));
        }).collect(Collectors.toList());
        StreamData.dataPrint(after2);

    }




}
