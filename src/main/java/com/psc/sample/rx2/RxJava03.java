package com.psc.sample.rx2;

import com.psc.sample.util.CustomSubscriber;
import com.psc.sample.util.ThreadUtil;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.internal.operators.maybe.MaybeToFlowable;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

// 통지하는 데이터를 생성하거나 필터링 변환하는 것을 연산자
public class RxJava03 {

    public static void main(String[] args) {

        RxJava03 rxJava03 = new RxJava03();

        // filter 걸러주는 녀석 (할말하않)
        rxJava03.filter();

        // 디스팅트 뚜렸한 분명한
        //distinct, distinctUtilChanged 강아지들의 염원 증복 제거
        //rxJava03.distinct();

        // take takeUtil takeWhile takeLast 나한테 몇 개나 어떻게 줄 수 있어요?
        // rxJava03.take();

        // skip skipUtil skipWhile skipLast 너는 좀 건너뛰자?
        //rxJava03.skip();

        // 쓰라틀,털 - 조절하다
        // throttleFirst throttleLast throttleWithTimeout debounce 특정 조건으로 조절하다.
        // rxJava03.throttle();

        // sample 해당 시점에서의 데이터를 샘플링
        // rxJava03.sample();

        // elementAt elementAtOrError 지정 된 값을 가져오거나 대채하던가
        //rxJava03.elementAt();

        // merge mergeDelayError 데이터를 젤 빠르게 잘 연결해 보자.
        //rxJava03.merge();

        // concat concatArrayDelayError 데이터를 느리게 잘 연결 해 보자
        //rxJava03.concat();

        //concatEager concatArrayEagerDelayError 데이터를 좀 빠르게 잘 연결 해 보자
        //rxJava03.concatEager();

        // startWith startWithArray 자신은 나중에 데이터는 먼저
        // rxJava03.startWith();

        // zip 두 개를 합하는 연산자
       // rxJava03.zip();





    }

    // 말이 필요 없지
    public void filter(){

        if(true){
            Flowable.range(1,100).filter(data -> data%3 == 0).subscribe(new CustomSubscriber());
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

    // 특정 조건 샘플링
    public void sample(){

        Flowable.just(1,2,3,4,5).doOnNext(data -> ThreadUtil.sleep(1,false)).sample(Flowable.timer(2,TimeUnit.SECONDS))
                .subscribe(new CustomSubscriber());

        ThreadUtil.sleep(10, false);
    }

    // 특정 아이템 가져오기
    public void elementAt(){

        // 특정 번째 아이템을 가져옴
        if(false){
            Maybe<Integer> integerMaybe = Flowable.range(0,10).elementAt(3);
            integerMaybe.subscribe(i -> System.out.println(i));
            ThreadUtil.sleep(10, false);
        }

        // 해당 아이템이 없을 경우
        if(false){
            Maybe<Integer> integerMaybe = Flowable.range(0,10).elementAt(11);
            integerMaybe.subscribe(i -> System.out.println(i));
            ThreadUtil.sleep(10, false);
        }

        // 해당 아이템이 없을 경우 정해진 값을 리턴함
        if(false){
            Single<Integer> integerMaybe = Flowable.range(0,10).elementAt(11, -1);
            integerMaybe.subscribe(i -> System.out.println(i));
            ThreadUtil.sleep(10, false);
        }

        // 해당 인자가 없음 에러 리턴
        if(true){
            Single<Integer> integerMaybe = Flowable.range(0,10).elementAtOrError(11);
            integerMaybe.subscribe(i -> System.out.println(i));
            ThreadUtil.sleep(10, false);
        }

    }

    // 데이터 합치기
    public void merge(){

        // 메인 쓰레드니까 연결되어 보임
        if(false){
            Flowable<Integer> source1 = Flowable.range(1, 20 );
            Flowable<Integer> source2 = Flowable.range(20, 20 );
            Flowable.merge(source1, source2).subscribe(new CustomSubscriber());
            ThreadUtil.sleep(10,false);
        }


        // 쓰레드를 분리하였으니 교차하면서 나옴
        if(false){
            Flowable<Integer> source1 = Flowable.range(1, 20 ).subscribeOn(Schedulers.computation()).doOnNext(data -> ThreadUtil.sleep(1,false));
            Flowable<Integer> source2 = Flowable.range(20, 20 ).subscribeOn(Schedulers.computation()).doOnNext(data -> ThreadUtil.sleep(1,false));
            Flowable.merge(source1, source2).subscribe(new CustomSubscriber());
            ThreadUtil.sleep(10,false);
        }

        if(false){
            Flowable<Integer> source1 = Flowable.range(1, 20 ).subscribeOn(Schedulers.computation()).doOnNext(data -> {
                ThreadUtil.sleep(1,false);
            }).map(data -> {
                System.out.println("["+data+"]");
                return data/(10-data);
            });
            Flowable<Integer> source2 = Flowable.range(20, 20 ).subscribeOn(Schedulers.computation()).doOnNext(data -> ThreadUtil.sleep(1,false));
            Flowable.merge(source1, source2).subscribe(new CustomSubscriber());
            ThreadUtil.sleep(20,false);
        }

        if(true){
            Flowable<Integer> source1 = Flowable.range(1, 20 ).subscribeOn(Schedulers.computation()).doOnNext(data -> {
                ThreadUtil.sleep(1,false);
            }).map(data -> {
                System.out.println("["+data+"]");
                return data/(10-data);
            });
            Flowable<Integer> source2 = Flowable.range(20, 20 ).subscribeOn(Schedulers.computation()).doOnNext(data -> ThreadUtil.sleep(1,false));
            Flowable.mergeDelayError(source1, source2).subscribe(new CustomSubscriber());
            ThreadUtil.sleep(20,false);
        }
    }

    public void concat(){
        // 중간에 에러 끝 시간은 연결되는 것이므로 40초
        if(true){
            Flowable<Integer> source1 = Flowable.range(1, 20 ).subscribeOn(Schedulers.computation()).doOnNext(data -> {
                ThreadUtil.sleep(1,false);
            }).map(data -> {
                System.out.println("["+data+"]");
                return data/(10-data);
            });
            Flowable<Integer> source2 = Flowable.range(20, 20 ).subscribeOn(Schedulers.computation()).doOnNext(data -> ThreadUtil.sleep(1,false));
            Flowable.concat(source1, source2).subscribe(new CustomSubscriber());
            ThreadUtil.sleep(40,false);
        }

        // 끝까지 에러 끝 시간은 연결되는 것이므로 40초
        if(true){
            Flowable<Integer> source1 = Flowable.range(1, 20 ).subscribeOn(Schedulers.computation()).doOnNext(data -> {
                ThreadUtil.sleep(1,false);
            }).map(data -> {
                System.out.println("["+data+"]");
                return data/(10-data);
            });
            Flowable<Integer> source2 = Flowable.range(20, 20 ).subscribeOn(Schedulers.computation()).doOnNext(data -> ThreadUtil.sleep(1,false));
            Flowable.concatArrayDelayError(source1, source2).subscribe(new CustomSubscriber());
            ThreadUtil.sleep(40,false);
        }
    }

    public void concatEager(){
        // 중간에 에러 끝 시간은 연결되는 것이므로 40초
        if(false){
            Flowable<Integer> s1 = Flowable.range(1,20).subscribeOn(Schedulers.computation()).doOnNext(data -> {
                ThreadUtil.sleep(1, false);
            }).map(data -> {
                System.out.println("["+ data +"] ");
                return data/(10-data);
            });;
            Flowable<Integer> s2 = Flowable.range(20,20).subscribeOn(Schedulers.computation()).doOnNext(data -> ThreadUtil.sleep(1, false));
            List<Flowable<Integer>> list = new ArrayList<>();
            list.add(s1);
            list.add(s2);
            Flowable.concatEager(list).subscribe(new CustomSubscriber());
            ThreadUtil.sleep(40,false);
        }

        // 끝까지 에러 끝 시간은 연결되는 것이므로 40초
        if(true){
            Flowable<Integer> s1 = Flowable.range(1,20).subscribeOn(Schedulers.computation()).doOnNext(data -> {
                ThreadUtil.sleep(1, false);
            }).map(data -> {
                System.out.println("["+ data +"] ");
                return data/(10-data);
            });;
            Flowable<Integer> s2 = Flowable.range(20,20).subscribeOn(Schedulers.computation()).doOnNext(data -> ThreadUtil.sleep(1, false));
            List<Flowable<Integer>> list = new ArrayList<>();
            Flowable.concatArrayEagerDelayError(s1, s2).subscribe(new CustomSubscriber());
            ThreadUtil.sleep(40,false);
        }
    }

    public void startWith(){

        if(false){
            Flowable<Integer> flowable1 = Flowable.range(100, 10).subscribeOn(Schedulers.computation());
            Flowable<Integer> flowable2 = Flowable.range(1, 10).subscribeOn(Schedulers.computation());
            Flowable<Integer> integerFlowable = flowable1.startWith(flowable2);
            integerFlowable.subscribe(new CustomSubscriber());
        }

        if(true){
            Flowable<Integer> flowable1 = Flowable.range(100, 10).subscribeOn(Schedulers.computation());
            Flowable<Integer> integerFlowable = flowable1.startWithArray(1,2,3,4,5,6,7);
            integerFlowable.subscribe(new CustomSubscriber());
        }
        ThreadUtil.sleep(10,false);
    }

    public void zip(){

        // 두개의 데이터를 조합해서 결과를 내보내돼 두개의 갯수가 같아야 함
        if(false){
            Flowable<Integer> flowable1 = Flowable.range(100, 20).subscribeOn(Schedulers.computation());
            Flowable<Integer> flowable2 = Flowable.range(1, 10).subscribeOn(Schedulers.computation());
            Flowable.zip(flowable1, flowable2, (data1, data2) -> {
                System.out.print("["+data1 + " "+ data2 +"] ");
                return data1 + data2;
            }).subscribe(new CustomSubscriber());
        }
        ThreadUtil.sleep(10,false);
    }
}
