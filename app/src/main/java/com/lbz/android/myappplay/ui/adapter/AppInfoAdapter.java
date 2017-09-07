package com.lbz.android.myappplay.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.AppInfo;
import com.lbz.android.myappplay.commom.imageloader.ImageLoader;


/**
 * Created by elitemc on 2017/9/4.
 */

public class AppInfoAdapter extends BaseQuickAdapter<AppInfo, BaseViewHolder> {

    String ICON_BASE_URL = "http://file.market.xiaomi.com/mfc/thumbnail/png/w150q80/";

    private Builder mBuilder;

    public AppInfoAdapter(Builder builder) {
        super(R.layout.template_appinfo);

        this.mBuilder = builder;

        openLoadAnimation();
    }

    @Override
    protected void convert(BaseViewHolder helper, AppInfo item) {
        ImageLoader.load(ICON_BASE_URL + item.getIcon(), (ImageView) helper.getView(R.id.img_app_icon));

        helper.setText(R.id.txt_app_name, item.getDisplayName())
                .setText(R.id.txt_brief, item.getBriefShow());

        TextView txtViewPosition = helper.getView(R.id.txt_position);
        txtViewPosition.setVisibility(mBuilder.isShowPosition? View.VISIBLE:View.GONE);
        txtViewPosition.setText(item.getPosition()+1 +". ");

        TextView txtViewCategory = helper.getView(R.id.txt_category);
        txtViewCategory.setVisibility(mBuilder.isShowCategoryName?View.VISIBLE:View.GONE);
        txtViewCategory.setText(item.getLevel1CategoryName());

        TextView txtViewBrief = helper.getView(R.id.txt_brief);
        txtViewBrief.setVisibility(mBuilder.isShowBrief?View.VISIBLE:View.GONE);
        txtViewBrief.setText(item.getBriefShow());

    }

    public static class  Builder{




        private boolean isShowPosition;
        private boolean isShowCategoryName;
        private boolean isShowBrief;






        public Builder showPosition(boolean b){

            this.isShowPosition =b;
            return this;
        }


        public Builder showCategoryName(boolean b){

            this.isShowCategoryName =b;
            return this;
        }


        public Builder showBrief(boolean b){

            this.isShowBrief =b;
            return this;
        }

        public AppInfoAdapter build(){


            return  new AppInfoAdapter(this);
        }

    }

    public static Builder builder(){

        return new Builder();

    }

}
