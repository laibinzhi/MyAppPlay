package com.lbz.android.myappplay.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.commom.imageloader.ImageLoader;
import com.lbz.android.myappplay.ui.widget.DividerItemDecoration;


import butterknife.Bind;
import butterknife.ButterKnife;

import static com.lbz.android.myappplay.data.http.ApiService.ICON_BASE_URL;

/**
 * Created by elitemc on 2017/7/25.
 */
public class SubjectAppAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    public static final int TYPE_PICTURE = 1;
    private static final int TYPE_DESC = 2;
    private static final int TYPE_APPS = 3;

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private PageBean mPageBean;
    private String icon_url;

    public SubjectAppAdapter(Context context, String icon_url) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.icon_url = icon_url;
    }

    public void setData(PageBean pageBean) {
        this.mPageBean = pageBean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_PICTURE) {
            return new ImageViewHolder(mLayoutInflater.inflate(R.layout.subject_image, parent, false));
        } else if (viewType == TYPE_DESC) {
            return new DESCViewHolder(mLayoutInflater.inflate(R.layout.subject_desc, parent, false));
        } else if (viewType == TYPE_APPS) {
            return new AppViewHolder(mLayoutInflater.inflate(R.layout.template_recycler_view, null, false), TYPE_APPS);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {

            ImageViewHolder imageViewHolder = (ImageViewHolder) holder;

            ImageLoader.load(ICON_BASE_URL + icon_url, imageViewHolder.imageView);

        } else if (position == 1) {

            DESCViewHolder bannerViewHolder = (DESCViewHolder) holder;

            bannerViewHolder.desc_tv.setText(mPageBean.getDescripttion());

        } else {
            AppViewHolder viewHolder = (AppViewHolder) holder;
            AppInfoAdapter mAppInfoAdapter = AppInfoAdapter.builder().showBrief(true).showCategoryName(false).showPosition(false).build();

            mAppInfoAdapter.addData(mPageBean.getListApp());

            viewHolder.mRecyclerView.setAdapter(mAppInfoAdapter);
            viewHolder.mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_PICTURE;
        } else if (position == 1) {
            return TYPE_DESC;
        } else if (position == 2) {
            return TYPE_APPS;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public void onClick(View v) {

    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.subject_image)
        ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class DESCViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.subject_desc)
        TextView desc_tv;

        public DESCViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class AppViewHolder extends RecyclerView.ViewHolder {


        @Bind(R.id.recycler_view)
        RecyclerView mRecyclerView;


        public AppViewHolder(View itemView, int type) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            initRecyclerView();

        }

        private void initRecyclerView() {

            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext) {

                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });

            DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST);

            mRecyclerView.addItemDecoration(itemDecoration);

        }
    }


}
