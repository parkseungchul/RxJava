package com.psc.sample.rx2;

import com.psc.sample.util.CustomSubscriber;
import com.psc.sample.util.ThreadUtil;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

// 통지하는 데이터를 생성하거나 필터링 변환하는 것을 연산자
public class RxJava03 {

    public static void main(String[] args) {

        RxJava03 rxJava03 = new RxJava03();

        // filter 걸러주는 녀석 (할말하않)
        // rxJava03.filter();

        //distinct, distinctUtilChanged 강아지들의 염원 증복 제거
        //rxJava03.distinct();

        // take takeUtil takeWhile takeLast 나한테 몇 개나 어떻게 줄 수 있어요?
        // rxJava03.take();

        // skip skipUtil skipWhile skipLast 너는 좀 건너뛰자?
        //rxJava03.skip();

        // 쓰라틀,털 - 조절하다
        // throttleFirst throttleLast throttleWithTimeout 특정 조건으로 조절하다.
        rxJava03.throttle();


    }

    // 말이 필요 없지
    public void filter(){

        if(true){
            Flowable.range(1,1000000).filter(data -> data%3 == 0).subscribe(new CustomSubscriber());
        }


        if(false){
            Flowable.range(1,1000000).subscribeOn(Schedulers.computation()).filter(data -> data%3 == 0)
                    .subscribe(new CustomSubscriber());

            ThreadUtil.sleep(1,false);
        }

    }

    // 중복 제거
    public void distinct(){

        // 기본
        if(false){
            Flowable.just(1,1,2,2,1,3,4,5,6,6).distinct().subscribe(new CustomSubscriber());
            ThreadUtil.sleep(3,false);
        }

        // key selector distinct
        if(false){
            Flowable.just("A1", "A2","B1","B2","C1","C2")
                    .distinct(data ->data.substring(0,1)).subscribe(new CustomSubscriber());
        }

        // distinctUtilChanged 연속 데이터
        if(false){
            Flowable.just(1,1,2,2,1,3,4,5,6,6).distinctUntilChanged().subscribe(new CustomSubscriber());
            ThreadUtil.sleep(3,false);
        }

        // 조건 스킵
        if(true){
            Flowable.just("A","a","b","c","C","a").distinctUntilChanged(
                    (data1, data2) ->{
                        return data1.toLowerCase().equals(data2.toLowerCase());
                    }
            ).subscribe(new CustomSubscriber());
            ThreadUtil.sleep(3,false);
        }
    }

    // take 몇개
    public void take(){

        if(false){
            Flowable.range(1, 100).take(10).subscribe(new CustomSubscriber());
        }

        // takeUntil 50 될때까지
        if(false){
            Flowable.range(1,100).takeUntil(data -> data == 50).subscribe(new CustomSubscriber());
        }

        // takeUntil 10초 동안
        if(true){
            Flowable.range(1,20).doOnNext(data -> ThreadUtil.sleep(1, false)).takeUntil(Flowable.timer(10, TimeUnit.SECONDS))
                    .subscribe(new CustomSubscriber());
        }

        // 처음부터 특정조건까지
        if(false){
            Flowable.range(1, 100).takeWhile(data -> data < 10).subscribe(new CustomSubscriber());
        }

        // 마지막 몇개
        if(false){
            Flowable.range(1, 100).takeLast(10).subscribe(new CustomSubscriber());
            ThreadUtil.sleep(3,false);
        }
    }

    // take 몇개
    public void skip(){

        if(false){
            Flowable.range(1, 100).skip(10).subscribe(new CustomSubscriber());
        }


        // skipUtil 10초 동안
        if(false){
            Flowable.range(1,20).doOnNext(data -> ThreadUtil.sleep(1, false)).skipUntil(Flowable.timer(10, TimeUnit.SECONDS))
                    .subscribe(new CustomSubscriber());
        }

        // 처음부터 특정조건까지 skip
        if(false){
            Flowable.range(1, 20).skipWhile(data -> data < 10).subscribe(new CustomSubscriber());
        }

        // 마지막 몇개
        if(true){
            Flowable.range(1, 20).skipLast(10).subscribe(new CustomSubscriber());
            ThreadUtil.sleep(3,false);
        }
    }

    // 쓰라틀
    public void throttle(){

        // 데이터 통지 이후 지정 시간 동안 스킵
        if(false){
            Flowable.interval(1, TimeUnit.SECONDS).throttleFirst(3, TimeUnit.SECONDS)
                    .subscribe(new CustomSubscriber());
            ThreadUtil.sleep(30,false);
        }

        // 지정한 시간마다 스킵하고 마지막에 통지된 데이터만 통지
        if(false){
            Flowable.interval(1, TimeUnit.SECONDS).throttleLast(3, TimeUnit.SECONDS)
                    .subscribe(new CustomSubscriber());
            ThreadUtil.sleep(30,false);
        }

        // 시간 초과하는 것을 리턴하는데 위배 전 값을 리턴함
        if(false){
            AtomicInteger i = new AtomicInteger(1);
            Flowable.interval(1, TimeUnit.SECONDS).map(data -> {
                if(data %5 == 0){
                    ThreadUtil.sleep(2,false);
                    return data;
                }else {
                    return data;
                }
            }).throttleWithTimeout(2, TimeUnit.SECONDS).subscribe(new CustomSubscriber());

            ThreadUtil.sleep(20,false);
        }

        // 시간 초과하는 것을 리턴하는데 위배 전 값을 리턴함
        if(true){
            AtomicInteger i = new AtomicInteger(1);
            Flowable.interval(1, TimeUnit.SECONDS).map(data -> {
                if(data %5 == 0){
                    ThreadUtil.sleep(2,false);
                    return data;
                }else {
                    return data;
                }
            }).debounce(2, TimeUnit.SECONDS).subscribe(new CustomSubscriber());

            ThreadUtil.sleep(25,false);
        }

    }

}
