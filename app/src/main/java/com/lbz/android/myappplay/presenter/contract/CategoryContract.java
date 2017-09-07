package com.lbz.android.myappplay.presenter.contract;

import com.lbz.android.myappplay.bean.BaseHttpResultBean;
import com.lbz.android.myappplay.bean.Category;
import com.lbz.android.myappplay.bean.LoginBean;
import com.lbz.android.myappplay.bean.PageMiBean;
import com.lbz.android.myappplay.ui.BaseView;

import java.util.List;

import rx.Observable;

/**
 * Created by elitemc on 2017/9/5.
 */

public interface CategoryContract {

    interface ICategoryModel {
        Observable<PageMiBean> getCategory();
    }

    interface CategoryView extends BaseView {

        void showData(List<Category> categories);

    }

}
