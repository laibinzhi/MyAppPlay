package com.lbz.android.myappplay.di.component;

import com.lbz.android.myappplay.di.FragmentScope;
import com.lbz.android.myappplay.di.module.RecommendModule;
import com.lbz.android.myappplay.ui.fragment.RecomendFragment;

import dagger.Component;

/**
 * Created by lbz on 2017/7/13.
 */

@FragmentScope
@Component(modules = RecommendModule.class,dependencies = AppComponent.class)
public interface RecommendComponent {


    void inject(RecomendFragment fragment);

}
