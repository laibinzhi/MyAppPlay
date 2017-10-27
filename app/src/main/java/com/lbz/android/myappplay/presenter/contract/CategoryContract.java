package com.lbz.android.myappplay.presenter.contract;

import com.lbz.android.myappplay.bean.Category;
import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.ui.BaseView;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by lbz on 2017/9/5.
 */

public interface CategoryContract {

    interface ICategoryModel {
        Observable<PageBean> getCategory(boolean update);
        Observable<PageBean> getCategory(int category_id,boolean update);
    }

    interface CategoryView extends BaseView {

        void showData(List<Category> categories);

    }

}
