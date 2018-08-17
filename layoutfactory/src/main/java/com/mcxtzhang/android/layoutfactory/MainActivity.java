package com.mcxtzhang.android.layoutfactory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //在基类Activity#onCreate中抢先设置LayoutFactory
//        LayoutInflaterCompat.setFactory2(LayoutInflater.from(this), new LayoutInflater.Factory2() {
//            @Override
//            public View onCreateView(String name, Context context, AttributeSet attrs) {
//                return onCreateView(null, name, context, attrs);
//            }
//
//            @Override
//            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
//                //先将构建View的行为仍交由AppCompatActivity的LayoutFactory去完成
//                AppCompatDelegate delegate = getDelegate();
//                View view = delegate.createView(parent, name, context, attrs);
//                //在EditText被inflate出来时（其实已经是“AppCompatEditText”了），设置光标颜色值
//                if (view instanceof EditText) {
//                    EditText editText = (EditText) view;
//                    try {
//                        java.lang.reflect.Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
//                        f.setAccessible(true);
//                        f.set(editText, R.drawable.cursor_color_bg_2);
//                    } catch (Exception e) {
//                    }
//                    return editText;
//                }
//                return view;
//            }
//        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout linearLayout = findViewById(R.id.root);
        linearLayout.addView(new EditText(this));

//        final View decorView = getWindow().getDecorView();
//        if (decorView instanceof ViewGroup) {
//            final ViewGroup root = (ViewGroup) decorView;
//            decorView.post(new Runnable() {
//                @Override
//                public void run() {
//                    travesalSetCursorDrawable(root);
//                }
//            });
//        }

/*
        EditText editText = findViewById(R.id.editText);
        try {
            java.lang.reflect.Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(editText, R.drawable.cursor_color_bg_2);
        } catch (Exception e) {
            e.printStackTrace();
        }
*/

    }

//    public static void travesalSetCursorDrawable(ViewGroup parent) {
//        int childCount = parent.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View child = parent.getChildAt(i);
//            if (child instanceof ViewGroup) {
//                //recursion
//                travesalSetCursorDrawable((ViewGroup) child);
//            } else {
//                if (child instanceof EditText) {
//                    EditText editText = (EditText) child;
//                    try {
//                        java.lang.reflect.Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
//                        f.setAccessible(true);
//                        f.set(editText, R.drawable.cursor_color_bg_2);
//                    } catch (Exception e) {
//                    }
//                }
//            }
//        }
//    }
}
