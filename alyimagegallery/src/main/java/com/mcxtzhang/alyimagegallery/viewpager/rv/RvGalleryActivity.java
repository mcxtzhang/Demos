package com.mcxtzhang.alyimagegallery.viewpager.rv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mcxtzhang.alyimagegallery.R;
import com.mcxtzhang.commonadapter.rv.CommonAdapter;
import com.mcxtzhang.commonadapter.rv.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class RvGalleryActivity extends AppCompatActivity {
    private static final String TAG = "TAG";
    RecyclerView mRecyclerView;
    CommonAdapter mAdapter;
    LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_gallery);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new CommonAdapter<String>(this, initDatas(), R.layout.uc_item_main_image_header) {
            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                position = position % mDatas.size();
                super.onBindViewHolder(holder, position);
            }

            @Override
            public void convert(ViewHolder viewHolder, String s) {
                ImageView view = (ImageView) viewHolder.getView(R.id.image);
                Glide.with(viewHolder.itemView.getContext())
                        .load(s)
                        .into(view);
            }

            @Override
            public String getItem(int position) {
                return mDatas.get(position % mDatas.size());
            }

            @Override
            public long getItemId(int position) {
                return (position % mDatas.size());
            }

            @Override
            public int getItemCount() {
                return Integer.MAX_VALUE;
            }
        };
        mRecyclerView.setAdapter(mAdapter);


        final SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int middle = (int) (mRecyclerView.getX() + mRecyclerView.getWidth() / 2);
                for (int i = 0; i < mRecyclerView.getChildCount(); i++) {
                    View child = mRecyclerView.getChildAt(i);
                    int childMiddle = (int) (child.getX() + child.getWidth() / 2);
                    int gap = Math.abs(middle - childMiddle);
                    float fraction = gap * 1.0f / mRecyclerView.getWidth() / 2;
                    scale(child, fraction);
                }
            }

            private void scale(View child, float fraction) {
                final float MIN_SCALE = 0.65f;
                float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(fraction));
                child.setScaleX(scaleFactor);
                child.setScaleY(scaleFactor);
            }
        });

        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.scrollToPosition(80);
                snapToTargetExistingView();
            }

            void snapToTargetExistingView() {
                if (mRecyclerView == null) {
                    return;
                }
                if (mLinearLayoutManager == null) {
                    return;
                }
                View snapView = snapHelper.findSnapView(mLinearLayoutManager);
                if (snapView == null) {
                    return;
                }
                int middle = (int) (mRecyclerView.getX() + mRecyclerView.getWidth() / 2);
                int childMiddle = (int) (snapView.getX() + snapView.getWidth() / 2);
                int gap = (middle - childMiddle);
                mRecyclerView.smoothScrollBy(gap, 0);
            }
        });
    }

    public List<String> initDatas() {
        List<String> datas = new ArrayList<>();
        datas.add(new String("http://imgs.ebrun.com/resources/2016_03/2016_03_25/201603259771458878793312_origin.jpg"));
        datas.add(new String("http://p14.go007.com/2014_11_02_05/a03541088cce31b8_1.jpg"));
        datas.add(new String("http://news.k618.cn/tech/201604/W020160407281077548026.jpg"));
        datas.add(new String("http://www.kejik.com/image/1460343965520.jpg"));
        datas.add(new String("http://cn.chinadaily.com.cn/img/attachement/jpg/site1/20160318/eca86bd77be61855f1b81c.jpg"));
        datas.add(new String("http://imgs.ebrun.com/resources/2016_04/2016_04_12/201604124411460430531500.jpg"));
        datas.add(new String("http://imgs.ebrun.com/resources/2016_04/2016_04_24/201604244971461460826484_origin.jpeg"));
        datas.add(new String("http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg"));
        return datas;
    }
}
