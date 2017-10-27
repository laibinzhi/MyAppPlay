package com.lbz.android.myappplay.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.SubjectBean;
import com.lbz.android.myappplay.commom.Constant;
import com.lbz.android.myappplay.commom.imageloader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lbz on 2017/9/8.
 */

public class HotSubjectAdapter extends BaseAdapter {

    private Context mContext;
    public List<SubjectBean> mData;

    public HotSubjectAdapter(Context context) {
        this.mContext = context;
        this.mData = new ArrayList<>();
    }

    public void setData(List<SubjectBean> subjectList) {
        this.mData = subjectList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        if (convertView == null || convertView.getTag(R.layout.grid_view_item) == null) {

            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.grid_view_item, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iamge);
            convertView.setTag(R.layout.grid_view_item);
        } else {
            viewHolder = (ViewHolder) convertView.getTag(R.layout.grid_view_item);
        }

        final SubjectBean bean = mData.get(position);

        ImageLoader.load(Constant.BASE_IMG_URL + bean.getMticon(), viewHolder.imageView);

        return convertView;
    }

    class ViewHolder {

        ImageView imageView;

    }

}
