package com.jun.viewdemo.widge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.jun.viewdemo.R;
import com.jun.viewdemo.utils.Tools;

public class AvatarView extends View {

    private static final float WIDTH = Tools.dp2px(300);
    private static final float PADDING = Tools.dp2px(50);
    private static final float EDGE_WIDTH = Tools.dp2px(10);
    private Paint paint;
    private Xfermode xfermode;
    private Bitmap bitmap;
    private RectF savedArea;

    public AvatarView(Context context) {
        super(context);
        init();
    }

    public AvatarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AvatarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);//混合模式  保持覆盖目标象素的源相素，丢弃剩余的源相素和目标像素
        savedArea = new RectF();
        bitmap = getAvatar((int) WIDTH);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        savedArea.set(PADDING, PADDING, PADDING + WIDTH, PADDING + WIDTH);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //最外层大黑圆 半径大于内层黑圆，所以有边框
        canvas.drawOval(PADDING, PADDING, PADDING + WIDTH, PADDING + WIDTH, paint);
        //图层可能有多个，将中间图层layer推入栈  并确定边界 目标是不让蒙版成为无限大的最外层背景
        int saved = canvas.saveLayer(savedArea, paint);
        //内层黑圆，半径小于外层黑圆，所以有边框  这个小黑圆会切绘出来的bitmap,切成它的大小
        canvas.drawOval(PADDING + EDGE_WIDTH, PADDING + EDGE_WIDTH, PADDING + WIDTH - EDGE_WIDTH, PADDING + WIDTH - EDGE_WIDTH, paint);
        paint.setXfermode(xfermode);
        canvas.drawBitmap(bitmap, PADDING, PADDING, paint);
        paint.setXfermode(null);
        //将中间图层推出栈，使其绘制在画布上
        canvas.restoreToCount(saved);

    }

    private Bitmap getAvatar(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.avatar_test, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;//inDesity 设置位图的像素密度，即每英寸有多少个像素
        options.inTargetDensity = width;//inTargetDensity 设置绘制位图的屏幕密度,与inScale和inDesity一起使用,来对位图进行放缩
        return BitmapFactory.decodeResource(getResources(), R.drawable.avatar_test, options);
    }
}
