package com.lbz.android.myappplay.presenter;

import com.lbz.android.myappplay.bean.IndexBean;
import com.lbz.android.myappplay.commom.rx.RxHttpResponseCompose;
import com.lbz.android.myappplay.commom.rx.subscriber.ProgressSubcriber;
import com.lbz.android.myappplay.data.AppInfoModel;
import com.lbz.android.myappplay.presenter.contract.AppInfoContract;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by elitemc on 2017/7/12.
 */
public class RecomendPresenter extends BasePresenter<AppInfoModel, AppInfoContract.View> {

    @Inject
    public RecomendPresenter(AppInfoModel mModel, AppInfoContract.View mView) {
        super(mModel, mView);
    }

    public void requestDatas() {

        mModel.getIndex().compose(RxHttpResponseCompose.<IndexBean>composeResult())
                .subscribe(new ProgressSubcriber<IndexBean>(mContext,mView) {
                    @Override
                    public void onNext(IndexBean indexBean) {
                        mView.showData(indexBean);
                    }
                });
    }
}
