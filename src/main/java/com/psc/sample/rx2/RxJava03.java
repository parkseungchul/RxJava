package com.psc.sample.rx2;

import com.psc.sample.CustomSubscriber;
import com.psc.sample.ThreadUtil;
import io.reactivex.Flowable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class RxJava03 {

    public static void main(String[] args) {

        RxJava03 rxJava03 = new RxJava03();

        // fromArray fromIterable 배열, 리스트 publisher 만들기
        //rxJava03.fromArray_fromIterable();

        // fromCallable 반환값을 끝으로 통지하는 publisher 만들기
        //rxJava03.fromCallable();

        // range 지정한 숫자만큼 통지 publisher 만들기
        //rxJava03.range();

        // interval 지정한 간격만자 숫자 통보 publisher 만들기
        //rxJava03.interval();

        // timer 지정한 시간 지난 후에 0을 리턴하는 publisher 만들기
        //rxJava03.timer();

    }


    public void fromArray_fromIterable(){
        Flowable<Integer> integerFlowable = Flowable.fromArray(1,2,3,4);
        Flowable<Long> longFlowable = Flowable.fromIterable(Arrays.asList(1l,2l,3l,4l,5l,6l));

    }

    public void fromCallable(){
        Flowable.fromCallable(() -> "Done").subscribe(new CustomSubscriber());
    }

    public void range(){
        //Flowable.range(100,5).subscribe(new CustomSubscriber());
        Flowable.range(100, 5).doOnNext(data -> ThreadUtil.Sleep(1,false)).subscribe(new CustomSubscriber());
        ThreadUtil.Sleep(10,false);
    }

    public void interval(){
        Flowable.interval(1, TimeUnit.SECONDS).subscribe(new CustomSubscriber(true));
        ThreadUtil.Sleep(10,true);
    }

    public void timer(){
        Flowable.timer(10L, TimeUnit.SECONDS).subscribe(new CustomSubscriber(true));
        ThreadUtil.Sleep(20, true);
    }

}
