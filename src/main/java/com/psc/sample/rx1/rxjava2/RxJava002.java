package com.psc.sample.rx1.rxjava2;

import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

enum Type{
    ADD, SUBTRACTION
}

public class RxJava002 {

    private static Type type;

    public static void main(String[] args) throws InterruptedException {

        type = Type.ADD;
        Flowable<Long> flowable2 = Flowable
                .interval(100L, TimeUnit.MILLISECONDS).take(5)
                .scan((x, y) ->{
                    if(type == Type.ADD){
                        return x + y ;
                    }else{
                        return x - y ;
                    }

                });

        //구독을 시작한다.
        flowable2.subscribe(data -> System.out.println("==> "+ data));

      //  type = Type.SUBTRACTION;




        Thread.sleep(2000);

 }
}
