package com.lbz.android.myappplay.presenter;

import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.commom.rx.RxHttpResponseCompose;
import com.lbz.android.myappplay.commom.rx.subscriber.ErrorHandleSubscriber;
import com.lbz.android.myappplay.commom.rx.subscriber.ProgressSubcriber;
import com.lbz.android.myappplay.data.AppInfoModel;
import com.lbz.android.myappplay.presenter.contract.AppInfoContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by lbz on 2017/9/4.
 */

public class AppInfoPresenter extends BasePresenter<AppInfoModel, AppInfoContract.AppInfoView> {

    public static final int TOP_LIST = 1;
    public static final int GAME = 2;
    public static final int CATEGORY = 3;
    public static final int HOT_APP_LIST = 4;
    public static final int SUBJECT_APP = 5;
    public static final int SAME_DEV_APP_LIST = 6;


    public static final int FEATURED = 0;
    public static final int TOPLIST = 1;
    public static final int NEWLIST = 2;

    @Inject
    public AppInfoPresenter(AppInfoModel mModel, AppInfoContract.AppInfoView mView) {
        super(mModel, mView);
    }

    public void request(int type, int page, int categoryId, int flagType, int subject_id,int appId,boolean update) {

        Observer subscriber = null;

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
                public void onComplete() {
                    mView.onLoadMoreComplete();
                }

                @Override
                public void onNext(PageBean pageBean) {
                    mView.showData(pageBean);
                }
            };
        }

        Observable observable = getObservable(type, page, categoryId, flagType, subject_id,appId,update);


        observable.compose(RxHttpResponseCompose.composeSchedulers())
                .subscribe(subscriber);
    }

    private Observable<PageBean> getObservable(int type, int page, int categoryId, int flagType, int subject_id,int appId,boolean update) {

        switch (type) {

            case TOP_LIST:

                return mModel.getTopList(page,update);

            case GAME:

            case CATEGORY:

                if (flagType == FEATURED) {

                    return mModel.getFeaturedAppsByCategory(categoryId, page,update);

                } else if (flagType == TOPLIST) {

                    return mModel.getTopListAppsByCategory(categoryId, page,update);

                } else if (flagType == NEWLIST) {

                    return mModel.getNewListAppsByCategory(categoryId, page,update);

                }

            case HOT_APP_LIST:

                return mModel.getHotAppList(page,update);

            case SUBJECT_APP:

                return mModel.getAppListBySubject(subject_id, page,update);

            case SAME_DEV_APP_LIST:

                return mModel.getSameDevAppList(appId,page,update);

            default:

                return Observable.empty();

        }

    }

    public void requestCategoryApps(int categoryId, int page, int flagType,boolean update) {

        request(CATEGORY, page, categoryId, flagType, 0,0,update);

    }

    public void requestSubjectApps(int subject_id, int page,boolean update) {

        request(SUBJECT_APP, page, 0, 0, subject_id,0,update);

    }

    public void requestSameDevApps(int appId, int page,boolean update) {

        request(SAME_DEV_APP_LIST, page, 0, 0,0, appId,update);

    }

    public void requestData(int type, int page,boolean update) {

        request(type, page, 0, 0, 0,0,update);

    }

}
