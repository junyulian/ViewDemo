package com.jun.viewdemo.widge;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.jun.viewdemo.Bean.Ball;
import com.jun.viewdemo.R;
import com.jun.viewdemo.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class ImageCrashView extends View {

    private static final int CRASH_RADIUS = 2;//ball的直径
    private Paint paint;
    private Bitmap bitmap;

    private ValueAnimator valueAnimator;
    private List<Ball> mBalls = new ArrayList<>();
    private Bitmap copyBitmap;


    public ImageCrashView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.tsy01);

        for(int i=0; i<bitmap.getWidth(); i++){
            for(int j=0; j<bitmap.getHeight(); j++){
                Ball ball = new Ball();
                ball.color = bitmap.getPixel(i,j);
                ball.x = i*CRASH_RADIUS + CRASH_RADIUS/2;
                ball.y = j*CRASH_RADIUS + CRASH_RADIUS/2;
                ball.r = CRASH_RADIUS/2;

                //速度
                ball.vx = (float)(Math.pow(-1,Math.ceil(Math.random()*1000))*20*Math.random());//(-20~20)
                ball.vy = Tools.rangInt(-15,35);
                //加速度
                ball.ax = 0;
                ball.ay = 0.98f;

                mBalls.add(ball);
            }
        }

        valueAnimator = ValueAnimator.ofFloat(0,1);
        valueAnimator.setRepeatCount(-1);
        valueAnimator.setDuration(5000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateBall();
                invalidate();
            }
        });
    }


    private void updateBall(){
        //更新粒子位置
        for(Ball ball: mBalls){
            ball.x += ball.vx;
            ball.y += ball.vy;

            ball.vx += ball.ax;
            ball.vy += ball.vy;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(200,200);
        for(Ball ball : mBalls){
            paint.setColor(ball.color);
            canvas.drawCircle(ball.x,ball.y,ball.r,paint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            valueAnimator.start();
        }

        return super.onTouchEvent(event);
    }
}
