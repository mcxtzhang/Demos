package mcxtzhang.zandemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ZanCallBack {
    private static final String TAG = "zxt";
    private RecyclerView mRv;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mManager;
    private List<ZanBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRv = (RecyclerView) findViewById(R.id.rv);
        mRv.setLayoutManager(mManager = new LinearLayoutManager(this));
        initDatas();
        mRv.setAdapter(mAdapter = new ZanAdapter(this, mDatas));
    }

    private void initDatas() {
        mDatas = new ArrayList<>();
        mDatas.add(new ZanBean(false, 0));
        mDatas.add(new ZanBean(false, 1));
        mDatas.add(new ZanBean(true, 2));
        mDatas.add(new ZanBean(true, 3));
        mDatas.add(new ZanBean(true, 4));
        mDatas.add(new ZanBean(false, 5));
        mDatas.add(new ZanBean(false, 6));
    }

    @Override
    public void onSuccess(int pos) {
        Log.d(TAG, "onZanSuccess() called with: pos = [" + pos + "]");
        //网络回调成功，改变数据，同时判断postion，如果positon在屏幕内则刷新UI 不在不用刷新。
        //桥黑板！！这里不用也不应该刷新！因为ViewHolder是服用的

        //操作的回调都不需要传入操作类型，因为点赞 取消点赞是互斥的操作，只要对原来的状态取反即可
        if (pos < mDatas.size()) {//万一这个item被删除了
            ZanBean zanBean = mDatas.get(pos);
            if (null != zanBean) {//改变数据
                zanBean.setZaned(!zanBean.isZaned());
            }
            if (pos <= mManager.findLastVisibleItemPosition() && pos >= mManager.findFirstVisibleItemPosition()) {
                mAdapter.notifyItemChanged(pos);//定向刷新某个item
            }
        }


    }

    @Override
    public void onError(int pos) {
        Log.d(TAG, "onZanError() called with: pos = [" + pos + "]");
        //网络回调失败，数据不用改变，只要判断postion是否在屏幕内，如果在改变UI，否则啥都不用做。
        if (pos <= mManager.findLastVisibleItemPosition() && pos >= mManager.findFirstVisibleItemPosition()) {
            mAdapter.notifyItemChanged(pos);//定向刷新某个item
        }
    }
}
