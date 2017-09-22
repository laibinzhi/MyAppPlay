package com.lbz.android.myappplay.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lbz.android.myappplay.MyApplication;
import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.AppInfo;
import com.lbz.android.myappplay.commom.Constant;
import com.lbz.android.myappplay.commom.imageloader.ImageLoader;
import com.lbz.android.myappplay.ui.widget.DownloadButtonConntroller;

import zlc.season.rxdownload2.entity.DownloadRecord;

/**
 * Created by elitemc on 2017/9/21.
 */

public class DownloadingAdapter extends BaseQuickAdapter<DownloadRecord, BaseViewHolder> {

    private DownloadButtonConntroller mDownloadButtonConntroller;

    public DownloadingAdapter(MyApplication application) {

        super(R.layout.download_manager_item);

        mDownloadButtonConntroller = new DownloadButtonConntroller(application);

        openLoadAnimation();
    }

    @Override
    protected void convert(BaseViewHolder helper, DownloadRecord item) {


        AppInfo appInfo = mDownloadButtonConntroller.downloadRecord2AppInfo(item);


        ImageLoader.load(Constant.BASE_IMG_URL + appInfo.getIcon(), (ImageView) helper.getView(R.id.img));
        helper.setText(R.id.name, appInfo.getDisplayName());


        helper.addOnClickListener(R.id.btn_download);


        mDownloadButtonConntroller.handClick(helper, item);


    }
}
