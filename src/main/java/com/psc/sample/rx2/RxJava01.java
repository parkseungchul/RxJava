package com.psc.sample.rx2;

import io.reactivex.*;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RxJava01 {


    public static void main(String[] args) {

        RxJava01 rxJava01 = new RxJava01();
        rxJava01.default001();
        //rxJava01.default002();
        //rxJava01.default003();
        //rxJava01.default004();
        //rxJava01.default005();
        //rxJava01.default006();
        //rxJava01.default007();
        //rxJava01.default008();
    }

    /**
     * 생산자와 소비자
     */
    public void default001(){
        // publisher, subscriber 생산자 소비자를 이용한 비동기 통신 (pptx 참고)
        // 배압 통지 속도가 데이터 처리 속도보다 빠를 때 통지량 제어
        // 배압 기능 있음 Reactive Stream 지원
        // 생산자 (흐름이 가능한 RxJava2)
        Flowable<Integer> flowable = Flowable.just(1,2,3,4,5,6);
        // 구독자  (RxJava2)
        //flowable.subscribe(data -> System.out.println(data));
        flowable.subscribe(data -> System.out.println(data), error -> error.printStackTrace(), () -> System.out.println("완료"));


        // 배압 기능 없음 Reactive Stream 지원
        Observable<Integer> integerObservable = Observable.just(1,2,3,4,5,6);
        integerObservable.subscribe(data -> System.out.println(data));


        // 단건 생산자 (Spring Reactor)
        Mono<Integer> integerMono = Mono.just(1);
        integerMono.subscribe(data -> System.out.println(data));

        // 다건 생산자 (Spring Reactor)
        Flux<Integer> integerFlux = Flux.just(1,2,3,4,5);
        integerFlux.sample(data -> System.out.println(data));
    }

    /**
     * 생산자와 소비자 cold, hot
     * hot 은  connect 를 해야 시작임...
     * hot 와 cold를 비교 pptx 참고
     */
    public void default002(){
        //https://medium.com/tompee/rxjava-ninja-hot-and-cold-observables-19b30d6cc2fa
        // publisher, subscriber 생산자 소비자를 이용한 비동기 통신 (pptx 참고)
        // 생산자 (흐름이 가능한 RxJava2)
        //Flowable<Long> flowable = Flowable.interval(1, TimeUnit.SECONDS);
        //ConnectableFlowable<Long> flowable = Flowable.interval(1, TimeUnit.SECONDS).publish();
        ConnectableObservable<Long> flowable = Observable.interval(1, TimeUnit.SECONDS).publish();

        flowable.connect();
        flowable.subscribe(data -> System.out.println("First ===> "+data));

        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        flowable.subscribe(data -> System.out.println("Second ===> "+data));

        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 구독 해지
     */
    public void default003(){
        Flowable<Long> flowable = Flowable.interval(1,TimeUnit.SECONDS);
        Disposable disposable = flowable.subscribe(data -> System.out.println(data));
        threadSleep(5, true);
        disposable.dispose();
        System.out.println("구독해지!!");
        threadSleep(5, true);


        // 일괄 구독 해지!!!!
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        Disposable disposable1 = flowable.subscribe(data -> System.out.println("1 " + data));
        compositeDisposable.add(disposable1);
        Disposable disposable2 = flowable.subscribe(data -> System.out.println("2 " + data));
        compositeDisposable.add(disposable2);

        threadSleep(5 , true);
        compositeDisposable.dispose();
    }

    /**
     * subscribe 구조 보기
     */
    public void default004(){

        Flowable<Integer> integerFlowable = Flowable.just(1,2,3,4,5,6);
        integerFlowable.subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                System.out.println("onSubscribe start");
                s.request(Long.MAX_VALUE);  // 몇개 구독할 것이 알려줘야 함
            }
            @Override
            public void onNext(Integer integer) {
                System.out.println("onNext:" + integer);
            }
            @Override
            public void onError(Throwable t) {
                System.out.println("onError");
            }
            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });

        threadSleep(15, true);

    }

    /**
     * 데이터 타입 가정
     */
    public void default005(){

        // 한개라는 가정
        Single<Integer> single = Single.just(1);

        // 한개 또는 없음
        //Maybe<Integer> maybe = Maybe.empty();
        Maybe<Integer> maybe = Maybe.just(1);

        //single.subscribe(i -> {System.out.println(i);});
        maybe.subscribe(i -> System.out.println("---> "+ i ));
        threadSleep(10 ,false);

    }

    /**
     * List Flowable 로 변경
     */
    private void default006() {

        List<Integer> list = Arrays.asList(1,2,3,4,5);
        Flowable.fromIterable(list).subscribe(data -> System.out.println("[1]  "+data));

        Flowable.fromArray(list).subscribe(data -> System.out.println("[2]  "+data));

        threadSleep(5, true);
    }


    /**
     * 배압
     * just 메인 쓰레드
     * interval 은 다른 쓰레드 (기존 쓰레드 바로 종료)
     */
    private void default007(){

        if(false){
            Flowable.just(1,2,3,4,5,6)
                    .doOnNext(data -> System.out.println(System.currentTimeMillis() +" "+ data)).subscribe(data ->{
                        threadSleep(2, true);
                    });
            System.out.println("END");
        }

        if(true){
            Flowable.interval(1,TimeUnit.SECONDS)
                    .doOnNext(data -> System.out.println(System.currentTimeMillis() +" "+ data)).subscribe(data ->{
                        threadSleep(2, true);
                    });
            System.out.println("END");
        }
        threadSleep(10, true);
    }


    /**
     * Scheduler
     *
     * subscribeOn 생산자에서의 쓰레드 정의 한번 정하면 끝
     * observeOn 소비자에서의 쓰레드 정의함
     */
    public void default008(){

        if(true){
            Flowable.just(1,2,3,4,5,6)
                    .subscribeOn(Schedulers.computation())
                    .subscribeOn(Schedulers.io())
                    .subscribeOn(Schedulers.single())
                    .subscribe(data ->{
                        threadSleep(0, true);
                    });
        }

        if(true){
            // 별도의 쓰레드를 사용하여 배압 전략
            Flowable<Long> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS).doOnNext(data ->
                                    System.out.print("")
                            //System.out.println("=====> " + data)
                    )
                    // .onBackpressureDrop();      // 단순히 오버플로워된 것을 드랍
                    // .onBackpressureBuffer();       // 배압이 될만한 것을 버퍼에 넣어서 배출 // 순서대로 버퍼에 넣고 돌림 나중에 문제가 될듯?
                    //.onBackpressureBuffer(2, ()->System.out.print(""), BackpressureOverflowStrategy.DROP_LATEST); // 최신것을 삭제
                    .onBackpressureBuffer(2, ()->System.out.print(""), BackpressureOverflowStrategy.DROP_OLDEST); // 오래된것을 삭제


            flowable.observeOn(Schedulers.computation(),false, 2)
                    .subscribe(d -> {
                        Thread.sleep(1000L);
                        System.out.println(d);
                    });
        }

        threadSleep(10, false);
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
