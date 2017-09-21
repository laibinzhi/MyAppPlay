package com.lbz.android.myappplay.commom.rx;

import com.lbz.android.myappplay.bean.BaseHttpResultBean;
import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.commom.exception.ApiException;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by elitemc on 2017/7/14.
 */
public class RxHttpResponseCompose {

    public static <T> ObservableTransformer<BaseHttpResultBean<T>, T> composeResult() {
        return new ObservableTransformer<BaseHttpResultBean<T>, T>() {
            @Override
            public Observable<T> apply(Observable<BaseHttpResultBean<T>> baseHttpResultBeanObservable) {

                return baseHttpResultBeanObservable.flatMap(new Function<BaseHttpResultBean<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(final BaseHttpResultBean<T> tBaseHttpResultBean) {
                        if (tBaseHttpResultBean.success()) {
                            return Observable.create(new ObservableOnSubscribe<T>() {
                                @Override
                                public void subscribe(ObservableEmitter<T> subscriber) {
                                    try {
                                        subscriber.onNext(tBaseHttpResultBean.getData());
                                        subscriber.onComplete();
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



    public static ObservableTransformer composeSchedulers() {
        return new ObservableTransformer<PageBean, PageBean>() {
            @Override
            public Observable<PageBean> apply(Observable<PageBean> pageMiBeanObservable) {
                return pageMiBeanObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}
