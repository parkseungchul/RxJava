package com.psc.sample.rx2;

import java.util.List;

public class StreamData {

    static class Data{
        Data(int i, long l, String s){
            this.i = i;
            this.l = l;
            this.s = s;
        }
        int i;
        long l;
        String s;

        @Override
        public String toString() {
            return "Data002{" +
                    "i=" + i +
                    ", l=" + l +
                    ", s='" + s + '\'' +
                    '}';
        }
    }

    public static int [] numbers = new int[]{1,2,3,4,5,6,7,8,9,10,9,8,7,6,5,4,3,2,1};
    public static String [] stringNumbers = new String[]{"1","2","3","4","5","6","7","8","9","10"};
    public static String [] strKrs = new String[]{"가","나","다","라","마","바", "사","아","자","차", "카", "타", "파","하"};
    public static String [] strEns = new String[]{"A","B","C","D","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    public static void listPrint(List list){
        for(Object object: list){
            System.out.print(object + " ");
        }
        System.out.println();
    }

    public static void objectPrint(Object[] objects){
        for(Object obj: objects){
            System.out.print(obj + " ");
        }
        System.out.println();
    }

    public static void longPrint(long[] longs){
        for(long obj: longs){
            System.out.print(obj +" ");
        }
        System.out.println();
    }

    public static void intPrint(int[] ints){
        for(int obj: ints){
            System.out.print(obj +" ");
        }
        System.out.println();
    }

    public static void integerPrint(Integer[] ints){
        for(Integer obj: ints){
            System.out.print(obj +" ");
        }
        System.out.println();
    }

    public static void dataPrint(Data[] data001s) {
        for(Data data001: data001s){
            System.out.print(data001.toString());
        }
        System.out.println();
    }

    public static void dataPrint(List<Data> data001s) {
        for(Data data001: data001s){
            System.out.print(data001.toString());
        }
        System.out.println();
    }
}
