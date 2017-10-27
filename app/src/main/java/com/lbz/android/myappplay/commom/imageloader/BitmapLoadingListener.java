package com.lbz.android.myappplay.commom.imageloader;

import android.graphics.Bitmap;

public interface BitmapLoadingListener {

    void onSuccess(Bitmap b);

    void onError();
}
