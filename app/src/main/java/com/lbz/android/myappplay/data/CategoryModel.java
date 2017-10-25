package com.lbz.android.myappplay.data;


import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.data.http.Repository;
import com.lbz.android.myappplay.presenter.contract.CategoryContract;

import io.reactivex.Observable;


/**
 * Created by elitemc on 2017/7/12.
 */
public class CategoryModel implements CategoryContract.ICategoryModel {


    private Repository mRepository;

    public CategoryModel(Repository repository) {
        this.mRepository = repository;
    }

    @Override
    public Observable<PageBean> getCategory(boolean update) {
        return mRepository.getCategory(update);
    }

    @Override
    public Observable<PageBean> getCategory(int category_id,boolean update) {
        return mRepository.getCategory(category_id,update);
    }
}
