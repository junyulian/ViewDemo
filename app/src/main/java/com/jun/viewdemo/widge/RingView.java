package com.jun.viewdemo.widge;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.jun.viewdemo.R;
import com.jun.viewdemo.utils.Tools;

public class RingView extends View {

    private static final float RING_WIDTH = Tools.dp2px(20);
    private static final float RADIUS = Tools.dpToPx(150);
    private static final int CIRCLE_COLOR = Color.parseColor("#90A4AE");
    private static final int HIGHLIGHT_COLOR = Color.parseColor("#FF4081");
    private Paint paint;
    private Rect rect;
    private Paint.FontMetrics fontMetrics;
    private int cx;
    private float cy;

    public RingView(Context context) {
        super(context);
        init();
    }

    public RingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rect = new Rect();
        fontMetrics = new Paint.FontMetrics();

        paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"Quicksand-Regular.ttf"));
        paint.getFontMetrics(fontMetrics);
        paint.setTextAlign(Paint.Align.CENTER);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cx = getWidth()/2;
        cy = (float)getHeight()/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制环
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(CIRCLE_COLOR);
        paint.setStrokeWidth(RING_WIDTH);
        canvas.drawCircle(cx,cy,RADIUS,paint);

        //绘制进度条
        paint.setColor(HIGHLIGHT_COLOR);
        paint.setStrokeCap(Paint.Cap.ROUND);//圆头
        canvas.drawArc(cx-RADIUS,cy-RADIUS,cx+RADIUS,cy+RADIUS,-90,120,false,paint);

        //绘制文字
        paint.setTextSize(Tools.dp2px(100));
        paint.setStyle(Paint.Style.FILL);//文字需要设置FILL
        paint.setTextAlign(Paint.Align.CENTER);//坐标水平居中绘制
        float offset = (fontMetrics.ascent+fontMetrics.descent)/2;
        canvas.drawText("abab",cx,getHeight()/2-offset,paint);

    }
}
