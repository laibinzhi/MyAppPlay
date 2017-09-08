package com.lbz.android.myappplay.di.component;

import com.lbz.android.myappplay.di.FragmentScope;
import com.lbz.android.myappplay.di.module.SubjectModule;
import com.lbz.android.myappplay.ui.activity.HotSubjectActivity;
import com.lbz.android.myappplay.ui.fragment.HotSubjectFragment;

import dagger.Component;


@FragmentScope
@Component(modules = SubjectModule.class,dependencies = AppComponent.class)
public interface SubjectComponent {


    void inject(HotSubjectFragment fragment);

}
