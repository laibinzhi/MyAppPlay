package com.lbz.android.myappplay.di.component;

import com.lbz.android.myappplay.di.FragmentScope;
import com.lbz.android.myappplay.di.module.AppDetailModule;
import com.lbz.android.myappplay.ui.fragment.AppDetailFragment;

import dagger.Component;

/**
 * Created by lbz on 2017/7/13.
 */

@FragmentScope
@Component(modules = AppDetailModule.class, dependencies = AppComponent.class)
public interface AppDetailComponent {

    void inject(AppDetailFragment fragment);

}
