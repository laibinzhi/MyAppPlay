package com.lbz.android.myappplay.presenter;

import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.commom.rx.subscriber.ProgressSubcriber;
import com.lbz.android.myappplay.presenter.contract.SubjectContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by elitemc on 2017/9/5.
 */

public class SubjectPresenter extends BasePresenter<SubjectContract.ISubjectModel, SubjectContract.SubjectView> {

    @Inject
    public SubjectPresenter(SubjectContract.ISubjectModel mModel, SubjectContract.SubjectView mView) {
        super(mModel, mView);
    }

    public void requestDatas(boolean update) {
        mModel.getSubject(update)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubcriber<PageBean>(mContext,mView) {
                    @Override
                    public void onNext(PageBean pageBean) {
                        mView.showData(pageBean.getTopfeaturedList());
                    }
                });
    }

}
