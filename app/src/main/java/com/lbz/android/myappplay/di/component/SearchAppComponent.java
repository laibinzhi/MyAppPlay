package com.lbz.android.myappplay.di.component;

import com.lbz.android.myappplay.di.FragmentScope;
import com.lbz.android.myappplay.di.module.SearchAppModule;
import com.lbz.android.myappplay.ui.activity.SearchAppActivity;
import com.lbz.android.myappplay.ui.fragment.KeyWordAppListFragment;
import com.lbz.android.myappplay.ui.fragment.SearchHistoryFragment;
import com.lbz.android.myappplay.ui.fragment.AssociationalFragment;

import dagger.Component;


@FragmentScope
@Component(modules = SearchAppModule.class,dependencies = AppComponent.class)
public interface SearchAppComponent {

    void injectSearchAppActivity(SearchAppActivity activity);


    void injectSearchHistoryFragment(SearchHistoryFragment fragment);

    void injectShowAssociationalFragment(AssociationalFragment fragment);

    void KeyWordAppListFragment(KeyWordAppListFragment fragment);


}
