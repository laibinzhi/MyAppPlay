package com.lbz.android.myappplay.data;

import com.lbz.android.myappplay.bean.Associational;
import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.data.http.ApiService;
import com.lbz.android.myappplay.presenter.contract.SearchAppContract;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by elitemc on 2017/9/14.
 */

public class SearchAppModel implements SearchAppContract.ISearchAppModel {

    private ApiService mApiService;

    public SearchAppModel(ApiService apiService) {
        this.mApiService = apiService;
    }

    @Override
    public Observable<Associational> getAssociational(String keyword) {
        return mApiService.getAssociationalList(keyword);
    }

    @Override
    public Observable<PageBean> getAppListByKeyword(String keyword, int page) {
        return mApiService.getAppListByKeyword(keyword,page);
    }

    @Override
    public Observable<List<String>> getHistoryWordList() {
        List<String> list = new ArrayList<String>() {{
            add("微信");
            add("支付宝");
            add("微博");
            add("Classrooms");
            add("ES文件浏览器");
            add("淘宝");
            add("今日头条");
            add("熊猫直播");
            add("花生地铁W");
            add("E-ducation");
        }};
        return Observable.just(list);
    }
}
