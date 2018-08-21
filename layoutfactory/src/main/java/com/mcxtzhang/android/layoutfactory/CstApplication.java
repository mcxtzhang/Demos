package com.mcxtzhang.android.layoutfactory;

import android.app.Application;

/**
 * Created by zhangxutong on 2018/8/17.
 */

public class CstApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

/*
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(final Activity activity, Bundle savedInstanceState) {
                try {
                    setFactory(activity);
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        LayoutInflater layoutInflater = LayoutInflater.from(activity);

                        Field mFactorySet = LayoutInflater.class.getDeclaredField("mFactorySet");
                        mFactorySet.setAccessible(true);
                        mFactorySet.set(layoutInflater, false);


                        Field mFactory = LayoutInflater.class.getDeclaredField("mFactory");
                        mFactory.setAccessible(true);
                        mFactory.set(layoutInflater, null);
                        setFactory(activity);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }

            }

            private void setFactory(final Activity activity) {
                LayoutInflater layoutInflater = LayoutInflater.from(activity);
                LayoutInflaterCompat.setFactory2(layoutInflater, new LayoutInflater.Factory2() {
                    @Override
                    public View onCreateView(String name, Context context, AttributeSet attrs) {
                        return onCreateView(null, name, context, attrs);
                    }

                    @Override
                    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                        //先将构建View的行为仍交由AppCompatActivity的LayoutFactory去完成
                        AppCompatDelegate delegate = ((AppCompatActivity) activity).getDelegate();
                        View view = delegate.createView(parent, name, context, attrs);
                        //在EditText被inflate出来时（其实已经是“AppCompatEditText”了），设置光标颜色值
                        if (view instanceof EditText) {
                            EditText editText = (EditText) view;
                            try {
                                Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
                                f.setAccessible(true);
                                f.set(editText, R.drawable.cursor_color_bg_2);
                            } catch (Exception e) {
                            }
                            return editText;
                        }
                        return view;
                    }
                });
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });*/
    }
}
