package com.psc.sample.rxjava2;

import io.reactivex.Flowable;

public class RxJava001 {

    public static void main(String[] args) throws InterruptedException {

        // Case1: 생산 소비 바로
        // 생산자
        Flowable<String> flowable1 = Flowable.just("A", "B","C");
        // 소비자
        flowable1.subscribe(data -> { System.out.print(data);  } );
        System.out.println();

        // Case2: 생산 데이터 가공 두 번 후출 값 같음
        Flowable<Integer> flowable2 = Flowable
                .just(1,2,3,4,5,6,7)
                .filter(data -> data % 2 ==0)
                .map(data -> data);
        flowable2.subscribe(data -> System.out.print(data));
        System.out.println();
        flowable2.subscribe(data -> System.out.print(data));
        System.out.println();

        // Case3. fromCallable 를 사용할 경우 호출할때 ㄱ
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
    }
}
