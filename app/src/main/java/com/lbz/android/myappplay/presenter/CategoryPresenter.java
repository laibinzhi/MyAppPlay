package com.lbz.android.myappplay.presenter;

import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.commom.rx.subscriber.ProgressSubcriber;
import com.lbz.android.myappplay.presenter.contract.CategoryContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by lbz on 2017/9/5.
 */

public class CategoryPresenter extends BasePresenter<CategoryContract.ICategoryModel, CategoryContract.CategoryView> {

    @Inject
    public CategoryPresenter(CategoryContract.ICategoryModel mModel, CategoryContract.CategoryView mView) {
        super(mModel, mView);
    }

    public void requestDatas(boolean update) {
        mModel.getCategory(update)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubcriber<PageBean>(mContext,mView) {
                    @Override
                    public void onNext(PageBean categoryBean) {
                        mView.showData(categoryBean.getCategories());
                    }
                });
    }

    public void requestDatas(int category_id,boolean update) {
        mModel.getCategory(category_id,update)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubcriber<PageBean>(mContext,mView) {
                    @Override
                    public void onNext(PageBean categoryBean) {
                        mView.showData(categoryBean.getCategories());
                    }
                });
    }

}
