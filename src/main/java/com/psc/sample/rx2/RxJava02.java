package com.psc.sample.rx2;

import com.psc.sample.util.CustomSubscriber;
import com.psc.sample.util.ThreadUtil;
import io.reactivex.Flowable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RxJava02 {

    public static void main(String[] args) {

        RxJava02 rxJava02 = new RxJava02();

        // fromArray fromIterable 배열, 리스트 publisher
        //rxJava02.fromArray_fromIterable();

        // fromCallable 반환값을 끝으로 통지하는 publisher
        //rxJava02.fromCallable();

        // range 지정한 숫자만큼 통지 publisher
        //rxJava02.range();

        // interval 지정한 간격만자 숫자 통보 publisher
        //rxJava02.interval();

        // timer 지정한 시간 지난 후에 0을 리턴하는 publisher
        //rxJava02.timer();
        
        // defer 구독한 시점에 publisher 데이터 생성
        //rxJava02.defer();

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
        Flowable.range(100, 5).doOnNext(data -> ThreadUtil.sleep(1,false)).subscribe(new CustomSubscriber());
        ThreadUtil.sleep(10,false);
    }

    public void interval(){
        Flowable.interval(1, TimeUnit.SECONDS).subscribe(new CustomSubscriber(true));
        ThreadUtil.sleep(10,true);
    }

    public void timer(){
        Flowable.timer(10L, TimeUnit.SECONDS).subscribe(new CustomSubscriber(true));
        ThreadUtil.sleep(20, true);
    }

    public void defer(){

        List<String> list = new ArrayList();
        list.add("a");
        list.add("b");
        list.add("c");

        Flowable<Integer> flowable = Flowable.defer(() -> Flowable.just(list.size(), list.size() + 1));
        flowable.subscribe(data -> System.out.println("1: "+ data));
        
        list.remove(0);
        ThreadUtil.sleep(1, false);
        flowable.subscribe(data -> System.out.println("2: "+ data));
        
        list.remove(0);
        ThreadUtil.sleep(1, false);
        flowable.subscribe(data -> System.out.println("3: "+ data));

        ThreadUtil.sleep(3, false);
    }
}
