package com.lbz.android.myappplay.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.Category;
import com.lbz.android.myappplay.commom.Constant;
import com.lbz.android.myappplay.commom.imageloader.ImageLoader;

/**
 * Created by elitemc on 2017/9/6.
 */

public class CategoryAdapter extends BaseQuickAdapter<Category, BaseViewHolder> {

    public CategoryAdapter() {
        super(R.layout.template_category);
    }

    @Override
    protected void convert(BaseViewHolder helper, Category item) {
        helper.setText(R.id.text_name, item.getName());

        ImageLoader.load(Constant.BASE_IMG_URL + item.getIcon(), (ImageView) helper.getView(R.id.img_icon));
    }
}
