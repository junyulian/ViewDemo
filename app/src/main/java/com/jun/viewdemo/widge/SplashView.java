package com.jun.viewdemo.widge;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.Nullable;

import com.jun.viewdemo.utils.Tools;

public class SplashView extends View {

    private int mBackgroundColor = Color.WHITE;

    private int START_ANGEL = 30;
    private int[] colors = new int[]{Color.parseColor("#00C6FF"),Color.parseColor("#00C6FF"),
        Color.parseColor("#FF3892"),Color.parseColor("#FF9600"),Color.parseColor("#02D1AC"),
        Color.parseColor("#FFD200")};

    private int mCircleRadius = Tools.dp2px(15);//6个小球的半径
    private int mRotateRadius = Tools.dp2px(50);//旋转大圆的半径
    private int DISTANCE = mRotateRadius;//当前大圆的半径
    private float mCurrentHoleRadius = 0F;//扩散圆的半径

    private ValueAnimator valueAnimator;
    private Paint mPaint;
    private Paint mHolePaint;//扩散圆的画笔
    private int cx;
    private int cy;
    private SplashState mState;
    private float mDistance;


    public SplashView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


        mHolePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHolePaint.setStyle(Paint.Style.STROKE);
        mHolePaint.setColor(mBackgroundColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        cx = w/2;
        cy = h/2;
        mDistance = (float)(Math.hypot(w,h)/2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(mState == null){
            mState = new RotateState();
        }
        mState.drawState(canvas);

    }



    //旋转
    private class RotateState implements SplashState{

        private RotateState(){
            Log.e("--","hahahahahahah----");//0~2*Math.PI
            valueAnimator = ValueAnimator.ofFloat(0,360);
            valueAnimator.setRepeatCount(3);
            valueAnimator.setDuration(1000);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    START_ANGEL = (int)(float)animation.getAnimatedValue();
                    invalidate();
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mState = new MerginState();
                }
            });
            valueAnimator.start();
        }

        @Override
        public void drawState(Canvas canvas) {
            //绘制背景
            drawBackground(canvas);
            //绘制6个小球
            drawCircles(canvas);
        }
    }
    //扩散聚合
    private class MerginState implements SplashState{

        private MerginState(){
            valueAnimator = ValueAnimator.ofFloat(mBackgroundColor,mRotateRadius);
            valueAnimator.setDuration(3000);
            valueAnimator.setInterpolator(new OvershootInterpolator(10f));
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    DISTANCE = (int)(float)animation.getAnimatedValue();
                    invalidate();
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mState = new ExpandState();
                }
            });
            valueAnimator.reverse();
        }

        @Override
        public void drawState(Canvas canvas) {
            drawBackground(canvas);
            drawCircles(canvas);
        }
    }
    //水波纹
    private class ExpandState implements SplashState{

        public ExpandState(){
            valueAnimator = ValueAnimator.ofFloat(mCircleRadius,mDistance);
            valueAnimator.setDuration(10000);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCurrentHoleRadius = (float)animation.getAnimatedValue();
                    invalidate();
                }
            });
            valueAnimator.start();
        }


        @Override
        public void drawState(Canvas canvas) {
            drawBackground(canvas);
        }
    }


    private void drawBackground(Canvas canvas) {
        if(mCurrentHoleRadius>0){
            //绘制空心圆
            float strokeWidth = mDistance - mCurrentHoleRadius;

            float radius = strokeWidth/2 + mCurrentHoleRadius;
            Log.e("----","mCurrentHoleRadius:"+mCurrentHoleRadius+"--strokeWidth:"+strokeWidth+"--radiu:"+radius);
            mHolePaint.setStrokeWidth(strokeWidth);
            canvas.drawCircle(cx,cy,radius,mHolePaint);
        }else{
            canvas.drawColor(mBackgroundColor);
        }

    }

    private void drawCircles(Canvas canvas) {
        for(int i=0; i<6; i++){
            float c1x = (float)Math.cos(Math.toRadians(START_ANGEL+60*i))*DISTANCE + cx;
            float c1y = (float)Math.sin(Math.toRadians(START_ANGEL+60*i))*DISTANCE + cy;

            mPaint.setColor(colors[i]);
            canvas.drawCircle(c1x,c1y,mCircleRadius,mPaint);
        }
    }


    private interface SplashState{
        void drawState(Canvas canvas);
    }

}
