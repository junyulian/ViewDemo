package com.jun.viewdemo.widge;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class MapBubbleDrawable extends Drawable {

    private RectF rect;
    private float mArrowWidth = 20;
    private float mArrowHeight = 20;
    private float mAngle = 28;
    private float mArrowPosition = 50;
    private int color;

    private Path mPath = new Path();
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


    public MapBubbleDrawable(RectF rect, int mArrowWidth, int mArrowHeight, int mAngle,int color){
        this.rect = rect;
        this.mArrowWidth = (float)mArrowWidth;
        this.mArrowHeight = (float)mArrowHeight;
        this.mAngle = (float)mAngle;
        this.color = color;
    }

    /**
     * 设置颜色
     * @param color
     */
    public void setColor(int color){
        this.color = color;
    }

    @Override
    public void draw(Canvas canvas) {
        mPaint.setColor(color);
        setUpBottomPath(mPath);
        canvas.drawPath(mPath, mPaint);
    }


    private void setUpBottomPath(Path path) {
        mArrowPosition = (rect.right - rect.left)/2 - mArrowWidth / 2;
        path.moveTo(rect.left + mAngle, rect.top);
        path.lineTo(rect.width() - mAngle, rect.top);
        path.arcTo(new RectF(rect.right - mAngle,
                rect.top, rect.right, mAngle + rect.top), 270, 90);

        path.lineTo(rect.right, rect.bottom - mArrowHeight - mAngle);
        path.arcTo(new RectF(rect.right - mAngle, rect.bottom - mAngle - mArrowHeight,
                rect.right, rect.bottom - mArrowHeight), 0, 90);

        path.lineTo(rect.left + mArrowWidth + mArrowPosition, rect.bottom - mArrowHeight);
        path.lineTo(rect.left + mArrowPosition + mArrowWidth / 2, rect.bottom);
        path.lineTo(rect.left + mArrowPosition, rect.bottom - mArrowHeight);
        path.lineTo(rect.left + Math.min(mAngle, mArrowPosition), rect.bottom - mArrowHeight);

        path.arcTo(new RectF(rect.left, rect.bottom - mAngle - mArrowHeight,
                mAngle + rect.left, rect.bottom - mArrowHeight), 90, 90);
        path.lineTo(rect.left, rect.top + mAngle);
        path.arcTo(new RectF(rect.left, rect.top, mAngle
                + rect.left, mAngle + rect.top), 180, 90);
        path.close();
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }



}
