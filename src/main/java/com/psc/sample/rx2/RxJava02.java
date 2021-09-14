package com.psc.sample.rx2;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class RxJava02 {

    public static void main(String[] args) {

        RxJava02 rxJava02 = new RxJava02();
        //rxJava02.flatMap();
        //rxJava02.concatMap();
        //rxJava02.concatMapEager();
        rxJava02.merge();

    }

    /**
     *  flatMap 새로운 것을 리턴하게 하지만 순서 보장은 불가능
     */
    public void flatMap() {

        if (false) {
            Flowable<Integer> flowable1 = Flowable.range(1, 1000).flatMap(data -> {
                if ((data % 3) == 0) {
                    return Flowable.just(data);
                } else {
                    return Flowable.empty();
                }
            }).doOnNext(data -> System.out.println("================>" + data));
            flowable1.subscribe(data -> System.out.println(data));
            threadSleep(10, true);
        }


        if (true) {
            Flowable<Integer> flowable2 = Flowable.range(1, 1000).flatMap(data -> {
                if ((data % 3) == 0) {
                    return Flowable.just(data).delay(1L, TimeUnit.SECONDS);
                } else {
                    return Flowable.empty();
                }
            }).doOnNext(data -> System.out.println("=====> " + data));
            flowable2.subscribe(data -> System.out.println(data));

            threadSleep(10, true);
        }
    }

    /**
     *  concatMap 새로운 것을 리턴하게 하고 단일 쓰레드
     *  순서 보장은 되지만 비용이 크다. 완전 느림 안쓰는 것이 정신건강
     */
    public void concatMap() {
        if (true) {
            Flowable<Integer> flowable2 = Flowable.range(1, 1000).concatMap(data -> {
                if ((data % 3) == 0) {
                    return Flowable.just(data).delay(1L, TimeUnit.SECONDS);
                } else {
                    return Flowable.empty();
                }
            }).doOnNext(data -> System.out.println("=====> " + data));
            flowable2.subscribe(data -> System.out.println(data));

            threadSleep(10, true);
        }
    }

    /**
     *  concatMapEager 새로운 것을 리턴하게 하고 순서 보장은 되고 멀티 쓰레드이지만
     *  데이터 순서, 속도 중요하다.
     *  메모리 OOM 날 가능성이 있음
     */
    public void concatMapEager() {
        if (true) {
            Flowable<Integer> flowable2 = Flowable.range(1, 1000).concatMapEager(data -> {
                if ((data % 3) == 0) {
                    return Flowable.just(data).delay(1L, TimeUnit.SECONDS);
                } else {
                    return Flowable.empty();
                }
            }).doOnNext(data -> System.out.println("=====> " + data));
            flowable2.subscribe(data -> System.out.println(data));

            threadSleep(10, true);
        }
    }


    public void merge(){
        Flowable<Integer> source1 = Flowable.range(1,100).subscribeOn(Schedulers.computation()).observeOn(Schedulers.computation());

        Flowable<Integer> source2 = Flowable.range(101,100).subscribeOn(Schedulers.computation()).observeOn(Schedulers.computation());



        Flowable.merge(source1, source2).subscribe(s -> System.out.println(s));

        threadSleep(10, true);
    }




    public void threadSleep(int time, boolean isDebug){
        try {
            if(isDebug){
                System.out.println("                                 " + time +"초 sleep " + Thread.currentThread().getName());
            }
            Thread.sleep(time * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
