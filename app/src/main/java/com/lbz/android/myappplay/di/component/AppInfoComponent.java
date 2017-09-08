package com.lbz.android.myappplay.di.component;

import com.lbz.android.myappplay.di.FragmentScope;
import com.lbz.android.myappplay.di.module.AppInfoModule;
import com.lbz.android.myappplay.ui.fragment.CategoryAppFragment;
import com.lbz.android.myappplay.ui.fragment.HotAppFragment;
import com.lbz.android.myappplay.ui.fragment.TopListFragment;

import dagger.Component;

/**
 * Created by elitemc on 2017/7/13.
 */

@FragmentScope
@Component(modules = AppInfoModule.class, dependencies = AppComponent.class)
public interface AppInfoComponent {

    void injectTopListFragment(TopListFragment fragment);

    void injectCategoryAppFragment(CategoryAppFragment fragment);

    void injectHotAppFragment(HotAppFragment fragment);

}
