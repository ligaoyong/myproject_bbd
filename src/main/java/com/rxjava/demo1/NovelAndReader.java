package com.rxjava.demo1;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import org.junit.Test;

/**
 * 小说：Observable
 * 读者：Observer
 */
public class NovelAndReader {
    @Test
    public void test1(){
        //小说 被观察者
        Observable<String> novel = Observable.create((ObservableEmitter<String> emitter) -> {
            emitter.onNext("连载1");
            emitter.onNext("连载2");
            emitter.onNext("连载3");
            emitter.onComplete();
        });

        //读者 观察者
        Observer<String> reader = new Observer<String>() {

            //这个方法什么时候被调用？
            @Override
            public void onSubscribe(Disposable disposable) {
                System.out.println("onSubscribe : " + disposable);
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext : "+ s);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("onError : "+throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };
        //写法有些奇怪：被观察者去订阅观察者  这是为了保持去链式编程得特性
        novel.subscribe(reader);
    }
}
