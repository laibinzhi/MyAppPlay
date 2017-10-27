package com.lbz.android.myappplay.data.http;

import com.lbz.android.myappplay.bean.AppDownloadInfo;
import com.lbz.android.myappplay.bean.Associational;
import com.lbz.android.myappplay.bean.PageBean;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.DynamicKeyGroup;
import io.rx_cache2.EvictProvider;

/**
 * Created by lbz on 2017/10/25.
 */

public class Repository {

    private CacheProviders mCacheProviders;

    private ApiService mApiService;

    @Inject
    public Repository(CacheProviders mCacheProviders, ApiService mApiService) {
        this.mCacheProviders = mCacheProviders;
        this.mApiService = mApiService;
    }

    public Observable<PageBean> topList(int page, boolean update) {
        return mCacheProviders.topList(mApiService.topList(page), new DynamicKey(page), new EvictProvider(update));
    }

    public Observable<PageBean> getCategory(boolean update) {
        return mCacheProviders.getCategory(mApiService.getCategory(), new EvictProvider(update));
    }

    public Observable<PageBean> getCategory(int category_id, boolean update) {
        return mCacheProviders.getCategory(mApiService.getCategory(category_id), new DynamicKey(category_id), new EvictProvider(update));
    }

    public Observable<PageBean> getIndexAppData(int page, boolean update) {
        return mCacheProviders.getIndexAppData(mApiService.getIndexAppData(page), new DynamicKey(page), new EvictProvider(update));
    }

    public Observable<PageBean> getIndexTopTheme(boolean update) {
        return mCacheProviders.getIndexTopTheme(mApiService.getIndexTopTheme(), new EvictProvider(update));
    }

    public Observable<PageBean> getIndexTopTheme(int category_id,boolean update) {
        return mCacheProviders.getIndexTopTheme(mApiService.getIndexTopTheme(category_id),new DynamicKey(category_id), new EvictProvider(update));
    }

    public Observable<PageBean> getFeaturedAppsByCategory(int category_id, int page, boolean update) {
        return mCacheProviders.getFeaturedAppsByCategory(mApiService.getFeaturedAppsByCategory(category_id, page), new DynamicKeyGroup(category_id, page), new EvictProvider(update));
    }

    public Observable<PageBean> getTopListAppsByCategory(int category_id, int page, boolean update) {
        return mCacheProviders.getTopListAppsByCategory(mApiService.getTopListAppsByCategory(category_id, page), new DynamicKeyGroup(category_id, page), new EvictProvider(update));
    }

    public Observable<PageBean> getNewListAppsByCategory(int category_id, int page, boolean update) {
        return mCacheProviders.getNewListAppsByCategory(mApiService.getNewListAppsByCategory(category_id, page), new DynamicKeyGroup(category_id, page), new EvictProvider(update));
    }

    public Observable<PageBean> getHotAppList(int page, boolean update) {
        return mCacheProviders.getHotAppList(mApiService.getHotAppList(page), new DynamicKey(page), new EvictProvider(update));
    }

    public Observable<PageBean> getSubjectList(int page, boolean update) {
        return mCacheProviders.getSubjectList(mApiService.getSubjectList(page), new DynamicKey(page), new EvictProvider(update));
    }

    public Observable<PageBean> getAppListBySubject(int subject_id, int page, boolean update) {
        return mCacheProviders.getAppListBySubject(mApiService.getAppListBySubject(subject_id, page), new DynamicKeyGroup(subject_id, page), new EvictProvider(update));
    }

    public Observable<PageBean> getAppDetailById(int appid, boolean update) {
        return mCacheProviders.getAppDetailById(mApiService.getAppDetailById(appid), new DynamicKey(appid), new EvictProvider(update));
    }

    public Observable<PageBean> getSameDevAppList(int appid, int page, boolean update) {
        return mCacheProviders.getSameDevAppList(mApiService.getSameDevAppList(appid, page), new DynamicKeyGroup(appid, page), new EvictProvider(update));
    }

    public Observable<Associational> getAssociationalList(String keyword, boolean update) {
        return mCacheProviders.getAssociationalList(mApiService.getAssociationalList(keyword), new DynamicKey(keyword), new EvictProvider(update));
    }

    public Observable<PageBean> getAppListByKeyword(String keyword, int page, boolean update) {
        return mCacheProviders.getAppListByKeyword(mApiService.getAppListByKeyword(keyword, page), new DynamicKeyGroup(keyword, page), new EvictProvider(update));
    }

    public Observable<AppDownloadInfo> getAppDownloadInfo(int appid, boolean update) {
        return mCacheProviders.getAppDownloadInfo(mApiService.getAppDownloadInfo(appid), new DynamicKey(appid), new EvictProvider(update));
    }

    public Observable<PageBean> getCanUpdateAppList(Map<String, String> fields, boolean update) {
        return mCacheProviders.getCanUpdateAppList(mApiService.getCanUpdateAppList(fields), new EvictProvider(update));
    }


}
