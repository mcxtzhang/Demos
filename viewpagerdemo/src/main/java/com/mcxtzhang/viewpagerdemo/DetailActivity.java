package com.mcxtzhang.viewpagerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

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

        List<View> views = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_detail, mVp, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    overridePendingTransition(0, R.anim.fade_out);
                }
            });
            view.findViewById(R.id.iv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(DetailActivity.this, "加入购物车", Toast.LENGTH_SHORT).show();
                }
            });
            views.add(view);
        }


        mVp.setAdapter(new DetailAdapter(views));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.fade_out);
    }
}
