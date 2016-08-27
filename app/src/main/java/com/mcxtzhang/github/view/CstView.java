package com.mcxtzhang.github.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhangxutong .
 * Date: 16/08/27
 */

public class CstView extends View {
    public CstView(Context context) {
        super(context);
    }

    public CstView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CstView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
    }
}
