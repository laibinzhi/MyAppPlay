package com.lbz.android.myappplay.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lbz.android.myappplay.R;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

/**
 * Created by elitemc on 2017/7/11.
 */
public class GamesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_games, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
        imageView.setImageDrawable(new IconicsDrawable(getActivity())
                .icon(GoogleMaterial.Icon.gmd_settings)
                .color(Color.RED)
                .sizeDp(80));
        return view;
    }
}
