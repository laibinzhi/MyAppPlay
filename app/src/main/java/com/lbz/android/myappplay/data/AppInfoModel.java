package com.lbz.android.myappplay.data;


import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.data.http.Repository;

import io.reactivex.Observable;


/**
 * Created by lbz on 2017/7/12.
 */
public class AppInfoModel {


    private Repository mRepository;

    public AppInfoModel(Repository repository) {
        this.mRepository = repository;
    }

    public Observable<PageBean> getIndexAppData(boolean update) {

        return mRepository.getIndexAppData(0, update);

    }

    public Observable<PageBean> getIndexTopTheme(boolean update) {

        return mRepository.getIndexTopTheme(update);

    }

    public Observable<PageBean> getIndexTopTheme(int category_id,boolean update) {

        return mRepository.getIndexTopTheme(category_id,update);

    }

    public Observable<PageBean> getTopList(int page,boolean update) {

        return mRepository.topList(page,update);

    }


    public Observable<PageBean> getFeaturedAppsByCategory(int category_id, int page,boolean update) {

        return mRepository.getFeaturedAppsByCategory(category_id, page,update);

    }

    public Observable<PageBean> getTopListAppsByCategory(int category_id, int page,boolean update) {

        return mRepository.getTopListAppsByCategory(category_id, page,update);

    }

    public Observable<PageBean> getNewListAppsByCategory(int category_id, int page,boolean update) {

        return mRepository.getNewListAppsByCategory(category_id, page,update);

    }

    public Observable<PageBean> getHotAppList(int page,boolean update) {

        return mRepository.getHotAppList(page,update);

    }

    public Observable<PageBean> getAppListBySubject(int subject_id, int page,boolean update) {

        return mRepository.getAppListBySubject(subject_id, page,update);

    }

    public Observable<PageBean> getAppDetailById(int app_id,boolean update) {

        return mRepository.getAppDetailById(app_id,update);

    }

    public Observable<PageBean> getSameDevAppList(int appId, int page,boolean update) {

        return mRepository.getSameDevAppList(appId, page,update);

    }

}
