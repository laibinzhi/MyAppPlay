package com.lbz.android.myappplay.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.AppInfo;
import com.lbz.android.myappplay.commom.Constant;
import com.lbz.android.myappplay.commom.imageloader.ImageLoader;
import com.lbz.android.myappplay.ui.widget.DownloadProgressButton;

/**
 * Created by elitemc on 2017/9/21.
 */

public class UpdateAppAdapter extends BaseQuickAdapter<AppInfo, BaseViewHolder> {


    public UpdateAppAdapter() {

        super(R.layout.template_app_update);

        openLoadAnimation();
    }

    @Override
    protected void convert(final BaseViewHolder helper, final AppInfo item) {

        ImageLoader.load(Constant.BASE_IMG_URL+item.getIcon(), ((ImageView) helper.getView(R.id.img_app_icon)));

        helper.setText(R.id.txt_app_name, item.getDisplayName());
        helper.setText(R.id.txt_app_version_name,item.getLocalVersionName()+ "->"+item.getVersionName());

        DownloadProgressButton btn = helper.getView(R.id.btn_download);

        btn.setText("升级");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
    }
}
