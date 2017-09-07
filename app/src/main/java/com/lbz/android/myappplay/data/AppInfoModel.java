package com.lbz.android.myappplay.data;


import com.lbz.android.myappplay.bean.AppInfo;
import com.lbz.android.myappplay.bean.BaseHttpResultBean;
import com.lbz.android.myappplay.bean.IndexBean;
import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.bean.PageMiBean;
import com.lbz.android.myappplay.data.http.ApiService;


import retrofit2.Callback;
import rx.Observable;

/**
 * Created by elitemc on 2017/7/12.
 */
public class AppInfoModel {


    private ApiService mApiService;

    public AppInfoModel(ApiService apiService) {
        this.mApiService = apiService;
    }

    public Observable<BaseHttpResultBean<IndexBean>> getIndex() {

        return mApiService.index();

    }

    public Observable<BaseHttpResultBean<PageBean<AppInfo>>> getTopList(int page) {
        return mApiService.topList(page);
    }


    public Observable<PageMiBean> getFeaturedAppsByCategory(int category_id, int page) {
        return mApiService.getFeaturedAppsByCategory(category_id, page);
    }

    public Observable<PageMiBean> getTopListAppsByCategory(int category_id, int page) {
        return mApiService.getTopListAppsByCategory(category_id, page);
    }

    public Observable<PageMiBean> getNewListAppsByCategory(int category_id, int page) {
        return mApiService.getNewListAppsByCategory(category_id, page);
    }

}
