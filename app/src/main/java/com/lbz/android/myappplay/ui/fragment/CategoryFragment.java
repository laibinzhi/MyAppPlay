package com.lbz.android.myappplay.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.Category;
import com.lbz.android.myappplay.commom.Constant;
import com.lbz.android.myappplay.di.component.AppComponent;
import com.lbz.android.myappplay.di.component.DaggerCategoryComponent;
import com.lbz.android.myappplay.di.module.CategoryModule;
import com.lbz.android.myappplay.presenter.CategoryPresenter;
import com.lbz.android.myappplay.presenter.contract.CategoryContract;
import com.lbz.android.myappplay.ui.activity.CategoryAppActivity;
import com.lbz.android.myappplay.ui.adapter.CategoryAdapter;
import com.lbz.android.myappplay.ui.widget.DividerItemDecoration;

import java.util.List;

import butterknife.Bind;

/**
 * Created by lbz on 2017/7/11.
 */
public class CategoryFragment extends ProgressFragment<CategoryPresenter> implements CategoryContract.CategoryView {


    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    CategoryAdapter mAdapter;

    @Override
    protected int setLayout() {
        return R.layout.template_recycler_view;
    }

    @Override
    protected void setFragmentComponent(AppComponent appComponent) {

        DaggerCategoryComponent.builder().appComponent(appComponent)
                .categoryModule(new CategoryModule(this))
                .build().inject(this);

    }

    @Override
    protected void init() {
        initRecycleView();
        mPresenter.requestDatas(false);
    }

    protected void initRecycleView() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);

        mRecyclerView.addItemDecoration(itemDecoration);
        mAdapter = new CategoryAdapter();

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), CategoryAppActivity.class);

                intent.putExtra(Constant.CATEGORY, mAdapter.getData().get(position));

                startActivity(intent);

            }
        });
    }

    @Override
    public void showData(List<Category> categories) {
        mAdapter.addData(categories);
    }

    @Override
    public void onEmptyViewClick() {
        mPresenter.requestDatas(false);
    }

}
