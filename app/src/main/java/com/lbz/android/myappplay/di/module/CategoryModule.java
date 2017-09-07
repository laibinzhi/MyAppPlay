package com.lbz.android.myappplay.di.module;



import com.lbz.android.myappplay.data.CategoryModel;
import com.lbz.android.myappplay.data.http.ApiService;
import com.lbz.android.myappplay.presenter.contract.CategoryContract;




import dagger.Module;
import dagger.Provides;


@Module
public class CategoryModule {

    private CategoryContract.CategoryView mView;

    public CategoryModule(CategoryContract.CategoryView view) {


        this.mView = view;
    }


    @Provides
    public CategoryContract.CategoryView provideView() {

        return mView;
    }


    @Provides
    public CategoryContract.ICategoryModel privodeModel(ApiService apiService) {

        return new CategoryModel(apiService);
    }



}
