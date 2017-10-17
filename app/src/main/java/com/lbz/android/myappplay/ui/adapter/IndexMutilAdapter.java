package com.lbz.android.myappplay.ui.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.lbz.android.myappplay.MyApplication;
import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.AppInfo;
import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.bean.SubjectBean;
import com.lbz.android.myappplay.bean.event.AppDetailPageDownLoadBtnClickEvent;
import com.lbz.android.myappplay.commom.Constant;
import com.lbz.android.myappplay.commom.imageloader.ImageLoader;
import com.lbz.android.myappplay.commom.rx.RxBus;
import com.lbz.android.myappplay.ui.activity.AppDetailActivity;
import com.lbz.android.myappplay.ui.activity.HotAppActivity;
import com.lbz.android.myappplay.ui.activity.HotSubjectActivity;
import com.lbz.android.myappplay.ui.activity.SubjectAppActivity;
import com.lbz.android.myappplay.ui.widget.BannerLayout;
import com.lbz.android.myappplay.ui.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

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
    private MyApplication mMyApplication;

    public IndexMutilAdapter(Context context,MyApplication mMyApplication) {
        this.mContext = context;
        this.mMyApplication = mMyApplication;
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

            final List<SubjectBean> themes = mPageBean.getTopTheme();
            List<String> urls = new ArrayList<>(themes.size());

            for (SubjectBean theme : themes) {

                urls.add(ICON_BASE_URL + theme.getMticon());
            }

            bannerViewHolder.mBanner.setViewUrls(urls);

            bannerViewHolder.mBanner.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
                @Override
                public void onItemClick(int position) {
//                    banners.get(position)

                    SubjectBean subjectBean =themes.get(position);
                    //滚播的是app
                    if (subjectBean.getFeaturedType()==1){
                        AppInfo appInfo =new AppInfo();
                        appInfo.setId(subjectBean.getRelatedId());
                        appInfo.setDisplayName(subjectBean.getTitle());
                        goToAppDetail(appInfo,true,true,0);
                    }
                    //滚播的是主题
                    else if (subjectBean.getFeaturedType()==2){

                        Intent intent = new Intent(mContext, SubjectAppActivity.class);

                        intent.putExtra(Constant.SUBJECT, subjectBean);

                        mContext.startActivity(intent);
                    }
                }
            });
        } else if (position == 1) {

            NavIconViewHolder bannerViewHolder = (NavIconViewHolder) holder;

            bannerViewHolder.mLayoutHotApp.setOnClickListener(this);
            bannerViewHolder.mLayoutHotGame.setOnClickListener(this);
            bannerViewHolder.mLayoutHotSubject.setOnClickListener(this);

        } else {
            AppViewHolder viewHolder = (AppViewHolder) holder;
            final AppInfoAdapter mAppInfoAdapter = AppInfoAdapter.builder().showBrief(true).showCategoryName(false).showPosition(false).application(mMyApplication).build();

            if (viewHolder.type == TYPE_APPS) {
                viewHolder.mText.setText(R.string.hot_app);

                mAppInfoAdapter.addData(mPageBean.getListExtrasApp());
            } else if (viewHolder.type == TYPE_GAMES) {
                viewHolder.mText.setText(R.string.hot_game);
                mAppInfoAdapter.addData(mPageBean.getListExtrasGameApp());
            }

            viewHolder.mRecyclerView.setAdapter(mAppInfoAdapter);
            viewHolder.mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                    mMyApplication.setView(view);
                    goToAppDetail(mAppInfoAdapter.getItem(position),false,false,position);
                }
            });
            RxBus.getDefault().toObservable(AppDetailPageDownLoadBtnClickEvent.class)
                    .subscribe(new Consumer<AppDetailPageDownLoadBtnClickEvent>() {
                        @Override
                        public void accept(AppDetailPageDownLoadBtnClickEvent event) throws Exception {
                            mAppInfoAdapter.notifyItemChanged(event.getPosition());
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
        switch (v.getId()){
            case R.id.layout_hot_app:
                mContext.startActivity(new Intent(mContext, HotAppActivity.class));
                break;
            case R.id.layout_hot_subject:
                mContext.startActivity(new Intent(mContext, HotSubjectActivity.class));
                break;
        }

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

    public void goToAppDetail(AppInfo appInfo,boolean closeAnimation,boolean isFromBanner,int position){
        Intent intent =new Intent(mContext, AppDetailActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("appinfo",appInfo);
        intent.putExtra("closeAnimation",closeAnimation);
        intent.putExtra("isFromBanner",isFromBanner);
        mContext.startActivity(intent);
    }
}
