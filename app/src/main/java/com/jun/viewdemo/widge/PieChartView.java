package com.jun.viewdemo.widge;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.jun.viewdemo.utils.Tools;

public class PieChartView extends View {

    private static final int RADIUS = Tools.dpToPx(150);
    private static final int LENGTH = Tools.dpToPx(30);
    private static final int TRANS_INDEX = 0;

    private int cx;
    private int cy;
    private Paint paint;
    private int[] angels;
    private int[] colors;
    private RectF rectF;

    public PieChartView(Context context) {
        super(context);
        init();
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        angels = new int[]{60,100,120,80};
        colors = new int[]{Color.RED,Color.BLACK,Color.GREEN,Color.YELLOW};

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cx = w/2;
        cy = h/2;
        rectF = new RectF(cx-RADIUS,cy-RADIUS,cx+RADIUS,cy+RADIUS);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int currentAngel = 0;
        for(int i=0; i<angels.length; i++){
            paint.setColor(colors[i]);
            canvas.save();
            if(i == TRANS_INDEX){
                canvas.translate((float) Math.cos(Math.toRadians(currentAngel+angels[i]/2))*LENGTH,(float) Math.sin(Math.toRadians(currentAngel+angels[i]/2))*LENGTH);
            }
            canvas.drawArc(rectF,currentAngel,angels[i],true,paint);
            canvas.restore();
            currentAngel += angels[i];
        }
    }
}
