package com.lbz.android.myappplay.data;

import com.lbz.android.myappplay.bean.Associational;
import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.bean.SearchHistory.SearchHistory;
import com.lbz.android.myappplay.bean.SearchHistory.SearchHistoryDao;
import com.lbz.android.myappplay.data.http.ApiService;
import com.lbz.android.myappplay.presenter.contract.SearchAppContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;


/**
 * Created by elitemc on 2017/9/14.
 */

public class SearchAppModel implements SearchAppContract.ISearchAppModel {

    private ApiService mApiService;
    private SearchHistoryDao mSearchHistoryDao;

    public SearchAppModel(ApiService apiService, SearchHistoryDao searchHistoryDao) {
        this.mApiService = apiService;
        this.mSearchHistoryDao = searchHistoryDao;
    }

    @Override
    public Observable<Associational> getAssociational(String keyword) {
        return mApiService.getAssociationalList(keyword);
    }

    @Override
    public Observable<PageBean> getAppListByKeyword(String keyword, int page) {
        return mApiService.getAppListByKeyword(keyword, page);
    }

    @Override
    public Observable<List<String>> getHistoryWordList() {

        return Observable.just(getHistoryList());

    }

    public List<String> getHistoryList() {
        List<SearchHistory> allData = mSearchHistoryDao.loadAll();
        Collections.reverse(allData);//反序。取最近的数据

        if (allData.size() > 10) {
            allData = allData.subList(0, 10);//如果超出10条，取最近的前十条数据
        }

        List<String> historyData = new ArrayList<>();
        for (SearchHistory history : allData) {
            historyData.add(history.getName());
        }
        return historyData;
    }

    @Override
    public void insertHistoryData(String history) {

        List<SearchHistory> allData = mSearchHistoryDao.loadAll();

        for (SearchHistory historyBean : allData) {
            if (historyBean.getName().equals(history)) {
                mSearchHistoryDao.deleteByKey(historyBean.getId());
            }
        }
        SearchHistory s = new SearchHistory();
        s.setName(history);
        mSearchHistoryDao.insert(s);
    }

    @Override
    public void deleteAllHistoryData() {
        mSearchHistoryDao.deleteAll();
    }
}
