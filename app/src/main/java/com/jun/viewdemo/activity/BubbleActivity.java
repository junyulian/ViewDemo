package com.jun.viewdemo.activity;

import android.graphics.Color;

import com.jun.viewdemo.R;
import com.jun.viewdemo.widge.MapBubbleTextView;

public class BubbleActivity extends BaseActivity {
    @Override
    public int getViewId() {
        return R.layout.activity_bubble;
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapBubbleTextView tv_bubble = findViewById(R.id.tv_bubble);
        tv_bubble.setColor(Color.parseColor("#F4683D"));
        tv_bubble.setText("与君相交，江水为竟");
    }
}
