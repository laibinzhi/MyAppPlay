package com.lbz.android.myappplay.ui.fragment;

import android.util.Log;

import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.SubjectBean;
import com.lbz.android.myappplay.di.component.AppComponent;
import com.lbz.android.myappplay.di.component.DaggerSubjectComponent;
import com.lbz.android.myappplay.di.module.SubjectModule;
import com.lbz.android.myappplay.presenter.SubjectPresenter;
import com.lbz.android.myappplay.presenter.contract.SubjectContract;
import com.lbz.android.myappplay.ui.adapter.HotSubjectAdapter;
import com.lbz.android.myappplay.ui.widget.PaddingGridView;

import java.util.List;

import butterknife.Bind;

/**
 * Created by elitemc on 2017/9/8.
 */

public class HotSubjectFragment extends ProgressFragment<SubjectPresenter> implements SubjectContract.SubjectView {

    private static final String TAG = "HotSubjectFragment";

    @Bind(R.id.subject_gridview)
    PaddingGridView mGridView;

    HotSubjectAdapter mAdapter;

    @Override
    protected int setLayout() {
        return R.layout.fragment_hot_subject;
    }

    @Override
    protected void setFragmentComponent(AppComponent appComponent) {
        DaggerSubjectComponent.builder().appComponent(appComponent)
                .subjectModule(new SubjectModule(this))
                .build().inject(this);
    }

    @Override
    protected void init() {
        initGridView();
        mPresenter.requestDatas();
    }

    private void initGridView() {
        mGridView.setNumColumns(2);
        mAdapter = new HotSubjectAdapter(getActivity());
        mGridView.setAdapter(mAdapter);
    }

    @Override
    public void showData(List<SubjectBean> subjectList) {
        Log.e(TAG, "showData: subjectList="+subjectList.size());
        mAdapter.setData(subjectList);
    }


}
