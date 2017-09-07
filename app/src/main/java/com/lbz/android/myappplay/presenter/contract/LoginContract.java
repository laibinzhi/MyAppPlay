package com.lbz.android.myappplay.presenter.contract;

import com.lbz.android.myappplay.bean.BaseHttpResultBean;
import com.lbz.android.myappplay.bean.LoginBean;
import com.lbz.android.myappplay.ui.BaseView;

import rx.Observable;

/**
 * Created by elitemc on 2017/9/5.
 */

public interface LoginContract {

    interface ILoginModel {
        Observable<BaseHttpResultBean<LoginBean>> login(String phone, String password);
    }

    interface LoginView extends BaseView {

        void checkPhoneError();

        void checkPhoneSuccess();

        void loginSuccess(LoginBean bean);
    }

}
