package com.lbz.android.myappplay.di.module;


import com.lbz.android.myappplay.data.SubjectModel;
import com.lbz.android.myappplay.data.http.Repository;
import com.lbz.android.myappplay.presenter.contract.SubjectContract;

import dagger.Module;
import dagger.Provides;


@Module
public class SubjectModule {

    private SubjectContract.SubjectView mView;

    public SubjectModule(SubjectContract.SubjectView  view) {

        this.mView = view;

    }

    @Provides
    public SubjectContract.SubjectView provideView() {

        return mView;
    }


    @Provides
    public SubjectContract.ISubjectModel privodeModel(Repository repository) {

        return new SubjectModel(repository);
    }

}
