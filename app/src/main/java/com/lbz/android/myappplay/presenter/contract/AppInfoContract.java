package com.lbz.android.myappplay.presenter.contract;

import com.lbz.android.myappplay.bean.AppInfo;
import com.lbz.android.myappplay.bean.IndexBean;
import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.ui.BaseView;

import java.util.List;

/**
 * Created by elitemc on 2017/7/12.
 */
public interface AppInfoContract {

    interface View extends BaseView {

        void showData(IndexBean indexBean);

    }

    interface AppInfoView extends BaseView {

        void showData(PageBean<AppInfo> pageBean);

        void onLoadMoreComplete();


    }

}
