package com.lbz.android.myappplay.di.component;

import com.lbz.android.myappplay.di.FragmentScope;
import com.lbz.android.myappplay.di.module.AppManagerModule;
import com.lbz.android.myappplay.ui.fragment.DownloadedFragment;
import com.lbz.android.myappplay.ui.fragment.DownloadingFragment;
import com.lbz.android.myappplay.ui.fragment.InstalledAppFragment;

import dagger.Component;

/**
 * Created by elitemc on 2017/9/21.
 */

@FragmentScope
@Component(modules = AppManagerModule.class,dependencies = AppComponent.class)
public interface AppManagerComponent {

    void injectDownloadingFragment(DownloadingFragment fragment);
    void injectDownloadedFragment(DownloadedFragment fragment);
    void injectInstalledAppFragment(InstalledAppFragment fragment);

}
