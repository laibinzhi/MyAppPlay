package com.lbz.android.myappplay.ui.adapter;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lbz.android.myappplay.MyApplication;
import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.AppInfo;
import com.lbz.android.myappplay.commom.Constant;
import com.lbz.android.myappplay.commom.imageloader.ImageLoader;
import com.lbz.android.myappplay.ui.widget.DownloadButtonConntroller;

import io.reactivex.functions.Action;
import zlc.season.rxdownload2.entity.DownloadRecord;

/**
 * Created by elitemc on 2017/9/21.
 */

public class DownloadingAdapter extends BaseQuickAdapter<DownloadRecord, BaseViewHolder> {

    private DownloadButtonConntroller mDownloadButtonConntroller;

    public DownloadingAdapter(MyApplication application) {

        super(R.layout.downloading_app_item);

        mDownloadButtonConntroller = new DownloadButtonConntroller(application);

        openLoadAnimation();
    }

    @Override
    protected void convert(final BaseViewHolder helper, final DownloadRecord item) {


        final AppInfo appInfo = mDownloadButtonConntroller.downloadRecord2AppInfo(item);


        ImageLoader.load(Constant.BASE_IMG_URL + appInfo.getIcon(), (ImageView) helper.getView(R.id.img));
        helper.setText(R.id.name, appInfo.getDisplayName());


        helper.addOnClickListener(R.id.btn_download);

        mDownloadButtonConntroller.handClick(helper, item);

        final LinearLayout whole_layout = helper.getView(R.id.whole_layout);
        whole_layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Snackbar.make(whole_layout, "是否删除《"+appInfo.getDisplayName()+"》下载",Snackbar.LENGTH_LONG).setAction("删除", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mDownloadButtonConntroller.deleteServiceDownload(item)
                                .doFinally(new Action() {
                                    @Override
                                    public void run() throws Exception {
                                        remove(helper.getAdapterPosition());
                                        Snackbar.make(whole_layout,"已成功删除该下载",Snackbar.LENGTH_LONG).show();
                                    }
                                })
                                .subscribe();
                    }
                }).show();
                return false;
            }
        });
    }
}
