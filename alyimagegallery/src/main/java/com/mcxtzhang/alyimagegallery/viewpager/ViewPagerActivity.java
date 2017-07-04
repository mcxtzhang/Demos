package com.mcxtzhang.alyimagegallery.viewpager;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.mcxtzhang.alyimagegallery.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        //mViewPager.setOffscreenPageLimit(Integer.MAX_VALUE);
        mViewPager.setPageMargin((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 27, getResources().getDisplayMetrics()));
        findViewById(R.id.parent).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mViewPager.onTouchEvent(event);
            }
        });


        final List<View> viewList = new ArrayList<>();
        viewList.add(LayoutInflater.from(this).inflate(R.layout.uc_item_main_image_header, mViewPager, false));
        viewList.add(LayoutInflater.from(this).inflate(R.layout.uc_item_main_image_header, mViewPager, false));
        viewList.add(LayoutInflater.from(this).inflate(R.layout.uc_item_main_image_header, mViewPager, false));
        viewList.add(LayoutInflater.from(this).inflate(R.layout.uc_item_main_image_header, mViewPager, false));
        viewList.add(LayoutInflater.from(this).inflate(R.layout.uc_item_main_image_header, mViewPager, false));
        viewList.add(LayoutInflater.from(this).inflate(R.layout.uc_item_main_image_header, mViewPager, false));

        PagerAdapter adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                position %= viewList.size();
                if (position < 0) {
                    position += viewList.size();
                }

                View view = viewList.get(position);

                ViewParent vp = view.getParent();
                if (vp != null) {
                    ViewGroup vg = (ViewGroup) vp;
                    vg.removeView(view);
                }
                container.addView(view);
                return view;
            }
        };
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(Integer.MAX_VALUE / 2 + 2);

        //mViewPager.setPageTransformer(false,new DepthPageTransformer());
        mViewPager.setPageTransformer(false,new ZoomOutPageTransformer());
    }
}
