package anlaiye.com.cn.performancedemo.drawable;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import anlaiye.com.cn.performancedemo.R;


public class CstBadView1 extends View {
    private static final String TAG = "CstBadView1";

    public CstBadView1(Context context) {
        super(context);
    }

    public CstBadView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CstBadView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure() called with: widthMeasureSpec = [" + widthMeasureSpec + "], heightMeasureSpec = [" + heightMeasureSpec + "]");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout() called with: changed = [" + changed + "], left = [" + left + "], top = [" + top + "], right = [" + right + "], bottom = [" + bottom + "]");
    }

    int mText = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw() called with: canvas = [" + canvas + "]");
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);

        canvas.drawText(String.valueOf(mText++), getWidth() / 2, getHeight() / 2, paint);
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pic_500_2), 0, 0, paint);
        invalidate();
    }
}
