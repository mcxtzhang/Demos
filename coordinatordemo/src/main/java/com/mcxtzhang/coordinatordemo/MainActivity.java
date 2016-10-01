package com.mcxtzhang.coordinatordemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.mcxtzhang.zxtcommonlib.recyclerview.CommonAdapter;
import com.mcxtzhang.zxtcommonlib.recyclerview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import static com.mcxtzhang.coordinatordemo.R.id.rv;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRv = (RecyclerView) findViewById(rv);
        mRv.setLayoutManager(new LinearLayoutManager(this/*,LinearLayoutManager.HORIZONTAL,false*/));

        mRv.setAdapter(new CommonAdapter<TestBean>(this, R.layout.item_rv, initDatas()) {
            @Override
            public void convert(ViewHolder holder, TestBean testBean) {
                holder.setText(R.id.tv, testBean.getName());
            }
        });

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        lp.setBehavior(new CstAppBehavior());
/*        TextView tvHeader1 = (TextView) findViewById(R.id.tvHeader1);
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)tvHeader1.getLayoutParams();
        lp.setBehavior(new AppBarLayout.Behavior());*/
    }


    public List initDatas() {
        List datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add(new TestBean("http://imgs.ebrun.com/resources/2016_03/2016_03_25/201603259771458878793312_origin.jpg", "张"));
            datas.add(new TestBean("http://p14.go007.com/2014_11_02_05/a03541088cce31b8_1.jpg", "旭童"));
            datas.add(new TestBean("http://news.k618.cn/tech/201604/W020160407281077548026.jpg", "多种type"));
            datas.add(new TestBean("http://www.kejik.com/image/1460343965520.jpg", "多种type"));
            datas.add(new TestBean("http://cn.chinadaily.com.cn/img/attachement/jpg/site1/20160318/eca86bd77be61855f1b81c.jpg", "多种type"));
            datas.add(new TestBean("http://imgs.ebrun.com/resources/2016_04/2016_04_12/201604124411460430531500.jpg", "多种type"));
            datas.add(new TestBean("http://imgs.ebrun.com/resources/2016_04/2016_04_24/201604244971461460826484_origin.jpeg", "多种type"));
            datas.add(new TestBean("http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg", "多种type"));
        }
        return datas;
    }


    public static class CstAppBehavior extends AppBarLayout.Behavior {
        private static final String TAG = "zxt";

        @Override
        public float getScrimOpacity(CoordinatorLayout parent, AppBarLayout child) {
            return 1;
        }

        @Override
        public int getScrimColor(CoordinatorLayout parent, AppBarLayout child) {
            return Color.GREEN;
        }

        @Override
        public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes) {
            Log.d(TAG, "onStartNestedScroll() called with:, child = [" + child + "], directTargetChild = [" + directTargetChild + "], target = [" + target + "], nestedScrollAxes = [" + nestedScrollAxes + "]");
            return super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes);
        }

        @Override
        public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes) {
            Log.d(TAG, "onNestedScrollAccepted() called with: coordinatorLayout = , child = [" + child + "], directTargetChild = [" + directTargetChild + "], target = [" + target + "], nestedScrollAxes = [" + nestedScrollAxes + "]");
            super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
        }

        @Override
        public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout abl, View target) {
            Log.d(TAG, "onStopNestedScroll() called with:  abl = [" + abl + "], target = [" + target + "]");
            super.onStopNestedScroll(coordinatorLayout, abl, target);
        }

        boolean isConsumed = true;

        @Override
        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed) {
            Log.v(TAG, "onNestedPreScroll() called with:, child = [" + child + "], target = [" + target + "], dx = [" + dx + "], dy = [" + dy + "], consumed = [" + consumed + "]");
            //isConsumed = !isConsumed;
            if (isConsumed) {
                consumed[1] = dy;
            } else {

            }

            //super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        }

        @Override
        public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
            Log.v(TAG, "onNestedScroll() called with: child = [" + child + "], target = [" + target + "], dxConsumed = [" + dxConsumed + "], dyConsumed = [" + dyConsumed + "], dxUnconsumed = [" + dxUnconsumed + "], dyUnconsumed = [" + dyUnconsumed + "]");
            if (isConsumed) {
                ViewCompat.offsetTopAndBottom(child,-dyConsumed);
            } else {

            }
            super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        }

        @Override
        public boolean onNestedFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, float velocityX, float velocityY, boolean consumed) {
            return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
        }

        @Override
        public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, float velocityX, float velocityY) {
            return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
        }
    }
}
