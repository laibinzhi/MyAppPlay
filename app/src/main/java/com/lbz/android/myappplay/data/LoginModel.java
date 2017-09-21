package com.lbz.android.myappplay.data;


import com.lbz.android.myappplay.bean.BaseHttpResultBean;
import com.lbz.android.myappplay.bean.LoginBean;
import com.lbz.android.myappplay.bean.requestbean.LoginRequestBean;
import com.lbz.android.myappplay.data.http.ApiService;
import com.lbz.android.myappplay.presenter.contract.LoginContract;

import io.reactivex.Observable;


/**
 * Created by elitemc on 2017/7/12.
 */
public class LoginModel implements LoginContract.ILoginModel {


    private ApiService mApiService;

    public LoginModel(ApiService apiService) {
        this.mApiService = apiService;
    }


    @Override
    public Observable<BaseHttpResultBean<LoginBean>> login(String phone, String password) {
        LoginRequestBean requestBean = new LoginRequestBean();
        requestBean.setEmail(phone);
        requestBean.setPassword(password);
        return mApiService.login(requestBean);
    }
}
