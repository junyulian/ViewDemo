package com.jun.viewdemo.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class Tools {


    public static int dip2px(Context context, float dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }


    public static int dpToPx(float dp){
        return Math.round(Resources.getSystem().getDisplayMetrics().density*dp);
    }


    public static int dp2px(float dp){
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,Resources.getSystem().getDisplayMetrics());
    }


    public static int rangInt(int i, int j) {
        int max = Math.max(i, j);
        int min = Math.min(i, j) - 1;
        //在0到(max - min)范围内变化，取大于x的最小整数 再随机
        return (int) (min + Math.ceil(Math.random() * (max - min)));
    }

}
