package com.lbz.android.myappplay.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.commom.apkparset.AndroidApk;
import com.lbz.android.myappplay.commom.util.AppUtils;
import com.lbz.android.myappplay.ui.widget.DownloadProgressButton;

/**
 * Created by elitemc on 2017/9/21.
 */

public class InstallAppAdapter extends BaseQuickAdapter<AndroidApk, BaseViewHolder> {


    public InstallAppAdapter() {

        super(R.layout.template_app_install);

        openLoadAnimation();
    }

    @Override
    protected void convert(final BaseViewHolder helper, final AndroidApk item) {
        ((ImageView) helper.getView(R.id.img_app_icon)).setImageDrawable(item.getDrawable());
        helper.setText(R.id.txt_app_name, item.getAppName());
        helper.setText(R.id.txt_app_pgname, item.getPackageName());

        DownloadProgressButton btn = helper.getView(R.id.btn_download);

        btn.setText("卸载");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AppUtils.uninstallApk(mContext, item.getPackageName())) {
                    remove(helper.getAdapterPosition());
                }

            }
        });
    }
}
