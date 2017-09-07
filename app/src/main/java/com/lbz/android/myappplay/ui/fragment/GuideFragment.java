package com.lbz.android.myappplay.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lbz.android.myappplay.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by elitemc on 2017/7/13.
 */
public class GuideFragment extends Fragment {

    @Bind(R.id.imgView)
    ImageView mImgView;
    @Bind(R.id.text)
    TextView mText;
    @Bind(R.id.rootView)
    LinearLayout mRootView;

    private View mView;

    public static final String IMG_ID = "IMG_ID";
    public static final String COLOR_ID = "COLOR_ID";
    public static final String TEXT_ID = "TEXT_ID";


    public static GuideFragment newInstance(int imgResId, int bgColorResId, int textResId) {
        GuideFragment fragment = new GuideFragment();
        Bundle args = new Bundle();
        args.putInt(IMG_ID, imgResId);
        args.putInt(COLOR_ID, bgColorResId);
        args.putInt(TEXT_ID, textResId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_guide, container, false);
        ButterKnife.bind(this, mView);
        initData();
        return mView;
    }

    private void initData() {
        Bundle args = getArguments();
        int colorId = args.getInt(COLOR_ID);
        int imgId = args.getInt(IMG_ID);
        int textId = args.getInt(TEXT_ID);


        mRootView.setBackgroundColor(getResources().getColor(colorId));
        mImgView.setImageResource(imgId);
        mText.setText(textId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
