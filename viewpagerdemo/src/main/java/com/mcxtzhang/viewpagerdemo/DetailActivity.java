package com.mcxtzhang.viewpagerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends Activity {


    private ViewPager mVp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
/*        findViewById(R.id.activity_detail).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.layout((int)event.getX(), (int)event.getY()
                        ,(int) event.getX() + v.getWidth(), (int)event.getY() + v.getHeight());
                return true;
            }
        });*/

        mVp = (ViewPager) findViewById(R.id.vp);
        mVp.setAdapter(new PagerAdapter() {
            private List mListViews = new ArrayList();



            @Override
            public int getCount() {
                return 10;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_detail, container, false);
                container.addView(view);
                mListViews.add(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
            }
        });
    }
}
