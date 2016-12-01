package com.mcxtzhang.cstviewdemo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.mcxtzhang.cstviewdemo.R;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * CSDN：http://blog.csdn.net/zxt0601
 * 时间： 16/11/24.
 */

public class AvoidXfermodeView extends View {
    private Paint mPaint;

    public AvoidXfermodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 图片的遮罩
        Bitmap mask = BitmapFactory.decodeResource(getResources(), R.drawable.icon_delete);

        /*
         * 将绘制操作保存到新的图层（更官方的说法应该是离屏缓存）
         */
        int sc = canvas.saveLayer(0, 0, 720, 1080, null, Canvas.ALL_SAVE_FLAG);

        // 先绘制一层颜色
        canvas.drawColor(0xFF8f66DA);

        // 设置混合模式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        // 再绘制src源图
        canvas.drawBitmap(mask, 180, 200, mPaint);

        // 还原混合模式
        mPaint.setXfermode(null);

        // 还原画布
        canvas.restoreToCount(sc);
    }
}
