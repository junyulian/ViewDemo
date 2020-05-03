package com.jun.viewdemo.widge;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class MapBubbleTextView extends AppCompatTextView {

    private int arrowWidth = dip2px(10);
    private int arrowHeight = dip2px(10);
    private int angle = dip2px(28);
    private int color = Color.parseColor("#4286FF");
    private MapBubbleDrawable mapBubbleDrawable;

    public MapBubbleTextView(Context context) {
        super(context);
        setUpBottomPadding();
    }

    public MapBubbleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUpBottomPadding();
    }

    public MapBubbleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUpBottomPadding();
    }

    private void setUpBottomPadding() {
        int bottom = getPaddingBottom();
        int left = getPaddingLeft();
        int right = getPaddingRight();
        int top = getPaddingTop();
        bottom += arrowHeight;
        setPadding(left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0) {
            setUp(w, h);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


    private void setUp(int width, int height) {
        setUp(0, width, 0, height);
    }

    private void setUp(int left, int right, int top, int bottom) {
        RectF rectF = new RectF(left, top, right, bottom);
        mapBubbleDrawable = new MapBubbleDrawable(rectF,arrowWidth,arrowHeight,angle,color);
    }

    public void setColor(int color){
        this.color = color;
        if(mapBubbleDrawable != null){
            mapBubbleDrawable.setColor(color);
            invalidate();
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mapBubbleDrawable != null){
            mapBubbleDrawable.draw(canvas);
        }
        super.onDraw(canvas);
    }

    public int dip2px(float dip) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f);
    }

}
