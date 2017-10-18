package com.lbz.android.myappplay.presenter;

import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.commom.rx.RxHttpResponseCompose;
import com.lbz.android.myappplay.commom.rx.subscriber.ProgressSubcriber;
import com.lbz.android.myappplay.data.AppInfoModel;
import com.lbz.android.myappplay.presenter.contract.AppInfoContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;


/**
 * Created by elitemc on 2017/7/12.
 */
public class GamePresenter extends BasePresenter<AppInfoModel, AppInfoContract.View> {

    @Inject
    public GamePresenter(AppInfoModel mModel, AppInfoContract.View mView) {
        super(mModel, mView);
    }

    public void requestDatas() {


        Observable<PageBean> faturedGames = mModel.getFeaturedAppsByCategory(15, 0);
        Observable<PageBean> topTheme = mModel.getIndexTopTheme(15);

        Observable.zip(faturedGames, topTheme, new BiFunction<PageBean, PageBean, PageBean>() {
            @Override
            public PageBean apply(PageBean app, PageBean topTheme) {
                PageBean newZipBean = new PageBean();
                newZipBean.setListApp(app.getListApp());
                newZipBean.setTopTheme(topTheme.getTopfeaturedList());
                return newZipBean;
            }
        }).compose(RxHttpResponseCompose.composeSchedulers())
                .subscribe(new ProgressSubcriber<PageBean>(mContext, mView) {

                    @Override
                    public void onNext(PageBean pageBean) {
                        mView.showData(pageBean);
                    }

                });
    }
}
