package com.psc.sample.rx1.generic;

import java.util.ArrayList;
import java.util.List;

// 1
class NonGenericType{
    private Object value;
    public NonGenericType(Object value){
        this.value = value;
    }
    public Object getValue(){
        return value;
    }

}
// 2
class GenericType<T>{
    private T t;
    public GenericType(T t){
        this.t = t;
    }
    public T getValue(){
        return t;
    }
}

// 3
class MultiType<K,V>{
    private K k;
    private V v;
    public MultiType(K k, V v){
        this.k = k;
        this.v = v;
    }
    public K getK(){return k;}
    public V getV(){return v;}
}

class A{}
class B extends A{}
class C extends B{}

/**
 1. non generic Type with class
 2. generic Type with Class
 3. MultiGeneric Type with Class
 4. generic Type with method
 5. wildcard generic
 **/
public class Generic001 {

    public static void main(String[] args) {
        // 1. non generic Type with class
        NonGenericType nonGenericType = new NonGenericType(1);
        String resultA = (String) nonGenericType.getValue();

        // 2. generic Type with Class
        GenericType<String> genericTypeA = new GenericType<>("1");
        String resultB = genericTypeA.getValue();

        GenericType<Integer> genericTypeB = new GenericType<>(1);
        Integer resultC = genericTypeB.getValue();

        // 3. MultiGeneric Type with Class
        MultiType<Integer, String> multiType = new MultiType<>(1, "1");
        Integer resultD = multiType.getK();
        String resultE = multiType.getV();

        // 4. generic Type with method
        List list = getA(new MultiType<>(1,"2"), false);

        // 5-1. wildcard generic Type
        getB(new GenericType<>(1));

        // 5-2. wildcard generic Type
        GenericType<A> a = new GenericType<>(new A());
        GenericType<B> b = new GenericType<>(new B());
        GenericType<C> c = new GenericType<>(new C());

        getExtendWildCard(b);
        getExtendWildCard(c);

        getSuperWildCare(b);
        getSuperWildCare(a);
    }

    // 4. generic Type with method
    // 사용 되는 generic 리턴 타입 앞에 선언
    public static <K,V,R> List<R> getA(MultiType<K,V> multiType, R r){
        List<R> list = new ArrayList<R>();
        return list;
    }

    // 5. wildcard generic
    public static void getB(GenericType<?> genericType ){}

    // extends wildcard 아래로
    public static void getExtendWildCard(GenericType<? extends B> genericType ){}

    // super wildcard 위로
    public static void getSuperWildCare(GenericType<? super B> genericType){}
}
