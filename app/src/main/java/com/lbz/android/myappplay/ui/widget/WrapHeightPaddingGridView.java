package com.lbz.android.myappplay.ui.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by elitemc on 2017/10/18.
 */

public class WrapHeightPaddingGridView extends PaddingGridView {


    public WrapHeightPaddingGridView(Context paramContext) {
        super(paramContext);
    }

    public WrapHeightPaddingGridView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    public WrapHeightPaddingGridView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //  AT_MOST参数表示控件可以自由调整大小，最大不超过Integer.MAX_VALUE/4
        int height=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, height);
    }
}
