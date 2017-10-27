package com.lbz.android.myappplay.data.http;

import com.lbz.android.myappplay.bean.AppDownloadInfo;
import com.lbz.android.myappplay.bean.Associational;
import com.lbz.android.myappplay.bean.PageBean;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.DynamicKeyGroup;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;

/**
 * Created by lbz on 2017/10/25.
 */

public interface CacheProviders {

    @LifeCache(duration = 10, timeUnit = TimeUnit.MINUTES)
    Observable<PageBean> topList(Observable<PageBean> observable, DynamicKey page_DynamicKey, EvictProvider evictProvider);

    @LifeCache(duration = 30, timeUnit = TimeUnit.DAYS)
    Observable<PageBean> getCategory(Observable<PageBean> observable, EvictProvider evictProvider);

    @LifeCache(duration = 30, timeUnit = TimeUnit.DAYS)
    Observable<PageBean> getCategory(Observable<PageBean> observable, DynamicKey category_id_DynamicKey, EvictProvider evictProvider);

    @LifeCache(duration = 10, timeUnit = TimeUnit.MINUTES)
    Observable<PageBean> getIndexAppData(Observable<PageBean> observable, DynamicKey page_DynamicKey, EvictProvider evictProvider);

    @LifeCache(duration = 10, timeUnit = TimeUnit.MINUTES)
    Observable<PageBean> getIndexTopTheme(Observable<PageBean> observable, EvictProvider evictProvider);

    @LifeCache(duration = 10, timeUnit = TimeUnit.MINUTES)
    Observable<PageBean> getIndexTopTheme(Observable<PageBean> observable,DynamicKey category_id_DynamicKey, EvictProvider evictProvider);

    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    Observable<PageBean> getFeaturedAppsByCategory(Observable<PageBean> observable, DynamicKeyGroup category_id_and_page_DynamicKeyGroup, EvictProvider evictProvider);

    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    Observable<PageBean> getTopListAppsByCategory(Observable<PageBean> observable, DynamicKeyGroup category_id_and_page_DynamicKeyGroup, EvictProvider evictProvider);

    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    Observable<PageBean> getNewListAppsByCategory(Observable<PageBean> observable, DynamicKeyGroup category_id_and_page_DynamicKeyGroup, EvictProvider evictProvider);

    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    Observable<PageBean> getHotAppList(Observable<PageBean> observable, DynamicKey page_DynamicKey, EvictProvider evictProvider);

    @LifeCache(duration = 10, timeUnit = TimeUnit.DAYS)
    Observable<PageBean> getSubjectList(Observable<PageBean> observable, DynamicKey page_DynamicKey, EvictProvider evictProvider);

    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    Observable<PageBean> getAppListBySubject(Observable<PageBean> observable, DynamicKeyGroup subject_id_and_page_DynamicKeyGroup, EvictProvider evictProvider);

    @LifeCache(duration = 30, timeUnit = TimeUnit.DAYS)
    Observable<PageBean> getAppDetailById(Observable<PageBean> observable, DynamicKey appid_DynamicKey, EvictProvider evictProvider);

    @LifeCache(duration = 30, timeUnit = TimeUnit.DAYS)
    Observable<PageBean> getSameDevAppList(Observable<PageBean> observable, DynamicKeyGroup appid_and_page_DynamicKeyGroup, EvictProvider evictProvider);

    @LifeCache(duration = 10, timeUnit = TimeUnit.MINUTES)
    Observable<Associational> getAssociationalList(Observable<Associational> observable, DynamicKey keyword_DynamicKey, EvictProvider evictProvider);

    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    Observable<PageBean> getAppListByKeyword(Observable<PageBean> observable, DynamicKeyGroup keyword_and_page_DynamicKeyGroup, EvictProvider evictProvider);

    @LifeCache(duration = 10, timeUnit = TimeUnit.DAYS)
    Observable<AppDownloadInfo> getAppDownloadInfo(Observable<AppDownloadInfo> observable, DynamicKey appid_DynamicKey, EvictProvider evictProvider);

    Observable<PageBean> getCanUpdateAppList(Observable<PageBean> observable, EvictProvider evictProvider);
}


