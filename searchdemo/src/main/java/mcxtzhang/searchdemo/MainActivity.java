package mcxtzhang.searchdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mLv;
    private JokeFillterAdapter mJokeFillterAdapter;
    private EditText mEtSearch;
    private ArrayList<TestBean> mDatas;


    private static final int MSG_UPDATE_SEARCH_RESULT = 1001;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_SEARCH_RESULT:
                    mJokeFillterAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLv = (ListView) findViewById(R.id.mLv);
        mLv.setTextFilterEnabled(true); // 开启过滤功能
        mEtSearch = (EditText) findViewById(R.id.mEtSearch);
        mEtSearch.addTextChangedListener(new JokeListener());

        initDatas();
        mJokeFillterAdapter = new JokeFillterAdapter(this, R.layout.item, mDatas) {
            @Override
            public void convert(ViewHolder holder, TestBean testBean) {
                holder.setText(R.id.tv1, testBean.getName());
                holder.setText(R.id.tv2, testBean.getUrl());
            }
        };
        mLv.setAdapter(mJokeFillterAdapter);

    }

    class JokeListener implements TextWatcher {
        @Override
        public void afterTextChanged(Editable s) {//表示最终内容
            Log.d("afterTextChanged", s.toString());
        }

        /**
         * @param s
         * @param start 开始的位置
         * @param count 被改变的旧内容数
         * @param after 改变后的内容数量
         */
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //这里的s表示改变之前的内容，通常start和count组合，可以在s中读取本次改变字段中被改变的内容。而after表示改变后新的内容的数量。
        }

        /**
         * @param str
         * @param start  开始位置
         * @param before 改变前的内容数量
         * @param count  新增数
         */
        @Override
        public void onTextChanged(CharSequence str, int start, int before, int count) {
            //这里的s表示改变之后的内容，通常start和count组合，可以在s中读取本次改变字段中新的内容。而before表示被改变的内容的数量。

            if (TextUtils.isEmpty(str.toString().trim())) {
                mLv.clearTextFilter();//搜索文本为空时，过滤设置
                //mJokeFillterAdapter.getFilter().filter("");
                mHandler.sendEmptyMessage(MSG_UPDATE_SEARCH_RESULT);
            } else {
//                    search_list.clearTextFilter();
                mLv.setFilterText(str.toString().trim());//设置过滤关键字  会带上一个局域屏幕中心的搜索文本提示
                //mJokeFillterAdapter.getFilter().filter(str.toString().trim()); // 不会带上一个局域屏幕中心的搜索文本提示
                mHandler.sendEmptyMessage(MSG_UPDATE_SEARCH_RESULT);
            }

        }
    }

    public List<TestBean> initDatas() {
        mDatas = new ArrayList<>();
        int i = 0;
        for (int j = 0; j < 600; j++) {

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
}
