package com.jun.viewdemo.widge;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.jun.viewdemo.utils.Tools;


public class DashBoardView extends View {

    private static final int ANGLE = 120;
    private static final int RADIUS = Tools.dpToPx(150);
    private static final int LENGTH = Tools.dpToPx(100);

    private Paint paint;
    private Path dashPath;
    private PathDashPathEffect effect;


    public DashBoardView(Context context) {
        super(context);
        init();
    }

    public DashBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DashBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(Tools.dp2px(2));

        //表盘刻度 宽2dp,长10dp
        dashPath = new Path();
        dashPath.addRect(0,0,Tools.dp2px(2),Tools.dp2px(10),Path.Direction.CW);

        //模拟一个表盘，需要获取它的长度
        Path arcPath = new Path();
        arcPath.addArc(getWidth()/2-RADIUS,getHeight()/2-RADIUS,getWidth()/2+RADIUS,getHeight()/2+RADIUS,90+ANGLE/2,360-ANGLE);
        PathMeasure pathMeasure = new PathMeasure(arcPath,false);

        //shape Path画的图形  advance 间隔   phase 偏移    ROTATE旋转成跟路径一致的方向
        effect = new PathDashPathEffect(dashPath,(pathMeasure.getLength()-Tools.dp2px(2))/20,0,PathDashPathEffect.Style.ROTATE);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画线
        canvas.drawArc(getWidth()/2-RADIUS,getHeight()/2-RADIUS,
                getWidth()/2+RADIUS,getHeight()/2+RADIUS,
                90+ANGLE/2,360-ANGLE,false,paint);

        //画刻度
        paint.setPathEffect(effect);
        canvas.drawArc(getWidth()/2-RADIUS,getHeight()/2-RADIUS,
                getWidth()/2+RADIUS,getHeight()/2+RADIUS,
                90+ANGLE/2,360-ANGLE,false,paint);
        paint.setPathEffect(null);


        paint.setColor(Color.BLACK);
        canvas.drawLine(getWidth()/2,getHeight()/2,
                (float) Math.cos(Math.toRadians(getAngleFromMark(5))) * LENGTH + getWidth() / 2,
                (float) Math.sin(Math.toRadians(getAngleFromMark(5))) * LENGTH + getHeight() / 2
                ,paint);

    }

    /**
     * 指针刻度转换指针角度
     * @param mark 刻度 0~20
     * @return
     */
    private int getAngleFromMark(int mark){
        return (int)(90+(float)ANGLE/2+(360-(float)ANGLE)/20*mark);
    }
}
