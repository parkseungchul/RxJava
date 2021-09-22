package com.psc.sample.rx2;

import com.psc.sample.util.CustomSubscriber;
import com.psc.sample.util.ThreadUtil;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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

        // map 데이터 변환
        //rxJava02.map();

        // floatMap Multi Thread 순서 보장 안됨 (얍삽한 토끼)
        //rxJava02.flatMap();

        // concatMap Single Thread 순서 보장 (느린 거북이)
        // rxJava02.concatMap();

        // 이거
        // concatMapEager Multi Thread 순서 보장 (빠른 거북이 무리하다 죽을 수 있음)
        //rxJava02.concatMapEager();

        // merge 두 개의 publisher 합체
        //rxJava02.merge();

        //retry 재 처리
        //rxJava02.retry();

        // onErrorReturn 에러 처리
        //rxJava02.onErrorReturn();

        // toList Single 만들어주는 마법 (OOM 위험 한방에 훅!!!)
        //rxJava02.toList();

        // toMap Single Map 만들기
        // rxJava02.toMap();

        // toMultiMap Single MultiMap 만들기
        // rxJava02.toMultiMap();
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

    public void map(){
        Flowable.just("a", "b","c").map(data -> data.toUpperCase()).subscribe(new CustomSubscriber());
    }

    /**
     *  flatMap 새로운 것을 리턴하게 하지만 순서 보장은 불가능
     */
    public void flatMap() {

        // 기본 문법
        if(false){
            Flowable.range(1, 1000).flatMap(data -> {
                if((data % 3) == 0){
                    return Flowable.just(data);
                }else{
                    return Flowable.empty();
                }
            }).doOnNext(data -> System.out.print(data)).subscribe(data -> System.out.println(" " + data));
        }

        // 응용, 두번째 인자
        if(false){
            Flowable.range(1, 1000).flatMap(data -> {
                        if((data % 3) == 0){
                            return Flowable.just(data + data);
                        }else{
                            return Flowable.empty();
                        }},
                    (orgData, newData) ->{
                        return "["+ orgData + "] " + newData;
                    }
            ).subscribe(data -> System.out.println(data));
        }

        // 응용, 에러 처리
        if(false){
            Flowable<Integer> stringFlowable = Flowable.just("1","2","가","4").map(data -> Integer.parseInt(data));
            stringFlowable.flatMap(
                    data -> Flowable.just(data),
                    error -> Flowable.just(-1),
                    () -> Flowable.just(999)
            ).subscribe(new CustomSubscriber());

            ThreadUtil.sleep(10,true);
        }

        // 마치 순서가 지켜지는 것처럼 보이지만
        if (false) {
            Flowable<Integer> flowable1 = Flowable.range(1, 1000).flatMap(data -> {
                if ((data % 3) == 0) {
                    return Flowable.just(data);
                } else {
                    return Flowable.empty();
                }
            }).doOnNext(data -> System.out.print(ThreadUtil.getThreadName() + " publisher ================> " + data ));
            flowable1.subscribe(data -> System.out.println(" [[ subscriber: " + data));
            ThreadUtil.sleep(10, true);

        }

        // publisher 에 조건이 걸어진다면 순서를 보장할 수 없다.
        // 일렬로 서서 들어왔다면 이런 속도 불가능 한번 들어오는데 1초씩 걸리는데 한줄로 들어오는거냐?
        // 절대 아니다.
        if (true) {
            Flowable<Integer> flowable2 = Flowable.range(1, 1000).flatMap(data -> {
                if ((data % 3) == 0) {
                    return Flowable.just(data).delay(1L, TimeUnit.SECONDS);
                } else {
                    return Flowable.empty();
                }
            }).doOnNext(data -> System.out.print(ThreadUtil.getThreadName() + " publisher ================> " + data ));
            flowable2.subscribe(data -> System.out.println(" [[ subscriber: " + data));

            ThreadUtil.sleep(10, true);
        }






    }

    /**
     *  concatMap 새로운 것을 리턴하게 하고 단일 쓰레드
     *  순서 보장은 되지만 비용이 크다. 완전 느림 안쓰는 것이 정신건강
     */
    public void concatMap() {
        // 거북이 속도
        if (false) {
            Flowable<Integer> flowable2 = Flowable.range(1, 1000).concatMap(data -> {
                if ((data % 3) == 0) {
                    return Flowable.just(data).delay(1L, TimeUnit.SECONDS);
                } else {
                    return Flowable.empty();
                }
            }).doOnNext(data -> System.out.println("=====> " + data));
            flowable2.subscribe(data -> System.out.println(data));

            ThreadUtil.sleep(10, true);
        }


        // 반복 카운터 만들기 1 ~ 10 반복
        if (true) {
            Flowable<Long> flowable2 = Flowable.interval(0, TimeUnit.SECONDS).concatMap(data ->{
                return Flowable.rangeLong(1,10).map(data2 -> data2);
            }).doOnNext(data -> ThreadUtil.sleep(1, false));

            flowable2.subscribe(data -> System.out.println(data));

            ThreadUtil.sleep(21, false);
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

            ThreadUtil.sleep(10, true);
        }
    }

    /**
     * 두 개의 stream 을 모음
     */
    public void merge(){
        Flowable<Integer> source1 = Flowable.range(1,100).subscribeOn(Schedulers.computation()).observeOn(Schedulers.computation());
        Flowable<Integer> source2 = Flowable.range(101,100).subscribeOn(Schedulers.computation()).observeOn(Schedulers.computation());
        Flowable.merge(source1, source2).subscribe(s -> System.out.println(s));
        ThreadUtil.sleep(10, true);
    }

    /**
     * 생산자 에러 재처리 도전
     */
    public void retry(){
        Flowable<String> flowable = Flowable.just("1","2", "삼","4","오","6").map(data -> String.valueOf(Integer.parseInt(data))).retry(3);
        flowable.subscribeOn(Schedulers.computation()).subscribe(data -> System.out.println(data));
        ThreadUtil.sleep(10, false);
    }

    /**
     * 생산자 에러 리턴 처음 에러나오면 끝
     */
    public void onErrorReturn() {
        Flowable<String> flowable = Flowable.just("1","2", "삼","4","오","6").map(data -> String.valueOf(Integer.parseInt(data))).onErrorReturn(data -> {
            return "-1";
        });
        flowable.subscribeOn(Schedulers.computation()).subscribe(data -> System.out.println(data));
        ThreadUtil.sleep(10, false);
    }

    /**
     * 한방에 훅 가는
     */
    public void toList(){

        // single 확인
        Single<List<Integer>> single = Flowable.just(1,2,3,4,5).toList();
        single.subscribe(data -> System.out.println(data));
        ThreadUtil.sleep(3, false);

    }

    public void toMap(){
        // Atomic 원자의 ->
        // 원자성: 더 이상 쪼갤수 없음
        // 멀티 쓰레드에서 한 메소드나 값을 다중으로 접근하게 되면 원하는 값이 안 나올수 있음
        // 쓰레드 안에서의 외부 변수는 그래서 Atomic 변수를 사용함

        if(false){
            AtomicInteger i = new AtomicInteger(1);
            String[] strEns = StreamData.strEns;
            Single<Map<Integer,String>> mapSinge = Flowable.fromArray(strEns).toMap(data -> i.getAndIncrement());
            mapSinge.subscribe(data -> System.out.println(data));
        }

        if(true){
            String[] strEns = StreamData.strEns;
            Single<Map<String,String>> mapSinge = Flowable.fromArray(strEns).toMap(data -> "key_" + data, data -> "value_" + data);
            mapSinge.subscribe(data -> System.out.println(data));
        }
    }

    // 편을 가르는 맵, 이것도 저것도 되는 건 아님
    public void toMultiMap(){

        Single<Map<String, Collection<Integer>>> single = Flowable.range(1, 100).toMultimap(data -> {
            if(data % 3 ==0 ){
                return "3의 배수";
            }else {
                return "3의 배수 아님";
            }
        });
        single.subscribe(data -> System.out.println(data));
    }
}
