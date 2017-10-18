package com.lbz.android.myappplay.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lbz.android.myappplay.MyApplication;
import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.AppInfo;
import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.bean.SubjectBean;
import com.lbz.android.myappplay.bean.event.AppDetailPageDownLoadBtnClickEvent;
import com.lbz.android.myappplay.commom.Constant;
import com.lbz.android.myappplay.commom.rx.RxBus;
import com.lbz.android.myappplay.ui.activity.AppDetailActivity;
import com.lbz.android.myappplay.ui.activity.SubjectAppActivity;
import com.lbz.android.myappplay.ui.widget.DividerItemDecoration;
import com.lbz.android.myappplay.ui.widget.WrapHeightPaddingGridView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;


/**
 * Created by elitemc on 2017/7/25.
 */
public class GamesIndexAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    public static final int TYPE_GRID_VIEW = 1;
    private static final int TYPE_ICON = 2;
    private static final int TYPE_GAMES = 3;

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private PageBean mPageBean;
    private MyApplication mMyApplication;

    public GamesIndexAdapter(Context context, MyApplication mMyApplication) {
        this.mContext = context;
        this.mMyApplication = mMyApplication;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public void setData(PageBean pageBean) {
        this.mPageBean = pageBean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_GRID_VIEW) {

            return new GridViewHolder(mLayoutInflater.inflate(R.layout.template_grid_view, parent, false));

        } else if (viewType == TYPE_ICON) {

            return new NavIconViewHolder(mLayoutInflater.inflate(R.layout.template_nav_icon_game, parent, false));

        }  else if (viewType == TYPE_GAMES) {

            return new AppViewHolder(mLayoutInflater.inflate(R.layout.template_recycler_view, null, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            GridViewHolder gridViewViewHolder = (GridViewHolder) holder;

            final List<SubjectBean> subjects = mPageBean.getTopTheme();

            HotSubjectAdapter mAdapter = new HotSubjectAdapter(mContext);
            mAdapter.setData(subjects);
            gridViewViewHolder.mGridView.setAdapter(mAdapter);
            gridViewViewHolder.mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    SubjectBean subjectBean =subjects.get(position);

                    //gridview的是app
                    if (subjectBean.getFeaturedType()==1){
                        AppInfo appInfo =new AppInfo();
                        appInfo.setId(subjectBean.getRelatedId());
                        appInfo.setDisplayName(subjectBean.getTitle());
                        goToAppDetail(appInfo,true,true,0);
                    }
                    //gridview的是主题
                    else if (subjectBean.getFeaturedType()==2){

                        Intent intent = new Intent(mContext, SubjectAppActivity.class);

                        intent.putExtra(Constant.SUBJECT, subjectBean);

                        mContext.startActivity(intent);
                    }

                }
            });

        } else if (position == 1) {

            NavIconViewHolder bannerViewHolder = (NavIconViewHolder) holder;

            bannerViewHolder.mLayoutRankGame.setOnClickListener(this);
            bannerViewHolder.mLayoutNewGame.setOnClickListener(this);
            bannerViewHolder.mLayoutGameSubject.setOnClickListener(this);

        } else {
            AppViewHolder viewHolder = (AppViewHolder) holder;
            final AppInfoAdapter mAppInfoAdapter = AppInfoAdapter.builder().showBrief(true).showCategoryName(false).showPosition(false).application(mMyApplication).build();

            mAppInfoAdapter.addData(mPageBean.getListApp());

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
            return TYPE_GRID_VIEW;
        } else if (position == 1) {
            return TYPE_ICON;
        } else if (position == 2) {
            return TYPE_GAMES;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

        }

    }

    class GridViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.grid_view)
        WrapHeightPaddingGridView mGridView;

        public GridViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            initGridView();
        }

        private void initGridView() {
            mGridView.setNumColumns(2);
        }
    }

    class NavIconViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.layout_rank_game)
        LinearLayout mLayoutRankGame;
        @Bind(R.id.layout_new_game)
        LinearLayout mLayoutNewGame;
        @Bind(R.id.layout_game_subject)
        LinearLayout mLayoutGameSubject;

        public NavIconViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class AppViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.recycler_view)
        RecyclerView mRecyclerView;

        public AppViewHolder(View itemView) {
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

    public void goToAppDetail(AppInfo appInfo,boolean closeAnimation,boolean isFromBanner,int position){
        Intent intent =new Intent(mContext, AppDetailActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("appinfo",appInfo);
        intent.putExtra("closeAnimation",closeAnimation);
        intent.putExtra("isFromBanner",isFromBanner);
        mContext.startActivity(intent);
    }

}
