package com.jun.viewdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.jun.viewdemo.activity.AvatarActivity;
import com.jun.viewdemo.activity.DashBoardActivity;
import com.jun.viewdemo.activity.PieChartActivity;
import com.jun.viewdemo.activity.RingActivity;
import com.jun.viewdemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.btnDashBoard.setOnClickListener(this);
        binding.btnPieChart.setOnClickListener(this);
        binding.btnAvatar.setOnClickListener(this);
        binding.btnRing.setOnClickListener(this);

        Drawable.
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_dash_board:
                moveTo(DashBoardActivity.class);
                break;
            case R.id.btn_pie_chart:
                moveTo(PieChartActivity.class);
                break;
            case R.id.btn_avatar:
                moveTo(AvatarActivity.class);
                break;
            case R.id.btn_ring:
                moveTo(RingActivity.class);
                break;
        }

    }

    private<T> void moveTo(Class<T> clazz) {
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
    }
}