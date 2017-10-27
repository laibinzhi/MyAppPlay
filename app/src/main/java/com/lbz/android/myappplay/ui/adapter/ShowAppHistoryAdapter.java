package com.lbz.android.myappplay.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lbz.android.myappplay.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lbz on 2017/9/14.
 */

public class ShowAppHistoryAdapter extends BaseAdapter{

    private Context mContext;
    private List<String> mDatas;

    public ShowAppHistoryAdapter(Context context){
        this.mContext = context;
        mDatas =new ArrayList<>();
    }

    public void setData(List<String> data){
        this.mDatas =data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null
                || convertView.getTag(R.layout.history_gridview_item) == null) {

            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.history_gridview_item, null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.history_app_name);
            convertView.setTag(R.layout.history_gridview_item);
        } else {
            viewHolder = (ViewHolder) convertView
                    .getTag(R.layout.history_gridview_item);
        }

        viewHolder.textView.setText(mDatas.get(position));

        return convertView;
    }


    class ViewHolder{

        TextView textView;
    }
}
