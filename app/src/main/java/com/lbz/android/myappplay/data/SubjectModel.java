package com.lbz.android.myappplay.data;

import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.data.http.Repository;
import com.lbz.android.myappplay.presenter.contract.SubjectContract;

import io.reactivex.Observable;


/**
 * Created by lbz on 2017/9/8.
 */

public class SubjectModel implements SubjectContract.ISubjectModel {

    private Repository mRepository;

    public SubjectModel(Repository repository) {
        this.mRepository = repository;
    }

    @Override
    public Observable<PageBean> getSubject(boolean update) {
        return mRepository.getSubjectList(0,update);
    }
}
