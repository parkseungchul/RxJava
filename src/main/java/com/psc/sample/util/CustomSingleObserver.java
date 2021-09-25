package com.psc.sample.util;

import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class CustomSingleObserver implements SingleObserver {

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        System.out.println("onSubscribe");
    }

    @Override
    public void onSuccess(@NonNull Object o) {
        System.out.println(o);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        System.out.println(e.getMessage());

    }
}
