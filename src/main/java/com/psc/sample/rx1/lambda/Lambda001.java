package com.psc.sample.rx1.lambda;

import java.text.SimpleDateFormat;

// 1. 인터페이스 생성
interface Mathematics {
    int calculate(int x, int y);
}
interface Trace<T>{
    void follow(T t);
}
interface  Trace2{
    void follow2();
}

@FunctionalInterface
interface Trace3<T,R>{
    R follow3(T t);
}

// 2. 인터패이스 구현체로 구현
public class Lambda001 implements Mathematics{
    @Override
    public int calculate(int x, int y) {
        return x + y;
    }

    public static void main(String[] args) {
        // 3. 람다란 무엇인가?
        // Lambda 는 수학에서 수학 수식을 단순하게 표현하고 이름 없는 함수라고도 함
        // f(x,y) = (x + y) ==> (x, y) -> x + y

        // 4. Lambda 파라미터와 리턴이 있는 mathematics 표현
        Mathematics mathematics;
        mathematics = (x, y) -> { return x + y;};
        mathematics = (x, y) -> { return x - y;};
        mathematics = (x, y) -> { return x * y;};

        // 5. Lambda 파라미터만 있고 리턴이 없음 + Generic
        Trace trace = (x) -> {
            SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
            String format_time1 = format1.format (System.currentTimeMillis());
            System.out.println(x +" : " + format_time1);
        };
        trace.follow("PSC");

        // 6. Lambda 파라미터와 리턴이 없음
        Trace2 trace2 = () -> System.out.println("follow2");
        trace2.follow2();

        // 7. @FunctionalInterface
        Trace3<Integer,String> trace3 = (x) -> {
            return String.valueOf(x + x);
        };
        System.out.println(trace3.follow3(1));


        // 8. 파라미터에 람다식이 간드아 ~
        Mathematics mathematics1 =  (x,y)-> x + y;
        System.out.println(count(1,1,mathematics1));
        // 추상 메소드가 한개인 인터페이스를 가지고 놀고 있어 .. 농락하는 거지...
        System.out.println(count(1,1,(x,y)-> x + y));
        System.out.println(count(1,1,(x,y)-> x - y));
    }

    public static Integer count(int x, int y, Mathematics mathematics){
        return mathematics.calculate(x, y);
    }
}
