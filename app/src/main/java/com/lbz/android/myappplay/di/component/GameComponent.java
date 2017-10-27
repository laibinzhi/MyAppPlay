package com.lbz.android.myappplay.di.component;

import com.lbz.android.myappplay.di.FragmentScope;
import com.lbz.android.myappplay.di.module.GameModule;
import com.lbz.android.myappplay.di.module.RecommendModule;
import com.lbz.android.myappplay.ui.fragment.GamesFragment;
import com.lbz.android.myappplay.ui.fragment.RecomendFragment;

import dagger.Component;

/**
 * Created by lbz on 2017/7/13.
 */

@FragmentScope
@Component(modules = GameModule.class,dependencies = AppComponent.class)
public interface GameComponent {


    void inject(GamesFragment fragment);

}
