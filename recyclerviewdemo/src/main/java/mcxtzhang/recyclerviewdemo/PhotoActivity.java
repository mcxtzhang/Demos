package mcxtzhang.recyclerviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mcxtzhang.commonadapter.rv.mul.BaseMulTypeAdapter;

import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends AppCompatActivity {

    List<PhotoBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        rv.setLayoutManager(layoutManager);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mDatas.get(position).isTitle() ? layoutManager.getSpanCount() : 1;
            }
        });
        rv.setAdapter(new BaseMulTypeAdapter<PhotoBean>(this, mDatas = initDatas()));
    }

    public List<PhotoBean> initDatas() {
        List<PhotoBean> datas = new ArrayList<>();
        datas.add(new PhotoBean(true, "http://imgs.ebrun.com/resources/2016_03/2016_03_25/201603259771458878793312_origin.jpg", "张"));
        datas.add(new PhotoBean(false, "http://p14.go007.com/2014_11_02_05/a03541088cce31b8_1.jpg", "旭童"));
        datas.add(new PhotoBean(false, "http://news.k618.cn/tech/201604/W020160407281077548026.jpg", "多种type"));
        datas.add(new PhotoBean(true, "http://cn.chinadaily.com.cn/img/attachement/jpg/site1/20160318/eca86bd77be61855f1b81c.jpg", "多种type"));
        datas.add(new PhotoBean(false, "http://imgs.ebrun.com/resources/2016_04/2016_04_12/201604124411460430531500.jpg", "多种type"));
        datas.add(new PhotoBean(false, "http://imgs.ebrun.com/resources/2016_04/2016_04_24/201604244971461460826484_origin.jpeg", "多种type"));
        datas.add(new PhotoBean(false, "http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg", "多种type"));
        datas.add(new PhotoBean(false, "http://imgs.ebrun.com/resources/2016_04/2016_04_12/201604124411460430531500.jpg", "多种type"));
        datas.add(new PhotoBean(false, "http://imgs.ebrun.com/resources/2016_04/2016_04_24/201604244971461460826484_origin.jpeg", "多种type"));
        datas.add(new PhotoBean(false, "http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg", "多种type"));
        datas.add(new PhotoBean(true, "http://imgs.ebrun.com/resources/2016_03/2016_03_25/201603259771458878793312_origin.jpg", "张"));
        datas.add(new PhotoBean(false, "http://p14.go007.com/2014_11_02_05/a03541088cce31b8_1.jpg", "旭童"));
        datas.add(new PhotoBean(false, "http://news.k618.cn/tech/201604/W020160407281077548026.jpg", "多种type"));
        datas.add(new PhotoBean(true, "http://cn.chinadaily.com.cn/img/attachement/jpg/site1/20160318/eca86bd77be61855f1b81c.jpg", "多种type"));
        datas.add(new PhotoBean(false, "http://imgs.ebrun.com/resources/2016_04/2016_04_12/201604124411460430531500.jpg", "多种type"));
        datas.add(new PhotoBean(false, "http://imgs.ebrun.com/resources/2016_04/2016_04_24/201604244971461460826484_origin.jpeg", "多种type"));
        datas.add(new PhotoBean(false, "http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg", "多种type"));
        datas.add(new PhotoBean(false, "http://imgs.ebrun.com/resources/2016_04/2016_04_12/201604124411460430531500.jpg", "多种type"));
        datas.add(new PhotoBean(false, "http://imgs.ebrun.com/resources/2016_04/2016_04_24/201604244971461460826484_origin.jpeg", "多种type"));
        datas.add(new PhotoBean(false, "http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg", "多种type"));
        datas.add(new PhotoBean(true, "http://imgs.ebrun.com/resources/2016_03/2016_03_25/201603259771458878793312_origin.jpg", "张"));
        datas.add(new PhotoBean(false, "http://p14.go007.com/2014_11_02_05/a03541088cce31b8_1.jpg", "旭童"));
        datas.add(new PhotoBean(false, "http://news.k618.cn/tech/201604/W020160407281077548026.jpg", "多种type"));
        datas.add(new PhotoBean(true, "http://cn.chinadaily.com.cn/img/attachement/jpg/site1/20160318/eca86bd77be61855f1b81c.jpg", "多种type"));
        datas.add(new PhotoBean(false, "http://imgs.ebrun.com/resources/2016_04/2016_04_12/201604124411460430531500.jpg", "多种type"));
        datas.add(new PhotoBean(false, "http://imgs.ebrun.com/resources/2016_04/2016_04_24/201604244971461460826484_origin.jpeg", "多种type"));
        datas.add(new PhotoBean(false, "http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg", "多种type"));
        datas.add(new PhotoBean(false, "http://imgs.ebrun.com/resources/2016_04/2016_04_12/201604124411460430531500.jpg", "多种type"));
        datas.add(new PhotoBean(false, "http://imgs.ebrun.com/resources/2016_04/2016_04_24/201604244971461460826484_origin.jpeg", "多种type"));
        datas.add(new PhotoBean(false, "http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg", "多种type"));
        datas.add(new PhotoBean(true, "http://imgs.ebrun.com/resources/2016_03/2016_03_25/201603259771458878793312_origin.jpg", "张"));
        datas.add(new PhotoBean(false, "http://p14.go007.com/2014_11_02_05/a03541088cce31b8_1.jpg", "旭童"));
        datas.add(new PhotoBean(false, "http://news.k618.cn/tech/201604/W020160407281077548026.jpg", "多种type"));
        datas.add(new PhotoBean(true, "http://cn.chinadaily.com.cn/img/attachement/jpg/site1/20160318/eca86bd77be61855f1b81c.jpg", "多种type"));
        datas.add(new PhotoBean(false, "http://imgs.ebrun.com/resources/2016_04/2016_04_12/201604124411460430531500.jpg", "多种type"));
        datas.add(new PhotoBean(false, "http://imgs.ebrun.com/resources/2016_04/2016_04_24/201604244971461460826484_origin.jpeg", "多种type"));
        datas.add(new PhotoBean(false, "http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg", "多种type"));
        datas.add(new PhotoBean(false, "http://imgs.ebrun.com/resources/2016_04/2016_04_12/201604124411460430531500.jpg", "多种type"));
        datas.add(new PhotoBean(false, "http://imgs.ebrun.com/resources/2016_04/2016_04_24/201604244971461460826484_origin.jpeg", "多种type"));
        datas.add(new PhotoBean(false, "http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg", "多种type"));
        datas.add(new PhotoBean(true, "http://imgs.ebrun.com/resources/2016_03/2016_03_25/201603259771458878793312_origin.jpg", "张"));
        datas.add(new PhotoBean(false, "http://p14.go007.com/2014_11_02_05/a03541088cce31b8_1.jpg", "旭童"));
        datas.add(new PhotoBean(false, "http://news.k618.cn/tech/201604/W020160407281077548026.jpg", "多种type"));
        datas.add(new PhotoBean(true, "http://cn.chinadaily.com.cn/img/attachement/jpg/site1/20160318/eca86bd77be61855f1b81c.jpg", "多种type"));
        datas.add(new PhotoBean(false, "http://imgs.ebrun.com/resources/2016_04/2016_04_12/201604124411460430531500.jpg", "多种type"));
        datas.add(new PhotoBean(false, "http://imgs.ebrun.com/resources/2016_04/2016_04_24/201604244971461460826484_origin.jpeg", "多种type"));
        datas.add(new PhotoBean(false, "http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg", "多种type"));
        datas.add(new PhotoBean(false, "http://imgs.ebrun.com/resources/2016_04/2016_04_12/201604124411460430531500.jpg", "多种type"));
        datas.add(new PhotoBean(false, "http://imgs.ebrun.com/resources/2016_04/2016_04_24/201604244971461460826484_origin.jpeg", "多种type"));
        datas.add(new PhotoBean(false, "http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg", "多种type"));

        return datas;
    }
}
