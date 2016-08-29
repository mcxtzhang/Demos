package mcxtzhang.itemdecorationdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 介绍：右侧导航栏view
 * 作者：Zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间：2016/6/6.
 */
public class SideBar extends View {

    public static String[] INDEX_STRING = {"#", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};//现在的index # 在最前面

    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    private List<String> indexStrings;
    private int choose = -1;
    private Paint paint = new Paint();
    private TextView mTextDialog;

    public SideBar(Context context) {
        this(context, null);
    }

    public SideBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setBackgroundColor(Color.TRANSPARENT);
        indexStrings = Arrays.asList(INDEX_STRING);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight() - getPaddingTop() - getPaddingBottom();
        int width = getWidth() - getPaddingLeft() - getPaddingRight();
        int singleHeight = height / indexStrings.size();

        for (int i = 0; i < indexStrings.size(); i++) {
            paint.setColor(Color.BLACK);
            //paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics()));
            if (i == choose) {
                paint.setColor(Color.parseColor("#3399ff"));
                paint.setFakeBoldText(true);
            }

            float xPos = width / 2 - paint.measureText(indexStrings.get(i)) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(indexStrings.get(i), xPos, yPos, paint);
            paint.reset();
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();// 点击y坐标
        final int oldChoose = choose;//上次点击的个数
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) (y / getHeight() * indexStrings.size());// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.

        switch (action) {
            case MotionEvent.ACTION_UP:
                setBackgroundColor(Color.TRANSPARENT);
                choose = -1;
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.GONE);
                }
                break;
            case MotionEvent.ACTION_DOWN:
                setBackgroundColor(Color.parseColor("#39000000"));
            default:
                //上次点击的个数和这次的不一样
                if (oldChoose != c) {
                    if (c >= 0 && c < indexStrings.size()) {
                        if (listener != null) {
                            listener.onTouchingLetterChanged(indexStrings.get(c));
                        }
                        if (mTextDialog != null) {
                            mTextDialog.setText(indexStrings.get(c));
                            mTextDialog.setVisibility(View.VISIBLE);
                        }
                        choose = c;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    /**
     * 导航栏数据设置方法
     * 若不设置，则使用默认的 INDEX_STRING
     *
     * @param indexStrings
     */
    public void setIndexText(ArrayList<String> indexStrings) {
        this.indexStrings = indexStrings;
        invalidate();
    }

    /**
     * hintTextView设置方法
     *
     * @param mTextDialog
     */
    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }

    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public interface OnTouchingLetterChangedListener {
        /**
         * 将点中的index(导航栏数据)返回
         *
         * @param s
         */
        void onTouchingLetterChanged(String s);
    }

}