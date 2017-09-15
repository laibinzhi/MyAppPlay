package com.lbz.android.myappplay.presenter;

import android.util.Log;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.lbz.android.myappplay.bean.Associational;
import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.commom.rx.RxHttpResponseCompose;
import com.lbz.android.myappplay.commom.rx.subscriber.ErrorHandleSubscriber;
import com.lbz.android.myappplay.commom.rx.subscriber.ProgressSubcriber;
import com.lbz.android.myappplay.presenter.contract.SearchAppContract;
import com.lbz.android.myappplay.ui.activity.SearchAppActivity;


import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;


/**
 * Created by elitemc on 2017/9/14.
 */

public class SearchAppPresenter extends BasePresenter<SearchAppContract.ISearchAppModel, SearchAppContract.SearchAppView> {

    @Inject
    public SearchAppPresenter(SearchAppContract.ISearchAppModel mModel, SearchAppContract.SearchAppView mView) {
        super(mModel, mView);
    }

    public void requestAssociational(TextView textView) {

        RxTextView.textChanges(textView)
                .skip(1)
                .debounce(200, TimeUnit.MILLISECONDS)
                .map(new Func1<CharSequence, String>() {
                    @Override
                    public String call(CharSequence charSequence) {
                        return charSequence.toString();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        if (!SearchAppActivity.notRuestAssociational){
                            return true;
                        }else {
                            SearchAppActivity.notRuestAssociational = false;
                            return false;
                        }
                    }
                })
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        if (s.length() >0) {
                            mView.showAssociationalFragment();
                            return true;
                        } else {
                            mView.showSearchHistoryFragment();
                            return false;
                        }
                    }
                })
                .switchMap(new Func1<String, Observable<Associational>>() {
                    @Override
                    public Observable<Associational> call(String s) {
                        return mModel.getAssociational(s);
                    }
                })
                .compose(RxHttpResponseCompose.composeSchedulers())
                .subscribe(new ErrorHandleSubscriber<Associational>(mContext) {


                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(Associational associational) {
                        mView.showAssociationalList(associational.getSuggestion());
                    }

                });

    }

    public void AppListByKeyword(String keyword, final int page) {

         Subscriber subscriber = null;

        if (page == 0) {
            subscriber = new ProgressSubcriber<PageBean>(mContext, mView) {
                @Override
                public void onNext(PageBean pageBean) {
                    if (!pageBean.isHasMore() && pageBean.getListApp().size() == 0) {
                        mView.showNoDataView("没有找到关于'"+pageBean.getKeyWord()+"'的应用");
                    }else {
                        mView.showAppList(pageBean);
                    }
                }
            };
        } else {
            subscriber = new ErrorHandleSubscriber<PageBean>(mContext) {
                @Override
                public void onCompleted() {
                    mView.onLoadMoreComplete();
                }

                @Override
                public void onNext(PageBean pageBean) {
                    mView.showAppList(pageBean);
                }
            };
        }

        mModel.getAppListByKeyword(keyword, page)
                .compose(RxHttpResponseCompose.composeSchedulers())
                .subscribe(subscriber);
    }


    public void getHistoryWordList() {
        mModel.getHistoryWordList()
                .compose(RxHttpResponseCompose.composeSchedulers())
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<String> history) {
                        mView.showHistoryList(history);
                    }

                });
    }

}
