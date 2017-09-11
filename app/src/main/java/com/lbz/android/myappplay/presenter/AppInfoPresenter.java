package com.lbz.android.myappplay.presenter;

import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.commom.rx.RxHttpResponseCompose;
import com.lbz.android.myappplay.commom.rx.subscriber.ErrorHandleSubscriber;
import com.lbz.android.myappplay.commom.rx.subscriber.ProgressSubcriber;
import com.lbz.android.myappplay.data.AppInfoModel;
import com.lbz.android.myappplay.presenter.contract.AppInfoContract;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by elitemc on 2017/9/4.
 */

public class AppInfoPresenter extends BasePresenter<AppInfoModel, AppInfoContract.AppInfoView> {

    public static final int TOP_LIST = 1;
    public static final int GAME = 2;
    public static final int CATEGORY = 3;
    public static final int HOT_APP_LIST = 4;
    public static final int SUBJECT_APP = 5;


    public static final int FEATURED = 0;
    public static final int TOPLIST = 1;
    public static final int NEWLIST = 2;

    @Inject
    public AppInfoPresenter(AppInfoModel mModel, AppInfoContract.AppInfoView mView) {
        super(mModel, mView);
    }

    public void request(int type, int page, int categoryId, int flagType, int subject_id) {

        Subscriber subscriber = null;

        if (page == 0) {
            subscriber = new ProgressSubcriber<PageBean>(mContext, mView) {
                @Override
                public void onNext(PageBean pageBean) {
                    mView.showData(pageBean);
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
                    mView.showData(pageBean);
                }
            };
        }

        Observable observable = getObservable(type, page, categoryId, flagType, subject_id);


        observable.compose(RxHttpResponseCompose.composeSchedulers())
                .subscribe(subscriber);
    }

    private Observable<PageBean> getObservable(int type, int page, int categoryId, int flagType, int subject_id) {

        switch (type) {

            case TOP_LIST:

                return mModel.getTopList(page);

            case GAME:

            case CATEGORY:

                if (flagType == FEATURED) {

                    return mModel.getFeaturedAppsByCategory(categoryId, page);

                } else if (flagType == TOPLIST) {

                    return mModel.getTopListAppsByCategory(categoryId, page);

                } else if (flagType == NEWLIST) {

                    return mModel.getNewListAppsByCategory(categoryId, page);

                }

            case HOT_APP_LIST:

                return mModel.getHotAppList(page);

            case SUBJECT_APP:

                return mModel.getAppListBySubject(subject_id, page);

            default:

                return Observable.empty();

        }

    }

    public void requestCategoryApps(int categoryId, int page, int flagType) {

        request(CATEGORY, page, categoryId, flagType, 0);

    }

    public void requestSubjectApps(int subject_id, int page) {

        request(SUBJECT_APP, page, 0, 0, subject_id);

    }

    public void requestData(int type, int page) {

        request(type, page, 0, 0, 0);

    }

}
