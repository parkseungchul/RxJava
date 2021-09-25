package com.psc.sample.rx2;

import com.psc.sample.util.CustomSingleObserver;
import com.psc.sample.util.CustomSubscriber;
import com.psc.sample.util.ThreadUtil;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BiPredicate;
import io.reactivex.schedulers.Schedulers;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class RxJava04 {
    public static void main(String[] args) {

        RxJava04 rxJava04 = new RxJava04();

        // 상태를 알려주는 연산자 (미팅 버전으로 풀이)

        // isEmpty (그 사람) 그지니?
        //rxJava04.isEmpty();

        // contains (돈) 좀 있니?
        // rxJava04.contains();

        //  all 모든 조건을 가지고 있는거지? (조건 맞선)
        // rxJava04.all();

        // sequenceEqual (너희들이) 같은 부류구나?
        // rxJava04.sequenceEqual();

        // count (그 사람 차가) 몇 개야?
        // rxJava04.count();

        // reduce reduceWith (맞선) 결과만 이야기 하자
        // rxJava04.reduce();

        // scan (친구들에게 맞선) 과정과 결과를 이야기 하자
        // rxJava04.scan();

        // repeat repeatWith (언제까지 맞선을) 반복적으로 할래?
        // rxJava04.repeat();

        // delay (맞선을) 미뤄볼까?
        rxJava04.delay();
    }

    public void isEmpty(){
        List list = new ArrayList<String>();
        list.add("a");

        Single<Boolean> booleanSingle = Flowable.defer(() -> Flowable.fromIterable(list)).isEmpty();
        booleanSingle.subscribe(data -> System.out.println("1: "+data));

        list.remove(0);
        booleanSingle.subscribe(data -> System.out.println("2: "+data));
    }

    public void contains(){
        Single<Boolean> booleanSingle = Flowable.fromArray(1,2,3,4,5,6,7).contains(1);
        booleanSingle.subscribe(data -> System.out.println(data));

    }

    //  all 모든 조건에 맞니?
    public void all() {
        Flowable.range(1, 10).all(data -> data <= 10).subscribe(new CustomSingleObserver());
    }

    // sequenceEqual 둘이 같냐?
    public void sequenceEqual(){

        // 같은 수 같은 크기
        if(false){
            Flowable<Integer> s1 = Flowable.range(1,5).observeOn(Schedulers.computation());
            Flowable<Integer> s2 = Flowable.just(1,2,3,4,5).observeOn(Schedulers.computation());
            Flowable.sequenceEqual(s1, s2).subscribe(new CustomSingleObserver());
            ThreadUtil.sleep(3, false);
        }

        // 같은 수 같은 크기 다른 시점이라도 같다고 판단.
        if(false){
            Flowable<Integer> s1 = Flowable.range(1,5).observeOn(Schedulers.computation()).doOnNext(data -> ThreadUtil.sleep(1, false));
            Flowable<Integer> s2 = Flowable.just(1,2,3,4,5).observeOn(Schedulers.computation());
            Flowable.sequenceEqual(s1, s2).subscribe(new CustomSingleObserver());
            ThreadUtil.sleep(10, false);
        }

        // 같다는 것을 커스텀
        if(false){
            Flowable<Integer> s1 = Flowable.range(1,5).observeOn(Schedulers.computation());
            Flowable<Integer> s2 = Flowable.just(11,12,13,14,15).observeOn(Schedulers.computation());
            Flowable.sequenceEqual(s1, s2, (data1, data2) -> {
                if((data1 < 100) && (data2 < 100)){
                    return true;
                }else{
                    return false;
                }
            }).subscribe(new CustomSingleObserver());
            ThreadUtil.sleep(3, false);
        }

        // 다른 수 같은 크기
        if(false){
            Flowable<Integer> s1 = Flowable.range(1,5);
            Flowable<String> s2 = Flowable.just("일","이","삼","사","오");
            Flowable.sequenceEqual(s1, s2).subscribe(new CustomSingleObserver());
        }
    }

    // count 몇개냐?
    public void count(){
        Flowable.range(1,100).concatMapEager(data -> {
            if(data%3==0){
                return Flowable.just(data);
            }else{
                return Flowable.empty();
            }
        }).count().subscribe(new CustomSingleObserver());
    }

    // reduce 그 결과는?
    public void reduce(){

        // 1부터 10까지 합계
        if(false){
            Flowable.range(1,10).reduce(0, (result, data) -> result + data)
                    .subscribe(new CustomSingleObserver());
        }

        // 초기값을 함수
        if(true) {
            Flowable.range(1, 10).reduceWith(() -> {
                Flowable.timer(3, TimeUnit.SECONDS);
                return 0;
            }, (data1, data2) -> data1 + data2).subscribe(new CustomSingleObserver());
        }
    }

    // scan 그 과정은?
    public void scan(){

        // 1부터 10까지 합계 절차를 보여드림
        if(false){
            Flowable.range(1,10).doOnNext(data -> System.out.print("-> [" + data +"] "))
                    .scan(0, (result, data) -> result + data)
                    .subscribe(new CustomSubscriber());
        }

        // 1부터 10까지 합계 절차를 보여드림
        if(true){
            Flowable.range(1,10)
                    .scan(0, new BiFunction<Integer, Integer, Integer>() {
                        @NonNull
                        @Override
                        public Integer apply(@NonNull Integer data1, @NonNull Integer data2) throws Exception {
                            System.out.print(data1 + " + " + data2 + " = ");
                            return data1 + data2;
                        }
                    }).subscribe(new CustomSubscriber());
        }
    }

    // repeat repeatWhen
    public void repeat(){

        // 반복
        if(false){
            Flowable.range(1,10).scan(0, (data1, data2)-> data1 + data2).repeat(2).subscribe(new CustomSubscriber());
        }

        // 외부에 의한 지정된 조건까지
        if(false){
            AtomicInteger atomicInteger = new AtomicInteger();
            Flowable.range(1,10).scan(0, (data1, data2) -> {
                atomicInteger.getAndIncrement();
                return data1 + data2;
            }).repeatUntil(() -> {
                System.out.println(" >>>>> "+ atomicInteger.get());
                return atomicInteger.get() > 50;
            }).subscribe(new CustomSubscriber());
        }

        // 반복마다의 조건을 넣을 수 있음
        if(true){
            AtomicInteger atomicInteger = new AtomicInteger();
            Flowable.range(1,5).repeatWhen(objectFlowable -> objectFlowable.delay(5, TimeUnit.SECONDS).take(2)).subscribe(new CustomSubscriber());
            ThreadUtil.sleep(20,false);
        }


    }

    public void delay(){
        Flowable.range(1,10).delay(5,TimeUnit.SECONDS).subscribe(new CustomSubscriber());
        ThreadUtil.sleep(10,false);
    }
}
