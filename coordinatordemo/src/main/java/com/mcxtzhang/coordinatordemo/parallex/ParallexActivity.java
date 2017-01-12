package com.mcxtzhang.coordinatordemo.parallex;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.mcxtzhang.coordinatordemo.R;

public class ParallexActivity extends AppCompatActivity {

    private static final String TAG = "zxt/ParallexActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallex);

        final View fab = findViewById(R.id.fab);

        findViewById(R.id.btnShow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] a = new int[2];
                int[] b = new int[2];
                v.getLocationInWindow(a);
                v.getLocationOnScreen(b);

                Log.d(TAG, "onClick() called with: a = [" + a[1] + "] b" + b[1] + "]");
                int[] c = new int[2];
                int[] d = new int[2];
                fab.getLocationInWindow(c);
                fab.getLocationOnScreen(d);
                Log.d(TAG, "onClick() called with: c [" + c[1] + "] d" + d[1] + "]");


                showCategoryPopupMenu(fab);
            }
        });
    }

    private PopupWindow mPopCategory;

    private void showCategoryPopupMenu(View v) {
        if (mPopCategory == null) {
            View bgView = View.inflate(this, R.layout.item_rv, null);
            bgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hidePopupWindow();
                }
            });
            mPopCategory = new PopupWindow(bgView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            //这句话加上会使得popwindow有焦点，按back退出的时候就不会直接退出activity了
            mPopCategory.setFocusable(true);
//设置点击窗口外边窗口消失
            mPopCategory.setOutsideTouchable(true);
        }
        //mPopupBackground.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        if (!mPopCategory.isShowing()) {
            mPopCategory.showAsDropDown(v);
        }
    }

    private void hidePopupWindow() {
        if (mPopCategory != null && mPopCategory.isShowing()) {
            mPopCategory.dismiss();
        }
    }
}
