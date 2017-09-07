package com.lbz.android.myappplay.commom.rx;

import com.lbz.android.myappplay.bean.BaseHttpResultBean;
import com.lbz.android.myappplay.commom.exception.ApiException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by elitemc on 2017/7/14.
 */
public class RxHttpResponseCompose {

    public static <T> Observable.Transformer<BaseHttpResultBean<T>, T> composeResult() {
        return new Observable.Transformer<BaseHttpResultBean<T>, T>() {
            @Override
            public Observable<T> call(Observable<BaseHttpResultBean<T>> baseHttpResultBeanObservable) {

                return baseHttpResultBeanObservable.flatMap(new Func1<BaseHttpResultBean<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(final BaseHttpResultBean<T> tBaseHttpResultBean) {
                        if (tBaseHttpResultBean.success()) {
                            return Observable.create(new Observable.OnSubscribe<T>() {
                                @Override
                                public void call(Subscriber<? super T> subscriber) {
                                    try {
                                        subscriber.onNext(tBaseHttpResultBean.getData());
                                        subscriber.onCompleted();
                                    } catch (Exception e) {
                                        subscriber.onError(e);
                                    }
                                }
                            });
                        } else {
                            return Observable.error(new ApiException(tBaseHttpResultBean.getCode(), tBaseHttpResultBean.getMessage()));
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}
