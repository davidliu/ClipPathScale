package com.test.clippathscale;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;


public class RoundedFrameLayout extends FrameLayout {

    Path mRevealPath = new Path();
    RectF pathRect = new RectF();

    boolean mClipOutlines = true;

    float[] mRadius = new float[8];

    public RoundedFrameLayout(Context context) {
        super(context);
    }

    public RoundedFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeFromAttributes(context, attrs);
    }

    public RoundedFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeFromAttributes(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RoundedFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeFromAttributes(context, attrs);
    }


    private void initializeFromAttributes(Context context, AttributeSet attrs) {
        for (int i = 0; i < mRadius.length; i++) {
            mRadius[i] = 30;
        }
    }

    public void setClipOutlines(boolean clip) {
        mClipOutlines = clip;
    }

    public void setTopLeftCornerRadius(float radius) {
        mRadius[0] = mRadius[1] = radius;
        invalidate();
    }

    public void setTopRightCornerRadius(float radius) {
        mRadius[2] = mRadius[3] = radius;
        invalidate();
    }

    public void setBottomRightCornerRadius(float radius) {
        mRadius[4] = mRadius[5] = radius;
        invalidate();
    }

    public void setBottomLeftCornerRadius(float radius) {
        mRadius[6] = mRadius[7] = radius;
        invalidate();
    }

    public float[] getCornerRadius() {
        return mRadius;
    }


    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        if (!mClipOutlines)
            return super.drawChild(canvas, child, drawingTime);

        final int state = canvas.save();

        pathRect.left = 200;
        pathRect.right = canvas.getWidth() - 200;
        pathRect.top = 200;
        pathRect.bottom = canvas.getHeight() - 200;
        mRevealPath.reset();
        mRevealPath.addRoundRect(pathRect, mRadius, Path.Direction.CW);

        //canvas.clipPath(mRevealPath);
        canvas.clipRect(pathRect);

        boolean isInvalided = super.drawChild(canvas, child, drawingTime);

        canvas.restoreToCount(state);

        return isInvalided;
    }
}
