package mcxtzhang.itemdecorationdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.github.promeg.pinyinhelper.Pinyin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mcxtzhang.itemdecorationdemo.decoration.DividerItemDecoration;
import mcxtzhang.itemdecorationdemo.decoration.TitleItemDecoration;
import mcxtzhang.itemdecorationdemo.widget.IndexBar;
import mcxtzhang.itemdecorationdemo.widget.SideBar;

public class MainActivity extends Activity {
    private static final String TAG = "zxt";
    private RecyclerView mRv;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mManager;
    private List<CityBean> mDatas;
    private List<String> mSideBarDatas;

    private TitleItemDecoration mDecoration;


    /**
     * 右侧边栏导航区域
     */
    private SideBar mSideBar;
    private IndexBar mIndexBar;

    /**
     * 显示指示器DialogText
     */
    private TextView mTvSideBarHint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSideBar = (SideBar) findViewById(R.id.sideBar);
        mTvSideBarHint = (TextView) findViewById(R.id.tvSideBarHint);
        mSideBar.setTextView(mTvSideBarHint);

        mIndexBar = (IndexBar) findViewById(R.id.indexBar);
        mIndexBar.setmPressedShowTextView(mTvSideBarHint);

        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                int position = getPosByTag(s);
                if (position != -1) {
                    mManager.scrollToPositionWithOffset(position/* + mAdapter.getHeaderViewsCount()*/, 0);
                }
            }
        });

        mRv = (RecyclerView) findViewById(R.id.rv);
        mRv.setLayoutManager(mManager = new LinearLayoutManager(this));
        //initDatas();
        initDatas(getResources().getStringArray(R.array.provinces));
        mRv.setAdapter(mAdapter = new CityAdapter(this, mDatas));
        mRv.addItemDecoration(mDecoration = new TitleItemDecoration(this, mDatas));
        //如果add两个，那么按照先后顺序，依次渲染。
        //mRv.addItemDecoration(new TitleItemDecoration2(this,mDatas));
        mRv.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL_LIST));


    }

    /**
     * 组织数据源
     *
     * @param data
     * @return
     */
    private void initDatas(String[] data) {
        mDatas = new ArrayList<>();
        mSideBarDatas = new ArrayList<>();//导航栏数据源

        for (int i = 0; i < data.length; i++) {
            CityBean cityBean = new CityBean();
            cityBean.setCity(data[i]);//设置城市名称

            StringBuilder pySb = new StringBuilder();
            //取出首个char得到它的拼音
            for (int i1 = 0; i1 < data[i].length(); i1++) {
                //如果c为汉字，则返回大写拼音；如果c不是汉字，则返回String.valueOf(c)
                pySb.append(Pinyin.toPinyin(data[i].charAt(i1)));
            }
            cityBean.setPyCity(pySb.toString());//设置城市名拼音

            //以下代码设置城市拼音首字母
            String sortString = pySb.toString().substring(0, 1);
            if (sortString.matches("[A-Z]")) {//如果是A-Z字母开头
                cityBean.setTag(sortString);
                if (!mSideBarDatas.contains(sortString)) {
                    mSideBarDatas.add(sortString);
                }
            } else {
                cityBean.setTag("#");
                if (!mSideBarDatas.contains("#")) {
                    mSideBarDatas.add("#");
                }
            }
            mDatas.add(cityBean);
        }
        sortData();
    }

    /**
     * 对数据源排序
     */
    private void sortData() {
        //对右侧栏进行排序 将 # 丢在最后
        Collections.sort(mSideBarDatas, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                if (lhs.equals("#")) {
                    return 1;
                } else if (rhs.equals("#")) {
                    return -1;
                } else {
                    return lhs.compareTo(rhs);
                }
            }
        });
        mSideBar.setIndexText(mSideBarDatas);

        //对数据源进行排序
        Collections.sort(mDatas, new Comparator<CityBean>() {
            @Override
            public int compare(CityBean lhs, CityBean rhs) {
                if (lhs.getTag().equals("#")) {
                    return 1;
                } else if (rhs.getTag().equals("#")) {
                    return -1;
                } else {
                    return lhs.getPyCity().compareTo(rhs.getPyCity());
                }
            }
        });
    }


    /**
     * 根据传入的pos返回tag
     *
     * @param tag
     * @return
     */
    private int getPosByTag(String tag) {
        if (TextUtils.isEmpty(tag)) {
            return -1;
        }
        for (int i = 0; i < mDatas.size(); i++) {
            if (tag.equals(mDatas.get(i).getTag())) {
                return i;
            }
        }
        return -1;
    }
}
