package com.lbz.android.myappplay.di.component;

import com.lbz.android.myappplay.di.FragmentScope;
import com.lbz.android.myappplay.di.module.CategoryModule;
import com.lbz.android.myappplay.ui.fragment.CategoryFragment;

import dagger.Component;


@FragmentScope
@Component(modules = CategoryModule.class,dependencies = AppComponent.class)
public interface CategoryComponent {


    void inject(CategoryFragment fragment);

}
