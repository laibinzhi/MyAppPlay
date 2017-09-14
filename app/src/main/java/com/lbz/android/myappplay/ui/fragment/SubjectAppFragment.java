package com.lbz.android.myappplay.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.di.component.AppComponent;
import com.lbz.android.myappplay.di.component.DaggerAppInfoComponent;
import com.lbz.android.myappplay.di.module.AppInfoModule;
import com.lbz.android.myappplay.presenter.AppInfoPresenter;
import com.lbz.android.myappplay.presenter.contract.AppInfoContract;
import com.lbz.android.myappplay.ui.adapter.SubjectAppAdapter;
import com.lbz.android.myappplay.ui.widget.DividerItemDecoration;

import butterknife.Bind;


/**
 * Created by elitemc on 2017/9/8.
 */

public class SubjectAppFragment extends ProgressFragment<AppInfoPresenter> implements AppInfoContract.AppInfoView {

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    SubjectAppAdapter mAdapter;

    private int subject_id;

    public String icon_url;

    public SubjectAppFragment(int subject_id, String icon_url) {

        this.subject_id = subject_id;
        this.icon_url = icon_url;

    }

    @Override
    protected int setLayout() {
        return R.layout.template_recycler_view;
    }

    @Override
    protected void setFragmentComponent(AppComponent appComponent) {
        DaggerAppInfoComponent.builder().appComponent(appComponent).appInfoModule(new AppInfoModule(this))
                .build().injectSubjectAppFragment(this);
    }

    @Override
    protected void init() {
        //请求数据
        initRecyclerView();
        mPresenter.requestSubjectApps(subject_id, 0);
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    public void showData(PageBean pageBean) {

        mAdapter = new SubjectAppAdapter(getActivity(),this.icon_url,mMyApplication);

        mAdapter.setData(pageBean);

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onLoadMoreComplete() {

    }
}
