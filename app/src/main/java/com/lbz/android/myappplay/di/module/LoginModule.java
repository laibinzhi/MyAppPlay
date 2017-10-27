package com.lbz.android.myappplay.di.module;


import com.lbz.android.myappplay.data.LoginModel;
import com.lbz.android.myappplay.data.http.ApiService;
import com.lbz.android.myappplay.presenter.contract.LoginContract;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;


@Module
public class LoginModule {

    private LoginContract.LoginView mView;

    public LoginModule(LoginContract.LoginView view){

        this.mView = view;

    }

    @Provides
    public LoginContract.LoginView provideView(){

        return mView;
    }

    @Provides
    public LoginContract.ILoginModel privodeModel(ApiService apiService){

        return  new LoginModel(apiService);
    }

}
