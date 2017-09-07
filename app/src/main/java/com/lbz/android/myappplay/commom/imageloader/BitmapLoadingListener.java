package com.lbz.android.myappplay.commom.imageloader;

import android.graphics.Bitmap;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5market.common.imageloader
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

public interface BitmapLoadingListener {

    void onSuccess(Bitmap b);

    void onError();
}
