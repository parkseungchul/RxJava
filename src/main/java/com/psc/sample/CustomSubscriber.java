package com.psc.sample;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class CustomSubscriber implements Subscriber {

    private boolean isDebug;

    public CustomSubscriber(){

    }
    public CustomSubscriber(boolean isDebug){
        this.isDebug = isDebug;
    }

    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(Object o) {

        if(isDebug){
            System.out.println(ThreadUtil.getThreadInfo()+" "+o);
        }else{
            System.out.println(o);
        }


    }


    @Override
    public void onError(Throwable t) {
        System.out.println("error");

    }

    @Override
    public void onComplete() {
        System.out.println("onComplete");
    }
}
