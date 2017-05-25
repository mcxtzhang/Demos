package com.mcxtzhang.github.popup;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.PopupWindow;

public class SFPopupWindow extends PopupWindow {

    public SFPopupWindow(Context context) {
        super(context);
    }

    public SFPopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SFPopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SFPopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public SFPopupWindow() {
    }

    public SFPopupWindow(View contentView) {
        super(contentView);
    }

    public SFPopupWindow(int width, int height) {
        super(width, height);
    }

    public SFPopupWindow(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    public SFPopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT == 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);  
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;  
            setHeight(h);  
        }  
        super.showAsDropDown(anchor);  
    }  
}  