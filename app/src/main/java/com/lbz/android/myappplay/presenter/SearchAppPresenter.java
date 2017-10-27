package com.lbz.android.myappplay.presenter;

import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
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

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;


/**
 * Created by lbz on 2017/9/14.
 */

public class SearchAppPresenter extends BasePresenter<SearchAppContract.ISearchAppModel, SearchAppContract.SearchAppView> {

    @Inject
    public SearchAppPresenter(SearchAppContract.ISearchAppModel mModel, SearchAppContract.SearchAppView mView) {
        super(mModel, mView);
    }

    public void requestAssociational(TextView textView, final boolean update) {

        RxTextView.textChanges(textView)
                .skip(1)
                .debounce(200, TimeUnit.MILLISECONDS)
                .map(new Function<CharSequence, String>() {
                    @Override
                    public String apply(CharSequence charSequence) {
                        return charSequence.toString();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(@NonNull String s) throws Exception {
                        if (!SearchAppActivity.notRuestAssociational){
                            return true;
                        }else {
                            SearchAppActivity.notRuestAssociational = false;
                            return false;
                        }
                    }
                })
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(@NonNull String s) throws Exception {
                        if (s.length() >0) {
                            mView.showAssociationalFragment();
                            return true;
                        } else {
                            mView.showSearchHistoryFragment();
                            return false;
                        }
                    }
                })
                .switchMap(new Function<String, ObservableSource<Associational>>() {
                    @Override
                    public ObservableSource<Associational> apply(@NonNull String s) throws Exception {
                        return mModel.getAssociational(s,update);
                    }
                })
                .compose(RxHttpResponseCompose.composeSchedulers())
                .subscribe(new ErrorHandleSubscriber<Associational>(mContext) {


                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(Associational associational) {
                        mView.showAssociationalList(associational.getSuggestion());
                    }

                    @Override
                    public void onComplete() {

                    }

                });

    }

    public void AppListByKeyword(String keyword, final int page,boolean update) {

        Observer subscriber = null;

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
                public void onNext(PageBean pageBean) {
                    mView.showAppList(pageBean);
                }

                @Override
                public void onComplete() {
                    mView.onLoadMoreComplete();
                }
            };
        }

        mModel.getAppListByKeyword(keyword, page,update)
                .compose(RxHttpResponseCompose.composeSchedulers())
                .subscribe(subscriber);
    }


    public void getHistoryWordList() {
        mModel.getHistoryWordList()
                .compose(RxHttpResponseCompose.composeSchedulers())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> history) throws Exception {
                        mView.showHistoryList(history);
                    }
                });
    }

    public void insertHistory(String history){

        mModel.insertHistoryData(history);

    }

    public void deleteAllHistory(){

        mModel.deleteAllHistoryData();

    }

}
