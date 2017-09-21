package com.lbz.android.myappplay.data;

import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.data.http.ApiService;
import com.lbz.android.myappplay.presenter.contract.SubjectContract;

import io.reactivex.Observable;


/**
 * Created by elitemc on 2017/9/8.
 */

public class SubjectModel implements SubjectContract.ISubjectModel {

    private ApiService mApiService;

    public SubjectModel(ApiService apiService) {
        this.mApiService = apiService;
    }

    @Override
    public Observable<PageBean> getSubject() {
        return mApiService.getSubjectList(0);
    }
}
