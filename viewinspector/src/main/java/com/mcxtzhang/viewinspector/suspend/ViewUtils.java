package com.mcxtzhang.viewinspector.suspend;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * Created in android-nova-lib
 * Changed by yuande.liu on 16/8/8.
 * <p/>
 * Merge some methods from android-nova-feed android-nova-travel
 * In place of the same name class in agentsdk lib titans and feed.
 */
public class ViewUtils {

    private static int screenWidthPixels;
    private static int screenHeightPixels;

    /**
     * 显示View
     *
     * @param v
     */
    public static void showView(View v) {
        if (v == null) {
            return;
        }
        v.setVisibility(View.VISIBLE);
    }

    /**
     * 显示View
     *
     * @param v
     * @param isShow
     */
    public static void showView(View v, boolean isShow) {
        showView(v, isShow, false);
    }

    /**
     * 显示View
     *
     * @param v
     * @param isShow
     * @param isGone
     */
    public static void showView(View v, boolean isShow, boolean isGone) {
        if (isShow) {
            showView(v);
        } else {
            hideView(v, isGone);
        }
    }

    /**
     * 隐藏View
     *
     * @param v
     */
    public static void hideView(View v) {
        hideView(v, false);
    }

    /**
     * 隐藏View
     *
     * @param v
     * @param isGone
     */
    public static void hideView(View v, boolean isGone) {
        if (v == null) {
            return;
        }
        if (isGone) {
            v.setVisibility(View.GONE);
        } else {
            v.setVisibility(View.INVISIBLE);
        }
    }


    /**
     * 设置View的可见性
     *
     * @param view
     * @param visibility
     */
    public static void updateViewVisibility(View view, int visibility) {
        if (view == null)
            return;

        view.setVisibility(visibility);
    }

    /**
     * 设置View的监听为空
     *
     * @param v
     */
    public static void readOnlyView(EditText v) {
        if (v == null) {
            return;
        }
        v.setKeyListener(null);
    }

    /**
     * enable view
     *
     * @param v
     */
    public static void enableView(View v) {
        if (v == null) {
            return;
        }
        v.setEnabled(true);
    }

    /**
     * disable view
     *
     * @param v
     */
    public static void disableView(View v) {
        if (v == null) {
            return;
        }
        v.setEnabled(false);
    }

    /**
     * 判断view是否可视
     *
     * @param v
     * @return
     */
    public static boolean isShow(View v) {
        if (v == null) {
            return false;
        }
        return v.getVisibility() == View.VISIBLE;
    }

    /**
     * 判断所给的点是否在View内部
     * Determines if given points are inside view
     *
     * @param x    - x coordinate of point
     * @param y    - y coordinate of point
     * @param view - view object to compare
     * @return true if the points are within view bounds, false otherwise
     */
    public static boolean isPointInsideView(float x, float y, View view) {
        int location[] = new int[2];
        view.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];

        // point is inside view bounds
        if ((x > viewX && x < (viewX + view.getWidth()))
                && (y > viewY && y < (viewY + view.getHeight()))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * dip转px
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        if (context == null) {
            return (int) dipValue;
        }
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        if (displayMetrics != null) {
            final float scale = displayMetrics.density;
            return (int) (dipValue * scale + 0.5f);
        } else {
            return (int) (dipValue * 3 + 0.5f) /* 使用主流手机的 density */;
        }
    }

    /**
     * px转dip
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        if (context == null) {
            return (int) pxValue;
        }
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        if (displayMetrics != null) {
            final float scale = displayMetrics.density;
            return (int) (pxValue / scale + 0.5f);
        } else {
            return (int) (pxValue / 3 + 0.5f) /* 使用主流手机的 density */;
        }
    }

    /**
     * 测量一个TextView
     *
     * @param tv
     * @return
     */
    public static int measureTextView(TextView tv) {
        if (tv == null) {
            return -1;
        }
        Paint p = tv.getPaint();
        return (int) p.measureText(tv.getText().toString());
    }

    /**
     * sp转px
     *
     * @param context
     * @param sp
     * @return
     */
    public static float sp2px(Context context, float sp) {
        if (context == null) {
            return sp;
        }
        Resources r = context.getResources();
        float size = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                r.getDisplayMetrics());
        return size;
    }

    /**
     * 得到屏幕像素宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidthPixels(Context context) {

        if (context == null) {
            //Log.e("Can't get screen size while the activity is null!");
            return 0;
        }

        if (screenWidthPixels > 0) {
            return screenWidthPixels;
        }
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        Display display = manager.getDefaultDisplay();
        if (display != null) {
            display.getMetrics(dm);
            screenWidthPixels = dm.widthPixels;
        }
        return screenWidthPixels;
    }

    /**
     * 得到屏幕像素高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeightPixels(Context context) {
        if (context == null) {
            //Log.e("Can't get screen size while the activity is null!");
            return 0;
        }

        if (screenHeightPixels > 0) {
            return screenHeightPixels;
        }
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        Display display = manager.getDefaultDisplay();
        if (display != null) {
            display.getMetrics(dm);
            screenHeightPixels = dm.heightPixels;
        }
        return screenHeightPixels;
    }

    /**
     * 获取屏幕设备整体的像素尺寸
     * 屏幕设备底部有虚拟按键时，屏幕设备整体高度包括NavigationBar的高度
     *
     * @param context
     * @return point.x 屏幕设备整体宽度， point.y 屏幕设备整体高度
     */
    public static Point getScreenDevicePixels(@NonNull Context context) {
        Point point = new Point();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager == null ? null : manager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            point.x = dm.widthPixels;
            point.y = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return point;
    }

    /**
     * TextView的可见性依赖于其文本内容的有无
     *
     * @param view
     * @param content
     */
    public static void setVisibilityAndContent(TextView view, String content) {
        if (!TextUtils.isEmpty(content)) {
            view.setText(content);
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * 得到设置文字后的TextView的宽度
     *
     * @param textView
     * @param text
     * @param textSize
     * @return
     */
    public static int getTextViewWidth(TextView textView, String text, int textSize) {
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredWidth();
    }

    /**
     * 得到设置文字后的TextView的高度
     *
     * @param textView
     * @param text
     * @return
     */
    public static int getTextViewWidth(TextView textView, String text) {
        textView.setText(text);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredWidth();
    }

    /**
     * 得到View的高度
     *
     * @param view
     * @return
     */
    public static int getViewHeight(View view) {
        if (view == null) {
            return 0;
        }
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec);
        return view.getMeasuredHeight();
    }

    /**
     * 得到View的宽度
     *
     * @param view
     * @return
     */
    public static int getViewWidth(View view) {
        if (view == null) {
            return 0;
        }
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec);
        return view.getMeasuredWidth();
    }

    /**
     * 得到View水平方向上的边距总和
     *
     * @param v
     * @return
     */
    public static int getHorizontalMarginSum(View v) {
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        return mlp.leftMargin + mlp.rightMargin;
    }


    /**
     * 得到颜色值
     * Take out from android-nova-feed
     *
     * @param rid
     * @return
     */
    public static String getResColor(int rid) {
        return String.format("#%06X", (0xFFFFFF & rid));
    }

    /**
     * 重用view，将ViewGroup中的子View全部移出
     * <p>
     * Take out from android-nova-travel TravelUtils.java
     *
     * @param viewGroup
     * @return
     */
    public static List<View> recycleView(ViewGroup viewGroup) {
        if (null == viewGroup) {
            return null;
        }

        int childCount = viewGroup.getChildCount();

        if (childCount > 0) {
            List<View> convertViewList = new ArrayList<>();
            for (int i = 0; i < childCount; i++) {
                View childView = viewGroup.getChildAt(0);
                viewGroup.removeView(childView);

                convertViewList.add(childView);
            }

            return convertViewList;
        }

        return null;
    }

    /**
     * 将View转成Bitmap
     *
     * @param view
     * @return
     */
    public static Bitmap drawBitmapFromView(View view) {
        if (view == null) {
            return null;
        }

        view.setDrawingCacheEnabled(true);

        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.buildDrawingCache(true);
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return bmp;
    }

/******************* Following methods are merged from HotelViewUtils.java in android-nova-hotel **********************/

    /**
     * 获得 child 距离 ancestor 的上边距的距离
     *
     * @param ancestor
     * @param child
     * @return
     */
    public static int getDescendantRelativeTop(View ancestor, View child) {
        if (child == null) {
            return -1;
        }

        int top = 0;
        ViewParent parent = child.getParent();
        while (parent instanceof ViewGroup) {
            top += child.getTop();
            if (parent == ancestor) {
                return top;
            } else {
                child = (View) parent;
                parent = child.getParent();
            }
        }

        return -1;
    }

    /**
     * 设置图片透明度
     *
     * @param imageView 图片
     * @param alpha     透明度（0.0~1.0）
     */
    public static void setImageAlpha(ImageView imageView, float alpha) {
        setImageAlpha(imageView, (int) (alpha * 255));
    }

    /**
     * 设置图片透明度
     *
     * @param imageView 图片
     * @param alpha     透明度（0~255）
     */
    public static void setImageAlpha(ImageView imageView, int alpha) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            imageView.setImageAlpha(alpha);
        } else {
            imageView.setAlpha(alpha);
        }
    }

    /**
     * 将两种颜色按影响因子 factor 进行混合，返回混合后的 argb 值
     *
     * @param scrColor
     * @param dstColor
     * @param factor
     * @return
     */
    public static int blendColor(int scrColor, int dstColor, float factor) {
        int scrAlpha = Color.alpha(scrColor);
        int scrRed = Color.red(scrColor);
        int scrGreen = Color.green(scrColor);
        int scrBlue = Color.blue(scrColor);

        int dstAlpha = Color.alpha(dstColor);
        int dstRed = Color.red(dstColor);
        int dstGreen = Color.green(dstColor);
        int dstBlue = Color.blue(dstColor);

        int outputAlpha = (int) (scrAlpha * (1 - factor) + dstAlpha * factor) & 0xFF;
        int outputRed = (int) (scrRed * (1 - factor) + dstRed * factor) & 0xFF;
        int outputGreen = (int) (scrGreen * (1 - factor) + dstGreen * factor) & 0xFF;
        int outputBlue = (int) (scrBlue * (1 - factor) + dstBlue * factor) & 0xFF;

        return Color.argb(outputAlpha, outputRed, outputGreen, outputBlue);
    }
}
