package mcxtzhang.recyclerviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.mcxtzhang.zxtcommonlib.recyclerview.CommonAdapter;
import com.mcxtzhang.zxtcommonlib.recyclerview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import mcxtzhang.recyclerviewdemo.itemlistener.OnItemTouchListener;
import mcxtzhang.recyclerviewdemo.zxt.FlowLayoutManager;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRv;
    private CommonAdapter<TestBean> mAdapter;
    private List<TestBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatas();
        mRv = (RecyclerView) findViewById(R.id.rv);
        //mRv.setLayoutManager(new CstLinearLayoutManager());//一个Demo
        //mRv.setLayoutManager(new CstLM());//学习仿造国外大神


        FixedGridLayoutManager layoutManager = new FixedGridLayoutManager();//国外大神
        //layoutManager.setTotalColumnCount(2);
        //mRv.setLayoutManager(layoutManager);

        //mRv.setLayoutManager(new ZxtCstLM2());//自己的第一个成品
        mRv.setLayoutManager(new FlowLayoutManager());//自己写的流式布局

        //mRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        //mRv.setLayoutManager(new CstSysLM(this));

        mRv.addOnItemTouchListener(new OnItemTouchListener<ViewHolder, TestBean>(this, mRv, mDatas) {
            @Override
            protected void onItemClick(ViewHolder viewHolder, TestBean data, View itemView, int position) {
                Log.d("TAG1", "onItemClick() called with: itemView = [" + itemView + "], viewHolder = [" + viewHolder + "], position = [" + position + "]");
            }

            @Override
            protected void onItemLongClick(ViewHolder viewHolder, TestBean data, View itemView, int position) {
                Log.d("TAG1", "onItemLongClick() called with: itemView = [" + itemView + "], viewHolder = [" + viewHolder + "], position = [" + position + "]");
            }

        });


        mRv.setAdapter(mAdapter = new CommonAdapter<TestBean>(this, R.layout.item_flow, mDatas) {
            private int lastHeight = 0;

            @Override
            public void convert(ViewHolder holder, TestBean testBean) {
                Log.d("zxt", "convert() called with: holder = [" + holder + "], testBean = [" + testBean + "]");
                holder.setText(R.id.tv, testBean.getName() + testBean.getUrl());
                holder.setOnClickListener(R.id.tv, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("TAG1", "onClick() called with: v = [" + v + "]");
                    }
                });
                //add by zhangxutong Feature1: 不同大小的Item也适应
/*                if (holder.getAdapterPosition() == 0) {
                    ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
                    if (layoutParams != null) {
                        lastHeight = layoutParams.height;
                        layoutParams.height = 500;
                    } else {
                        lastHeight = 0;
                        layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500);
                    }
                    holder.itemView.setLayoutParams(layoutParams);
                } else {
                    ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
                    if (layoutParams != null) {
                        layoutParams.height = lastHeight;
                    } else {
                        layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500);
                    }
                    holder.itemView.setLayoutParams(layoutParams);
                }*/

            }
        });
    }


    private int i = 0;

    public List<TestBean> initDatas() {
        mDatas = new ArrayList<>();

        for (int j = 0; j < 6; j++) {
            mDatas.add(new TestBean((i++) + "  ", "张"));
            mDatas.add(new TestBean((i++) + " ", "旭童"));
            mDatas.add(new TestBean((i++) + " ", "多种type"));
            mDatas.add(new TestBean((i++) + "    ", "遍"));
            mDatas.add(new TestBean((i++) + "   ", "多种type"));
            mDatas.add(new TestBean((i++) + "  ", "多种type"));
            mDatas.add(new TestBean((i++) + "  ", "多种type"));
            mDatas.add(new TestBean((i++) + "  ", "多种type"));
        }
        return mDatas;
    }

    public void add(View vIew) {
        mDatas.add(new TestBean((i++) + "  ", "新增的一个Item"));
        mAdapter.notifyDataSetChanged();
    }

    public void del(View vIew) {
        mDatas.remove(mDatas.size() - 1);
        mAdapter.notifyDataSetChanged();
    }




    /*private void initDatas() {
        int i = 0;
        mDatas = new ArrayList<>();

        for (int j = 0; j < 1; j++) {
            mDatas.add(new TestBean((i++) + "", "http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://fudaoquan.com/wp-content/uploads/2016/04/wanghong.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://imgs.ebrun.com/resources/2016_03/2016_03_25/201603259771458878793312_origin.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://p14.go007.com/2014_11_02_05/a03541088cce31b8_1.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://news.k618.cn/tech/201604/W020160407281077548026.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://www.kejik.com/image/1460343965520.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://cn.chinadaily.com.cn/img/attachement/jpg/site1/20160318/eca86bd77be61855f1b81c.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://imgs.ebrun.com/resources/2016_04/2016_04_12/201604124411460430531500.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://imgs.ebrun.com/resources/2016_04/2016_04_24/201604244971461460826484_origin.jpeg"));
            mDatas.add(new TestBean((i++) + "", "http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://fudaoquan.com/wp-content/uploads/2016/04/wanghong.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://imgs.ebrun.com/resources/2016_03/2016_03_25/201603259771458878793312_origin.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://p14.go007.com/2014_11_02_05/a03541088cce31b8_1.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://news.k618.cn/tech/201604/W020160407281077548026.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://www.kejik.com/image/1460343965520.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://cn.chinadaily.com.cn/img/attachement/jpg/site1/20160318/eca86bd77be61855f1b81c.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://imgs.ebrun.com/resources/2016_04/2016_04_12/201604124411460430531500.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://imgs.ebrun.com/resources/2016_04/2016_04_24/201604244971461460826484_origin.jpeg"));
            mDatas.add(new TestBean((i++) + "", "http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg"));
        }


    }*/
}
