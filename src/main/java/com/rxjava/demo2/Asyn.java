package com.rxjava.demo2;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import org.junit.Test;
import org.objenesis.instantiator.android.Android10Instantiator;

/**
 * 异步模式
 */
public class Asyn {
    @Test
    public void test1(){
        Observable.create(observableEmitter -> {
            observableEmitter.onNext("连载1");
            observableEmitter.onNext("连载2");
            observableEmitter.onNext("连载3");
            observableEmitter.onComplete();
        });
    }
}
