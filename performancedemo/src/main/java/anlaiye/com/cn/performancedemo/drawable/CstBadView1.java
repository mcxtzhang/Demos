package anlaiye.com.cn.performancedemo.drawable;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
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

    int mText = 0;

    //    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic_500_2);
//        canvas.drawBitmap(bitmap, 0, 0, paint);

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Log.d(TAG, "onDraw() called with: canvas = [" + canvas + "]");
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(100);

        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pic_500_2), 0, 0, paint);
        canvas.drawText(String.valueOf(mText++), getWidth() / 2, getHeight() / 2, paint);
        invalidate();
    }
}
