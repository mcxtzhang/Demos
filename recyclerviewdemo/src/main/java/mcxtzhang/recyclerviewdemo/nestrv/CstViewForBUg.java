package mcxtzhang.recyclerviewdemo.nestrv;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/11/8.
 */

public class CstViewForBug extends View {
    public CstViewForBug(Context context) {
        super(context);
    }

    public CstViewForBug(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CstViewForBug(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    Paint mPaint = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("TAG", "onDraw() called with: canvas = [" + canvas + "]");
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(30);
        canvas.drawText("完美", 50, 50, mPaint);
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        Log.d("TAG", "onVisibilityChanged() called with: changedView = [" + changedView + "], visibility = [" + visibility + "]");
    }
}
