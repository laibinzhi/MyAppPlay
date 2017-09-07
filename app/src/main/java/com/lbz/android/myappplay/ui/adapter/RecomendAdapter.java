package com.lbz.android.myappplay.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.AppInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by elitemc on 2017/7/12.
 */
public class RecomendAdapter extends RecyclerView.Adapter<RecomendAdapter.RecomendViewHolder> {


    private List<AppInfo> mAppList;

    private Context mContext;

    public RecomendAdapter(List<AppInfo> appInfos, Context context) {
        this.mAppList = appInfos;
        this.mContext = context;

    }


    @Override
    public RecomendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecomendViewHolder(LayoutInflater.from(mContext).inflate(R.layout.template_recomend_app, parent, false));
    }

    @Override
    public void onBindViewHolder(RecomendViewHolder holder, int position) {
        AppInfo appInfo = mAppList.get(position);
        String ICON_BASE_URL = "http://file.market.xiaomi.com/mfc/thumbnail/png/w150q80/";
        Picasso.with(mContext).load(ICON_BASE_URL + appInfo.getIcon()).resize(150,150).into(holder.imgIcon);
        holder.textTitle.setText(appInfo.getDisplayName());
        holder.textSize.setText(appInfo.getApkSize() / 1024 / 1024 + "MB");
    }

    @Override
    public int getItemCount() {
        return mAppList.size();
    }

    public class RecomendViewHolder extends RecyclerView.ViewHolder {


        @Bind(R.id.img_icon)
        ImageView imgIcon;
        @Bind(R.id.text_title)
        TextView textTitle;
        @Bind(R.id.text_size)
        TextView textSize;
        @Bind(R.id.btn_dl)
        Button btnDl;

        public RecomendViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
