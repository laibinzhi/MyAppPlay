package com.lbz.android.myappplay.di.component;

import com.lbz.android.myappplay.di.ClassScope;
import com.lbz.android.myappplay.ui.widget.DownloadButtonConntroller;

import dagger.Component;


@ClassScope
@Component(dependencies = AppComponent.class)
public interface DownloadComponent {

    void inject(DownloadButtonConntroller conntroller);

}
