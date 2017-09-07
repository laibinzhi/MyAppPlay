package com.lbz.android.myappplay.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.bean.ThemeBean;
import com.lbz.android.myappplay.commom.imageloader.ImageLoader;
import com.lbz.android.myappplay.ui.widget.BannerLayout;
import com.lbz.android.myappplay.ui.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.lbz.android.myappplay.data.http.ApiService.ICON_BASE_URL;

/**
 * Created by elitemc on 2017/7/25.
 */
public class IndexMutilAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    public static final int TYPE_BANNER = 1;
    private static final int TYPE_ICON = 2;
    private static final int TYPE_APPS = 3;
    private static final int TYPE_GAMES = 4;

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private PageBean mPageBean;

    public IndexMutilAdapter(Context context) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public void setData(PageBean pageBean) {
        this.mPageBean = pageBean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_BANNER) {
            return new BannerViewHolder(mLayoutInflater.inflate(R.layout.template_banner, parent, false));
        } else if (viewType == TYPE_ICON) {
            return new NavIconViewHolder(mLayoutInflater.inflate(R.layout.template_nav_icon, parent, false));
        } else if (viewType == TYPE_APPS) {

            return new AppViewHolder(mLayoutInflater.inflate(R.layout.template_recycler_view_with_title, null, false), TYPE_APPS);
        } else if (viewType == TYPE_GAMES) {

            return new AppViewHolder(mLayoutInflater.inflate(R.layout.template_recycler_view_with_title, null, false), TYPE_GAMES);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;

            List<ThemeBean> themes = mPageBean.getTopTheme();
            List<String> urls = new ArrayList<>(themes.size());

            for (ThemeBean theme : themes) {

                urls.add(ICON_BASE_URL + theme.getMticon());
            }

            bannerViewHolder.mBanner.setViewUrls(urls);

            bannerViewHolder.mBanner.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
                @Override
                public void onItemClick(int position) {
//                    banners.get(position)
                }
            });
        } else if (position == 1) {

            NavIconViewHolder bannerViewHolder = (NavIconViewHolder) holder;

            bannerViewHolder.mLayoutHotApp.setOnClickListener(this);
            bannerViewHolder.mLayoutHotGame.setOnClickListener(this);
            bannerViewHolder.mLayoutHotSubject.setOnClickListener(this);

        } else {
            AppViewHolder viewHolder = (AppViewHolder) holder;
            AppInfoAdapter mAppInfoAdapter = AppInfoAdapter.builder().showBrief(true).showCategoryName(false).showPosition(false).build();

            if (viewHolder.type == TYPE_APPS) {
                viewHolder.mText.setText("热门应用");

                mAppInfoAdapter.addData(mPageBean.getListExtrasApp());
            } else if (viewHolder.type == TYPE_GAMES) {
                viewHolder.mText.setText("热门游戏");
                mAppInfoAdapter.addData(mPageBean.getListExtrasGameApp());
            }

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
            return TYPE_BANNER;
        } else if (position == 1) {
            return TYPE_ICON;
        } else if (position == 2) {
            return TYPE_APPS;
        } else if (position == 3) {
            return TYPE_GAMES;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public void onClick(View v) {

    }

    class BannerViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.banner)
        BannerLayout mBanner;

        public BannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mBanner.setImageLoader(new ImgLoader());
        }
    }

    class NavIconViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.layout_hot_app)
        LinearLayout mLayoutHotApp;
        @Bind(R.id.layout_hot_game)
        LinearLayout mLayoutHotGame;
        @Bind(R.id.layout_hot_subject)
        LinearLayout mLayoutHotSubject;

        public NavIconViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class AppViewHolder extends RecyclerView.ViewHolder {


        @Bind(R.id.text)
        TextView mText;
        @Bind(R.id.recycler_view)
        RecyclerView mRecyclerView;


        int type;

        public AppViewHolder(View itemView, int type) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.type = type;

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

    class ImgLoader implements BannerLayout.ImageLoader {


        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            ImageLoader.load(path, imageView);
        }
    }
}
