package mcxtzhang.recyclerviewdemo.generalizerv.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

import com.mcxtzhang.zxtcommonlib.recyclerview.HeaderRecyclerAndFooterWrapperAdapter;

import mcxtzhang.recyclerviewdemo.generalizerv.base.widget.LoadingFooter;

/**
 * 介绍：封装的Rv
 * 1 支持滑动到底部，自动触发加载更多操作,关联接口OnLoadMoreListener
 * 2
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/10/31.
 */

public class ZRecyclerView extends RecyclerView {
    private static final String TAG = "zxt/ZRecyclerView";
    private Context mContext;

    private HeaderRecyclerAndFooterWrapperAdapter mWrapperAdapter;//装饰Adapter
    private LoadingFooter mFooter;

    public ZRecyclerView(Context context) {
        this(context, null);
    }

    public ZRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }


    private void init(Context context) {
        mContext = context;
        mFooter = new LoadingFooter(context, new LoadingFooter.OnLoadErrorClickListener() {
            @Override
            public void onClick() {
                //错误重试
                if (null != mOnLoadMoreListener) {
                    mFooter.setState(LoadingFooter.State.Loading);
                    mOnLoadMoreListener.onLoadMore();
                }
            }
        });
        addOnScrollListener(new OnScrollToLastListener() {
            @Override
            public void onLastItemVisible() {
                Log.e(TAG, "onLastItemVisible: ");
                switch (mFooter.getState()) {
                    case Normal://正常状态，应该进入loading，同时回调加载更多接口
                        mFooter.setState(LoadingFooter.State.Loading);
                        Log.e(TAG, "加载更多");
                        if (null != mOnLoadMoreListener) {
                            mOnLoadMoreListener.onLoadMore();
                        }
                        break;
                    case Loading:
                        //啥也不干，
                        break;
                    case Error:
                        //啥也不干
                        break;
                    case End:
                        //啥也不干
                        break;
                }
            }
        });


    }

    /**
     * 重写setAdapter,扩展功能，+上footer。
     *
     * @param adapter
     */
    @Override
    public void setAdapter(Adapter adapter) {
        if (!(adapter instanceof HeaderRecyclerAndFooterWrapperAdapter)) {
            Log.e(TAG, "Error setAdapter: need the adapter instanceof HeaderRecyclerAndFooterWrapperAdapter!!!!!!");
            return;
        }
        mWrapperAdapter = (HeaderRecyclerAndFooterWrapperAdapter) adapter;
        super.setAdapter(adapter);
        mWrapperAdapter.addFooterView(mFooter);
    }


    /**
     * LoadMore接口
     */
    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    private OnLoadMoreListener mOnLoadMoreListener;

    public OnLoadMoreListener getOnLoadMoreListener() {
        return mOnLoadMoreListener;
    }

    public ZRecyclerView setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
        return this;
    }

    /**
     * Footer处理
     */
    public void setFooterState(LoadingFooter.State state) {
        mFooter.setState(state);
    }

}
