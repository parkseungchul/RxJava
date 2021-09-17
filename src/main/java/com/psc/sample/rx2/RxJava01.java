/**
 * RxJava 2.0 reactive stream 구현
 * reactive stream 데이터 스트림을 비동기로 처리하는 것을 의미
 *
 * 데이터를 가져와서 처리하는 것이 아니고
 * 데이터를 받은 시점에 반응해서 처리
 * 데이터 보내는 publisher 열심히 보내고 데이터를 소비하는 subscriber 는 열심히 소비하면 됨
 * 마이크로 서비스와 어울림.
 *
 *
 */
package com.psc.sample.rx2;

import com.psc.sample.ThreadUtil;
import io.reactivex.*;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Publisher;
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

        // RxJava 기본 컨셉
        //rxJava01.basicConcept_001();

        // 생산자 타입 cold hot
        //rxJava01.coldHot_002();

        // 구독 해지 disposable
        //rxJava01.disposable_003();

        // 구독 과정 보기
        //rxJava01.newSubscribe_004();

        // 데이터 타입 가정 (한개, 한개 또는 없음)
        //rxJava01.singleMaybe_005();

        // List 생산자 전략
        //rxJava01.fromIterable_006();


        // just, Interval thread 보기
        //rxJava01.thread_007();

        //back pressure
        //rxJava01.backPressure_008();
    }

    /**
     * 생산자와 소비자
     */
    public void basicConcept_001(){
        // publisher, subscriber 생산자 소비자를 이용한 비동기 통신 (pptx 참고)
        // 배압 통지 속도가 데이터 처리 속도보다 빠를 때 통지량 제어
        // 배압 기능 있음 Reactive Stream 지원
        // 생산자 (흐름이 가능한 RxJava2)

        Publisher<Integer> a;      // 생산자
        Subscriber<Integer> c;     // 구독자


        // 구독자  (RxJava2)
        Flowable<Integer> flowable = Flowable.just(1,2,3,4,5,6);
        //flowable.subscribe(data -> System.out.println(data));
        flowable.subscribe(data -> System.out.println(data), error -> error.printStackTrace(), () -> System.out.println("완료"));


        Observable<Integer> d;  // 생산자
        Observer<Integer> e;    // 구독자



        // observe - observable
        // 배압 기능 없음 Reactive Stream 지원
        Observable<Integer> integerObservable = Observable.just(1,2,3,4,5,6);
        integerObservable.subscribe(data -> System.out.println(data));


        // 단건 생산자 (Spring Reactor)
        //Mono<Integer> integerMono = Mono.just(1);
       // integerMono.subscribe(data -> System.out.println(data));

        // 다건 생산자 (Spring Reactor)
        //Flux<Integer> integerFlux = Flux.just(1,2,3,4,5);
        //integerFlux.sample(data -> System.out.println(data));

        ThreadUtil.Sleep(10, false);
    }

    /**
     * 생산자와 소비자 cold, hot
     * hot 은  connect 를 해야 시작임...
     * hot 와 cold를 비교 pptx 참고
     */
    public void coldHot_002(){
        //https://medium.com/tompee/rxjava-ninja-hot-and-cold-observables-19b30d6cc2fa
        // publisher, subscriber 생산자 소비자를 이용한 비동기 통신 (pptx 참고)
        // 생산자 (흐름이 가능한 RxJava2)


        boolean isClod = true;

        if(isClod){
            Flowable<Long> flowable = Flowable.interval(1, TimeUnit.SECONDS);
            flowable.subscribe(data -> System.out.println("First ===> "+data));
            ThreadUtil.Sleep(5, false);
            flowable.subscribe(data -> System.out.println("Second ===> "+data));
            ThreadUtil.Sleep(5, false);

        // Hot!!!
        }else{
            /**
            ConnectableObservable<Long> observable1 = Observable.interval(1, TimeUnit.SECONDS).publish();
            observable1.connect();
            observable1.subscribe(data -> System.out.println("First ===> "+data));
            threadSleep(5, false);
            observable1.subscribe(data -> System.out.println("Second ===> "+data));
            threadSleep(5, false);
             **/


            ConnectableObservable<Long> observable2 = Observable.just(1L,2L,3L,4L,5L,6L).doOnNext(data ->  ThreadUtil.Sleep(1, false)).publish();
            observable2.connect();
            observable2.subscribe(data -> System.out.println("First ===> "+data));
            ThreadUtil.Sleep(2, false);
            observable2.subscribe(data -> System.out.println("Second ===> "+data));
            ThreadUtil.Sleep(5, false);
        }
    }

    /**
     * 구독 해지
     */
    public void disposable_003(){
        Flowable<Long> flowable = Flowable.interval(1,TimeUnit.SECONDS);
        Disposable disposable = flowable.subscribe(data -> System.out.println(data));
        ThreadUtil.Sleep(5, true);
        disposable.dispose();
        System.out.println("구독해지!!");
        ThreadUtil.Sleep(5, true);


        // 일괄 구독 해지!!!!
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        Disposable disposable1 = flowable.subscribe(data -> System.out.println("1 " + data));
        compositeDisposable.add(disposable1);
        Disposable disposable2 = flowable.subscribe(data -> System.out.println("2 " + data));
        compositeDisposable.add(disposable2);

        ThreadUtil.Sleep(5 , true);
        compositeDisposable.dispose();
    }

    /**
     * subscribe 구조 보기 구동과정 보기
     */
    public void newSubscribe_004(){

        Flowable<Integer> integerFlowable = Flowable.just(1,2,3,4,5,6);

        integerFlowable.subscribe();

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

        ThreadUtil.Sleep(15, true);

    }

    /**
     * 데이터 타입 가정
     */
    public void singleMaybe_005(){

        // 한개라는 가정
        Single<Integer> single = Single.just(1);

        // 한개 또는 없음
        //Maybe<Integer> maybe = Maybe.empty();
        Maybe<Integer> maybe = Maybe.just(1);

        //single.subscribe(i -> {System.out.println(i);});
        maybe.subscribe(i -> System.out.println("---> "+ i ));
        ThreadUtil.Sleep(10 ,false);




    }

    /**
     * List Flowable 로 변경
     */
    private void fromIterable_006() {

        List<Integer> list = Arrays.asList(1,2,3,4,5);
        Flowable.fromIterable(list).subscribe(data -> System.out.println("[1]  "+data));

        Flowable.fromArray(list).subscribe(data -> System.out.println("[2]  "+data));

        ThreadUtil.Sleep(5, true);
    }


    /**
     * 배압
     * just 메인 쓰레드
     * interval 은 다른 쓰레드 (기존 쓰레드 바로 종료)
     * END 가 언제 찍히는지가 중요
     */
    private void thread_007(){

        if(false){
            Flowable.just(1,2,3,4,5,6)
                    .doOnNext(data -> System.out.println(System.currentTimeMillis() +" "+ data)).subscribe(data ->{
                        ThreadUtil.Sleep(2, true);
                    });
            System.out.println("END");
        }

        if(true){
            Flowable.interval(1,TimeUnit.SECONDS)
                    .doOnNext(data -> System.out.println(System.currentTimeMillis() +" "+ data)).subscribe(data ->{
                        ThreadUtil.Sleep(2, true);
                    });
            System.out.println("END");
        }
        ThreadUtil.Sleep(10, true);
    }


    /**
     * Scheduler
     *
     * subscribeOn 생산자에서의 쓰레드 정의 한번 정하면 끝
     * observeOn 소비자에서의 쓰레드 정의함
     */
    public void backPressure_008(){

        if(true){
            Flowable.just(1,2,3,4,5,6)
                    .subscribeOn(Schedulers.computation())
                    .subscribeOn(Schedulers.io())
                    .subscribeOn(Schedulers.single())
                    .subscribe(data ->{
                        ThreadUtil.Sleep(0, true);
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

        ThreadUtil.Sleep(10, false);
    }










}
