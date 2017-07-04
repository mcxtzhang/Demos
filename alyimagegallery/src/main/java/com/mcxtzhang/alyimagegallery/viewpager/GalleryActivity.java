package com.mcxtzhang.alyimagegallery.viewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mcxtzhang.alyimagegallery.R;
import com.mcxtzhang.commonadapter.lvgv.CommonAdapter;
import com.mcxtzhang.commonadapter.lvgv.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {
    private Gallery mGallery;
    private int mSelectPos ;
    private CommonAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        mGallery = (Gallery) findViewById(R.id.viewPager);
        mGallery.setAdapter(mAdapter = new CommonAdapter<String>(this, initDatas(), R.layout.uc_item_main_image_header) {
            @Override
            public String getItem(int position) {
                return mDatas.get(position % mDatas.size());
            }

            @Override
            public long getItemId(int position) {
                return (position % mDatas.size());
            }

            @Override
            public void convert(ViewHolder viewHolder, String s, int i) {
                ImageView view = (ImageView) viewHolder.getView(R.id.image);
                Log.d("TAG", "convert() called with: viewHolder = [" + viewHolder + "], mSelectPos = [" + mSelectPos + "], i = [" + i + "]");
                if (i%mDatas.size() == mSelectPos%mDatas.size()){
                    view.setScaleY(1);


                    view.animate().scaleY(1).start();
                }else {
                    view.setScaleY(0.6f);
                }
                Glide.with(viewHolder.getConvertView().getContext())
                        .load(s)
                        .into(view);
            }

            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }
        });
        mGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Toast.makeText(GalleryActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });
        mGallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TAG", "onItemSelected() called with: parent = [" + parent + "], view = [" + view + "], position = [" + position + "], id = [" + id + "]");
                mSelectPos = position;
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mGallery.setSelection(80);


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
