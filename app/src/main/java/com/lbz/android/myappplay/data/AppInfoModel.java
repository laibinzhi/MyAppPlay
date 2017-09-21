package com.lbz.android.myappplay.data;


import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.data.http.ApiService;

import io.reactivex.Observable;


/**
 * Created by lbz on 2017/7/12.
 */
public class AppInfoModel {


    private ApiService mApiService;

    public AppInfoModel(ApiService apiService) {
        this.mApiService = apiService;
    }

    public Observable<PageBean> getIndexAppData() {

        return mApiService.getIndexAppData(0);

    }

    public Observable<PageBean> getIndexTopTheme() {

        return mApiService.getIndexTopTheme();

    }

    public Observable<PageBean> getTopList(int page) {

        return mApiService.topList(page);

    }


    public Observable<PageBean> getFeaturedAppsByCategory(int category_id, int page) {

        return mApiService.getFeaturedAppsByCategory(category_id, page);

    }

    public Observable<PageBean> getTopListAppsByCategory(int category_id, int page) {

        return mApiService.getTopListAppsByCategory(category_id, page);

    }

    public Observable<PageBean> getNewListAppsByCategory(int category_id, int page) {

        return mApiService.getNewListAppsByCategory(category_id, page);

    }

    public Observable<PageBean> getHotAppList(int page) {

        return mApiService.getHotAppList(page);

    }

    public Observable<PageBean> getAppListBySubject(int subject_id, int page) {

        return mApiService.getAppListBySubject(subject_id, page);

    }

    public Observable<PageBean> getAppDetailById(int app_id) {

        return mApiService.getAppDetailById(app_id);

    }

    public Observable<PageBean> getSameDevAppList(int appId,int page) {

        return mApiService.getSameDevAppList(appId,page);

    }

}
