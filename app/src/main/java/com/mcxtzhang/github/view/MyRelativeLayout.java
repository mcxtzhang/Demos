package com.mcxtzhang.github.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.mcxtzhang.github.R;

/**
 * Created by mcxtzhang on 2017/6/2.
 */

public class MyRelativeLayout extends RelativeLayout {
    public MyRelativeLayout(Context context) {
        super(context);
        View inflate;
        inflate = LayoutInflater.from(getContext()).inflate(R.layout.list_item, null);
        //inflate.setBackgroundColor(Color.YELLOW);
        addView(inflate);
        setBackgroundColor(Color.GREEN);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(getContext()).inflate(R.layout.list_item, this);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(getContext()).inflate(R.layout.list_item, this);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500);
    }
}
