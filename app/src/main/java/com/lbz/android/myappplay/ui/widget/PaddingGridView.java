package com.lbz.android.myappplay.ui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.GridView;

import com.lbz.android.myappplay.R;

/**
 * Created by lbz on 2017/9/8.
 */

public class PaddingGridView extends GridView {
    private int mTouchPaddingLeft = 0;
    private int mTouchPaddingRight = 0;

    public PaddingGridView(Context paramContext) {
        super(paramContext);
        initDefaultPadding();
    }

    public PaddingGridView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        initDefaultPadding();
    }

    public PaddingGridView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        initDefaultPadding();
    }

    private void initDefaultPadding() {
        try {
            this.mTouchPaddingLeft = getContext().getResources().getDimensionPixelSize(R.dimen.main_padding);
            this.mTouchPaddingRight = 0;
            return;
        } catch (Resources.NotFoundException localNotFoundException) {
            Log.e("PaddingGridView", "Resource main_padding not found");
        }
    }

    private boolean isOutOfTouchRange(MotionEvent paramMotionEvent) {
        return (paramMotionEvent.getX() < getPaddingLeft() + this.mTouchPaddingLeft) || (paramMotionEvent.getX() > getWidth() - getPaddingLeft() - this.mTouchPaddingRight);
    }

    public boolean onTouchEvent(MotionEvent paramMotionEvent) {
        if (isOutOfTouchRange(paramMotionEvent)) {
            return true;
        }
        return super.onTouchEvent(paramMotionEvent);
    }
}
