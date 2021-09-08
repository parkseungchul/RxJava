package com.psc.sample.rx1.functional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/*
  - function 리턴해준다. Function<T,R> return R
  - consumer 소비해준다. Consumer<T>
  - predicate 판별해준다. Predicate<T> return boolean 참과 거짓을 구분해줘 파라미터로 참 편하지
  - supplier 지지해준다. Supplier<T>
 */
public class Functional001 {
    public static void main(String[] args) {
        // 1. function
        System.out.println(getA(10,20 , (x) -> x + 20));
        // 어 이상해 아까 했던거랑 뭐가 달라...미리 요딴걸 정의해 놓으면 또 functional Interface 안 만들어도 되잖아

        // 2.consumer
        getB("PSC", (x) -> System.out.println("Logging:" + x));

        // 3. predicate
        System.out.println(getC(Arrays.asList(1,2,3,4,5,6,7), (x) -> x > 10));
        System.out.println(getC(Arrays.asList(1,2,3,4,5,6,7), (x) -> x < 10));

        // 4. supplier (LAZY)
        Long start = System.currentTimeMillis();
        getD1(1, heavyValue());
        getD1(0, heavyValue());
        getD1(1, heavyValue());
        System.out.println("default: " + ((System.currentTimeMillis() - start)/1000));

        start = System.currentTimeMillis();
        getD2(1, ()-> heavyValue());
        getD2(0, ()-> heavyValue());
        getD2(1, ()-> heavyValue());
        System.out.println("supplier: " + ((System.currentTimeMillis() - start)/1000));
    }

    public static Integer getA(int x, int y, Function<Integer,Integer> function){
        return function.apply(x);
    }

    public static void getB(String key, Consumer<String> consumer){
         consumer.accept(key);
    }

    public static boolean getC(List<Integer> list, Predicate<Integer> predicate){

        for(Integer number: list){
            if(predicate.test(number)){
                return true;
            }
        }
        return false;
        //Optional<Integer> optional = list.stream().filter(x -> predicate.test(x)).findFirst();
        //return optional.isPresent();
    }

    private static void getD1(int number, String value) {
        if (number == 0) {
            System.out.println("getD1: 0");
        } else {
            System.out.println("getD1:" + value);
        }
    }

    private static void getD2(int number, Supplier<String> supplier){
        if(number == 0){
            System.out.println("getD2: 0");
        }else{
            System.out.println("getD2: " + supplier.get());
        }
    }

    private static String heavyValue(){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "heavy value";
    }
}
