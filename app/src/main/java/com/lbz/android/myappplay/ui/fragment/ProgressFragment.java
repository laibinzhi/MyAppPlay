package com.lbz.android.myappplay.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lbz.android.myappplay.MyApplication;
import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.di.component.AppComponent;
import com.lbz.android.myappplay.presenter.BasePresenter;
import com.lbz.android.myappplay.ui.BaseView;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by elitemc on 2017/7/18.
 */
public abstract class ProgressFragment<T extends BasePresenter> extends Fragment implements BaseView {
    private FrameLayout mRootView;

    private View mViewProgress;
    private FrameLayout mViewContent;
    private View mViewEmpty;
    private TextView mTextError;

    protected MyApplication mMyApplication;

    @Inject
    T mPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = (FrameLayout) inflater.inflate(R.layout.fragment_progress, container, false);
        mViewProgress = mRootView.findViewById(R.id.view_progress);
        mViewContent = (FrameLayout) mRootView.findViewById(R.id.view_content);
        mViewEmpty = mRootView.findViewById(R.id.view_empty);
        mTextError = (TextView) mRootView.findViewById(R.id.text_tip);
        mViewEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmptyViewClick();
            }
        });
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mMyApplication = (MyApplication) getActivity().getApplication();
        setFragmentComponent(mMyApplication.getAppComponent());
        setRealContentView();
        init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void showError(String msg) {
        showEmptyView(msg);
    }

    @Override
    public void hideLoading() {
        showContentView();
    }

    @Override
    public void showLoading() {
        showProgressView();
    }

    public void onEmptyViewClick() {

    }

    private void showProgressView() {
        showView(R.id.view_progress);
    }

    private void showContentView() {
        showView(R.id.view_content);
    }

    private void showEmptyView() {
        showView(R.id.view_empty);
    }

    private void showView(int viewId) {
        for (int i = 0; i < mRootView.getChildCount(); i++) {
            if (mRootView.getChildAt(i).getId() == viewId) {
                mRootView.getChildAt(i).setVisibility(View.VISIBLE);
            } else {
                mRootView.getChildAt(i).setVisibility(View.GONE);
            }
        }
    }


    private void showEmptyView(int resId) {
        showEmptyView();
        mTextError.setText(resId);
    }

    private void showEmptyView(String msg) {
        showEmptyView();
        mTextError.setText(msg);
    }

    private void showEmpty(String msg) {

    }

    private void setRealContentView() {
        View realContentView = LayoutInflater.from(getActivity()).inflate(setLayout(), mViewContent, true);
        ButterKnife.bind(this, realContentView);
    }

    protected abstract int setLayout();

    protected abstract void setFragmentComponent(AppComponent appComponent);

    protected abstract void init();

}
