package com.psc.sample.rx2;

import com.psc.sample.util.CustomSubscriber;
import com.psc.sample.util.ThreadUtil;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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

        // take takeUtil takeWhile takeLast 나한테 몇 개나 줄 수 있어요?
        rxJava03.take();
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

    // take
    public void take(){

        if(false){
            Flowable.range(1, 100).take(10).subscribe(new CustomSubscriber());
        }

        // 33이 될때까지
        if(false){
            Flowable.range(1, 100).takeUntil(data -> data == 33).subscribe(new CustomSubscriber());
        }

        // 처음부터 특정조건까지
        if(false){
            Flowable.range(1, 100).takeWhile(data -> data < 10).subscribe(new CustomSubscriber());
        }

        // 마지막 몇개
        if(true){
            Flowable.range(1, 100).takeLast(10).subscribe(new CustomSubscriber());
        }

        ThreadUtil.sleep(3,false);



    }
}
