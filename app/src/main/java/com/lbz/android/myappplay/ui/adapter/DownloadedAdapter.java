package com.lbz.android.myappplay.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.commom.apkparset.AndroidApk;

/**
 * Created by elitemc on 2017/9/21.
 */

public class DownloadedAdapter extends BaseQuickAdapter<AndroidApk,BaseViewHolder> {


    public DownloadedAdapter() {

        super(R.layout.template_app_downloading);

        openLoadAnimation();
    }

    @Override
    protected void convert(BaseViewHolder helper, AndroidApk item) {
        ((ImageView) helper.getView(R.id.img_app_icon)).setImageDrawable(item.getDrawable());
        helper.setText(R.id.txt_app_name,item.getAppName());

    }
}
