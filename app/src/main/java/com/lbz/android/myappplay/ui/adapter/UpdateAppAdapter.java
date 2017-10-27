package com.lbz.android.myappplay.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lbz.android.myappplay.MyApplication;
import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.AppInfo;
import com.lbz.android.myappplay.commom.Constant;
import com.lbz.android.myappplay.commom.imageloader.ImageLoader;
import com.lbz.android.myappplay.ui.widget.DownloadButtonConntroller;
import com.lbz.android.myappplay.ui.widget.DownloadProgressButton;

/**
 * Created by lbz on 2017/9/21.
 */

public class UpdateAppAdapter extends BaseQuickAdapter<AppInfo, BaseViewHolder> {

    private DownloadButtonConntroller mDownloadButtonConntroller;

    public UpdateAppAdapter(MyApplication mMyApplication) {

        super(R.layout.template_app_update);

        openLoadAnimation();

        mDownloadButtonConntroller = new DownloadButtonConntroller(mMyApplication);

    }

    @Override
    protected void convert(final BaseViewHolder helper, final AppInfo item) {

        ImageLoader.load(Constant.BASE_IMG_URL+item.getIcon(), ((ImageView) helper.getView(R.id.img_app_icon)));

        helper.setText(R.id.txt_app_name, item.getDisplayName());
        helper.setText(R.id.txt_app_version_name,item.getLocalVersionName()+ "->"+item.getVersionName());

        helper.addOnClickListener(R.id.btn_download);

        View viewBtn  = helper.getView(R.id.btn_download);

        if (viewBtn instanceof DownloadProgressButton){

            DownloadProgressButton btn = (DownloadProgressButton) viewBtn;


            mDownloadButtonConntroller.handClick(btn,item);

        }
    }
}
