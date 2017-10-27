package com.lbz.android.myappplay.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.AppInfo;
import com.lbz.android.myappplay.bean.event.DownloadFinishEvent;
import com.lbz.android.myappplay.commom.rx.RxBus;
import com.lbz.android.myappplay.di.component.AppComponent;
import com.lbz.android.myappplay.di.component.DaggerAppManagerComponent;
import com.lbz.android.myappplay.di.module.AppManagerModule;
import com.lbz.android.myappplay.ui.adapter.DownloadingAdapter;

import java.util.List;

import butterknife.Bind;
import io.reactivex.functions.Consumer;
import zlc.season.rxdownload2.entity.DownloadRecord;

/**
 * Created by lbz on 2017/9/21.
 */

public class DownloadingFragment extends AppManangerFragment{

    @Bind(R.id.empty_text)
    TextView mEmptyView;

    private DownloadingAdapter mAdapter;

    @Override
    public int setLayout() {
        return R.layout.template_recycler_view_with_empty_view;
    }

    @Override
    public void init() {
        super.init();
        setupEmptyView();
        mPresenter.getDownlodingApps();
        RxBus.getDefault().toObservable(DownloadFinishEvent.class)
                .subscribe(new Consumer<DownloadFinishEvent>() {
                    @Override
                    public void accept(DownloadFinishEvent downloadFinishEvent) throws Exception {
                        mAdapter.remove(downloadFinishEvent.getHolder().getAdapterPosition());
                        if (mAdapter.getData().size()==0){
                            showEmptyView();
                        }
                    }
                });

    }

    @Override
    protected RecyclerView.Adapter setupAdapter() {
        mAdapter = new DownloadingAdapter(mMyApplication);
        return mAdapter;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void setFragmentComponent(AppComponent appComponent) {

        DaggerAppManagerComponent.builder().appComponent(appComponent)
                .appManagerModule(new AppManagerModule(this))
                .build().injectDownloadingFragment(this);
    }

    @Override
    public void showDownloading(List<DownloadRecord> downloadRecords) {

        if (downloadRecords.size()==0){
            showEmptyView();
        }else {
            showRecyclerView();
        }
        mAdapter.addData(downloadRecords);

    }

    @Override
    public void showUpdateApps(List<AppInfo> apps) {

    }

    public void showEmptyView(){
        mRecyclerView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
    }

    public void showRecyclerView(){
        mRecyclerView.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
    }

    private void setupEmptyView() {
        mEmptyView.setText("当前暂无下载任务");
        mEmptyView.setVisibility(View.VISIBLE);
        mEmptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

}
