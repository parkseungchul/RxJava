package com.psc.sample.rxjava;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class RxJava001 {

    public static void main(String[] args) {
        RxJava001 rxJava001 = new RxJava001();
        //rxJava001.test001_비동기통신();
        //rxJava001.test002_반복();
        //rxJava001.test003_콜드생산자();
        //rxJava001.test004_핫생산자();
        //rxJava001.test005_구독취소();
        //rxJava001.test006_구독일괄취소();
        //rxJava001.test007_에러처리();
        rxJava001.test008_flatMap();
    }

    /**
     * 비동기 통신임
     * Hello1, World1 이런순으로 동작하지 않음
     * Publisher, Subscriber
     */
    public void test001_비동기통신(){

        // Publisher 데이터를 통지하는 생산자
        Flowable<String> flowable = Flowable.just("Hello1", "World1");
        // Subscriber 데이터를 받아 처리하는 소비자
        flowable.subscribe(data -> System.out.println(data));
        System.out.println();

        // Spring Reactor 다건
        Flux<String> flux = Flux.just("Hello2", "World2");
        flux.subscribe(data -> System.out.println(data));
        System.out.println();

        // String Reactor 단건
        Mono<String> mono = Mono.just("Hello3");
        mono.subscribe(data -> System.out.println(data));
    }


    /**
     * publisher vs subscriber
     */
    public void test002_반복(){

        /**
         * 비동기 통신 주요 컨셉은 앞에 프로그램 종료 여부 없이 그냥 진행
         *  interval 속도에 따라 출력 속도가 달라짐
         *
         */
        Flowable<Long> integers = Flowable
                .interval(1000L, TimeUnit.MILLISECONDS).take(1000).map(i -> Long.valueOf(i));

        integers.subscribe(i -> System.out.println(i));
        //while(true){
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        //}
    }

    void test003_콜드생산자(){
        Flowable<Long> integers = Flowable
                .interval(100L, TimeUnit.MILLISECONDS).take(10).map(i -> Long.valueOf(i));

        ArrayList<Long> aList = new ArrayList();
        integers.subscribe(a -> aList.add(a));

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<Long> bList = new ArrayList();
        integers.subscribe(a -> bList.add(a));

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(aList);
        System.out.println(bList);
    }

    void test004_핫생산자(){

    }

    /**
     * 구독 취소
     */
    void test005_구독취소(){
        Flowable<Long> integers = Flowable
                .interval(100L, TimeUnit.MILLISECONDS).map(i -> Long.valueOf(i));

        Disposable disposable = integers.subscribe(a -> System.out.println("===========>"+a));

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        disposable.dispose();
    }

    /**
     * 일괄 구독 취소
     */
    void test006_구독일괄취소(){
        CompositeDisposable compositeDisposable = new CompositeDisposable();

        Flowable<Long> integers = Flowable
                .interval(100L, TimeUnit.MILLISECONDS).map(i -> Long.valueOf(i));

        Disposable disposable1 = integers.subscribe(a -> System.out.println(">>>>>>>>>>>"+a));
        compositeDisposable.add(disposable1);

        Disposable disposable2 = integers.subscribe(a -> System.out.println("<<<<<<<<<<"+a));
        compositeDisposable.add(disposable2);
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        compositeDisposable.dispose();
    }

    public void test007_에러처리(){
        Flowable<String> flowable = Flowable
                .just("1","2","3","가","5","6","7","8","9","10");
        flowable.map(x -> Integer.parseInt(x)).subscribe(
                x -> System.out.println("--> "+x)
                ,error -> System.out.println(error)
                ,() -> System.out.println("complete")
        );
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void test008_flatMap(){
        Flowable<String> flowable = Flowable
                .just("1","2","3","가","5","6","7","8","9","10");

        flowable.flatMap(data -> {
            try{
                int x = Integer.parseInt(data);
                return Flowable.just(x);
            }catch (Exception e){
                return Flowable.empty();
            }
        }).subscribe(x -> System.out.println(x));

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
