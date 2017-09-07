package com.lbz.android.myappplay.di.component;

import com.lbz.android.myappplay.data.http.ApiService;
import com.lbz.android.myappplay.di.FragmentScope;
import com.lbz.android.myappplay.di.module.LoginModule;
import com.lbz.android.myappplay.ui.activity.LoginActivity;

import dagger.Component;


@FragmentScope
@Component(modules = LoginModule.class,dependencies = AppComponent.class)
public interface LoginComponent {


    void inject(LoginActivity activity);
}
