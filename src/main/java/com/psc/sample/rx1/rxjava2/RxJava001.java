package com.psc.sample.rx1.rxjava2;

import io.reactivex.Flowable;
import io.reactivex.flowables.ConnectableFlowable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RxJava001 {

    /**
     * RxJava2 import
     * Publisher -> Subscribe
     * 생산 -> 소비
     */
    public static void A001(){
        Flowable<Integer> flowable = Flowable.just(1,2,3,4,5,6,7,8,9,10);
        flowable.subscribe(data ->  System.out.print(data));
        System.out.println();
        flowable.subscribe(x -> System.out.print(x));
        System.out.println();

    }

    /**
     * RxJava2 import
     * Publisher -> Subscribe
     * 생산 -> 가공,소비
     */
    public static void A002() throws InterruptedException {
        Flowable<Integer> flowable = Flowable.just(1,2,3,4,5,6,7,8,9,10);
        // 조건
        flowable.filter(x -> (x%2==0)).subscribe(x -> System.out.print(x));
        System.out.println();
        // 갯수
        flowable.filter(x -> (x%2==0)).take(2).subscribe(x -> System.out.print(x));
        System.out.println();

        // 첫번째
        flowable.filter(x -> (x%2==0)).first(1).subscribe(x -> System.out.print(x));
        System.out.println();
        // 첫번째 없을때 디폴트 값
        flowable.filter(x -> (x%100==0)).first(100).subscribe(x -> System.out.print(x));
    }


    /**
     * 비동기임
     * 비동기 처리에는 생산과 소비처리가 외부에 노출되는 잣되는 경우
     */
    private enum TypeCal{
        ADD,SUBTRACTION;
    }
    private static TypeCal typeCal;
    public static void A003() throws InterruptedException {

        typeCal = TypeCal.ADD;
        Flowable<Long> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .take(10).scan((sum, data) ->{
                    if(typeCal == TypeCal.ADD){
                        sum = sum + data;
                    }else{
                        sum = sum - data;
                    }
           return sum;
        });
        flowable.subscribe(data ->  System.out.println("data["+ typeCal +"]=" + data));
        Thread.sleep(900);
        typeCal = TypeCal.SUBTRACTION;
        Thread.sleep(5000);

    }

    /**
     * 에러 처리
     */
    public static void A004() {
        Flowable<String> flowable = Flowable.just("1", "2","3","A");
        flowable.map(x -> Integer.parseInt(x)).onErrorReturn(x -> 0)
                .subscribe(x -> System.out.println(x));


    }

    public static void main(String[] args) throws InterruptedException {

        A001();
        //A002();
        //A003();
        //A004();


/**
        // Case2: 생산 데이터 가공 두 번 후출 값 같음
        Flowable<Integer> flowable2 = Flowable
                .just(1,2,3,4,5,6,7)
                .filter(data -> data % 2 ==0)
                .map(data -> data);
        flowable2.subscribe(data -> System.out.print(data));
        System.out.println();
        flowable2.subscribe(data -> System.out.print(data));
        System.out.println();

        // Case3. fromCallable 를 사용할 경우 호출할때
        Flowable<Long> flowable3 = Flowable.just(System.currentTimeMillis());
        System.out.println(flowable3.subscribe(data -> System.out.println(data)));
        Thread.sleep(1000);
        System.out.println();
        System.out.println(flowable3.subscribe(data -> System.out.println(data)));
        Thread.sleep(1000);
        System.out.println("-----");

        Flowable<Long> flowable3_1 = Flowable.fromCallable(() -> System.currentTimeMillis());
        System.out.println(flowable3_1.subscribe(data -> System.out.println(data)));
        Thread.sleep(1000);
        System.out.println();
        System.out.println(flowable3_1.subscribe(data -> System.out.println(data)));


        Flowable<Integer> flowable4 =
                Flowable.just(1,2,3,4,5,6,7,8,9)
                        .scan((x, y) -> {
                            Integer j = x + y;
                            return  j;
                        });
        flowable4.subscribe(x -> System.out.println(x));
 **/
        }


}
