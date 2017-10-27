package com.lbz.android.myappplay.di.module;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.lbz.android.myappplay.bean.SearchHistory.DaoMaster;
import com.lbz.android.myappplay.bean.SearchHistory.DaoSession;
import com.lbz.android.myappplay.bean.SearchHistory.SearchHistoryDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lbz on 2017/9/18.
 */

@Module
public class DBModule {

    @Provides
    @Singleton
    SearchHistoryDao provideSearchHistoryDao(DaoSession daoSession) {
        return daoSession.getSearchHistoryDao();
    }

    @Provides
    @Singleton
    DaoSession provideDaoSession(DaoMaster daoMaster) {
        return daoMaster.newSession();
    }

    @Provides
    @Singleton
    DaoMaster provideDaoMaster(SQLiteDatabase sqLiteDatabase) {
        return new DaoMaster(sqLiteDatabase);
    }

    @Provides
    @Singleton
    SQLiteDatabase provideSQLiteDatabase(DaoMaster.DevOpenHelper devOpenHelper) {
        return devOpenHelper.getWritableDatabase();
    }

    @Provides
    @Singleton
    DaoMaster.DevOpenHelper provideDevOpenHelper(Application application) {
        return new DaoMaster.DevOpenHelper(application, "notes-db", null);
    }

}
