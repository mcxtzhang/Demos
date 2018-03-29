package com.mcxtzhang.github;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.example.ZRouter;

@ZRouter(path = SparseArrayOrderTestActivity.TAG)
public class SparseArrayOrderTestActivity extends AppCompatActivity {

    public static final String TAG = "zxt/SparseArray";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sparce_array_order_test);
        findViewById(R.id.btnChangeAlpha).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable background = view
                        .getBackground()
                        .mutate();
                /*Log.d("TAG", "onClick() called with: background = [" + background + "]");
                if (background instanceof BitmapDrawable) {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) background;
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    Log.d("TAG", "onClick() called with: bitmap = [" + bitmap + "]");
                }*/
                background.setAlpha(20);


                //useless
                //background.setBounds(new Rect(1,2,3,4));

                findViewById(R.id.activity_sparce_array_order_test).invalidate();
            }
        });

/*        findViewById(R.id.btnChangeAlpha).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCategoryPopupMenu(view);
            }
        });*/


    }

    public void run(View view) {
        SparseArray<Integer> sparseArray = new SparseArray<>();
        sparseArray.put(1, 1);
        sparseArray.put(2, 2);
        sparseArray.put(4, 4);
        sparseArray.put(6, 6);
        sparseArray.put(3, 3);


        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            int key = sparseArray.keyAt(i);
            Log.d(TAG, "key = [" + key + "]" + ", value:" + sparseArray.get(key));
        }
    }

    public void runInt(View view) {
        SparseIntArray sparseArray = new SparseIntArray();
        sparseArray.put(1, 1);
        sparseArray.put(2, 2);
        sparseArray.put(4, 4);
        sparseArray.put(6, 6);
        sparseArray.put(3, 3);


        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            int key = sparseArray.keyAt(i);
            Log.d(TAG, "key = [" + key + "]" + ", value:" + sparseArray.get(key));
        }
    }


    private PopupWindow mPopCategory;

    private void showCategoryPopupMenu(View anchorView) {
        View bgView = View.inflate(this, R.layout.activity_touch, null);
        bgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hidePopupWindow();
            }
        });
/*        new VGUtil((ViewGroup) bgView.findViewById(R.id.fvg), new SingleAdapter<BfMainCategoryBean>(mActivity, mCateDatas, R.layout.breakfast_item_category_tab) {
            @Override
            public void onBindViewHolder(ViewGroup viewGroup, com.mcxtzhang.commonadapter.viewgroup.adapter.cache.ViewHolder viewHolder, BfMainCategoryBean bfMainCategoryBean, int i) {
                viewHolder.setText(R.id.tv_category_tab, bfMainCategoryBean.getName());
                viewHolder.getView(R.id.tv_category_tab).setSelected(bfMainCategoryBean.isSelect());
            }
        }, new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup viewGroup, View view, int i) {
                hidePopupWindow();
                changeCategoryUI(i);
                scrollFromCategoryClick(i);
            }
        }).bind();*/
        mPopCategory = new PopupWindow(bgView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopCategory.setTouchable(true);
        mPopCategory.setOutsideTouchable(true);
        if (!mPopCategory.isShowing()) {
            if (android.os.Build.VERSION.SDK_INT >= 24) {
                int[] a = new int[2];
                anchorView.getLocationInWindow(a);
                mPopCategory.showAtLocation(getWindow().getDecorView(), Gravity.NO_GRAVITY, 0, a[1] + anchorView.getHeight());
            } else {
                mPopCategory.showAsDropDown(anchorView);
            }

        }
    }
}
