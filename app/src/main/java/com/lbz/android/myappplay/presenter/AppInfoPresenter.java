package com.lbz.android.myappplay.presenter;

import com.lbz.android.myappplay.bean.AppInfo;
import com.lbz.android.myappplay.bean.BaseHttpResultBean;
import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.commom.rx.RxHttpResponseCompose;
import com.lbz.android.myappplay.commom.rx.subscriber.ErrorHandleSubscriber;
import com.lbz.android.myappplay.commom.rx.subscriber.ProgressSubcriber;
import com.lbz.android.myappplay.data.AppInfoModel;
import com.lbz.android.myappplay.presenter.contract.AppInfoContract;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by elitemc on 2017/9/4.
 */

public class AppInfoPresenter extends BasePresenter<AppInfoModel, AppInfoContract.AppInfoView> {

    public static final int TOP_LIST = 1;
    public static final int GAME = 2;

    @Inject
    public AppInfoPresenter(AppInfoModel mModel, AppInfoContract.AppInfoView mView) {
        super(mModel, mView);
    }

    public void requestData(int type, int page) {

        Subscriber subscriber = null;
        if (page == 0) {
            subscriber = new ProgressSubcriber<PageBean<AppInfo>>(mContext, mView) {
                @Override
                public void onNext(PageBean<AppInfo> pageBean) {
                    mView.showData(pageBean);
                }
            };
        } else {
            subscriber = new ErrorHandleSubscriber<PageBean<AppInfo>>(mContext) {
                @Override
                public void onCompleted() {
                    mView.onLoadMoreComplete();
                }

                @Override
                public void onNext(PageBean<AppInfo> pageBean) {
                    mView.showData(pageBean);
                }
            };
        }

        Observable observable = getObservable(type, page);


        observable.compose(RxHttpResponseCompose.<PageBean<AppInfo>>composeResult())
                .subscribe(subscriber);
    }

    private Observable<BaseHttpResultBean<PageBean<AppInfo>>> getObservable(int type, int page) {

        switch (type) {

            case TOP_LIST:
                return mModel.getTopList(page);


            case GAME:

            default:
                return Observable.empty();
        }

    }




}
