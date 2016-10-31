package mcxtzhang.recyclerviewdemo.generalizerv.base;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

/**
 * 介绍：RecyclerView的滑动监听器，当滑动到底部(Last)触发回调
 * 这个类仅做监听器作用，只要last 可见就不停的回调，
 * 实现它的类，应该在onLastItemVisible()这个方法里，去除重复的回调。
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/10/31.
 */

public abstract class OnScrollToLastListener extends RecyclerView.OnScrollListener {

    public static final String TAG = "zxt/ScrollListener";

    public abstract void onLastItemVisible();

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int itemCount = recyclerView.getAdapter().getItemCount();
        int lastVisibleItemPosition = -1;
        RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();

        if (dy > 0 || dx > 0) {
            if (lm instanceof GridLayoutManager) {
                GridLayoutManager gLm = (GridLayoutManager) lm;
                lastVisibleItemPosition = gLm.findLastVisibleItemPosition();
                Log.d(TAG, "onScrolled() called with: findLastVisibleItemPosition = [" + gLm.findLastVisibleItemPosition() + ", itemCount = [" + itemCount + "]");
            } else if (lm instanceof LinearLayoutManager) {
                LinearLayoutManager lLm = (LinearLayoutManager) lm;
                lastVisibleItemPosition = lLm.findLastVisibleItemPosition();
                Log.d(TAG, "onScrolled() called with: findLastVisibleItemPosition = [" + lLm.findLastVisibleItemPosition() + ", itemCount = [" + itemCount + "]");
            } else if (lm instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager sGLm = (StaggeredGridLayoutManager) lm;
                int spanCount = sGLm.getSpanCount();
                int[] lastVisibleItemPositions = sGLm.findLastVisibleItemPositions(null);
                lastVisibleItemPosition = lastVisibleItemPositions[spanCount - 1];
                Log.d(TAG, "onScrolled() called with: spanCount = [" + spanCount + ", lastIndex = [" + lastVisibleItemPositions[spanCount - 1] + "]" + ", itemCount = [" + itemCount + "]");
            }
        }

        if (lastVisibleItemPosition == itemCount - 1) {//下标会少一位的
            onLastItemVisible();
        }
    }
}
