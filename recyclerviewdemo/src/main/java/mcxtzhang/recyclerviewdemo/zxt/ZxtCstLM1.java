package mcxtzhang.recyclerviewdemo.zxt;

import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * 介绍：第一个自定义LM
 * 模仿一个ListView好了
 * 2016 10 19:
 * 1 先当所有View都一样大
 * 2 先不考虑刷新
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/10/19.
 */

public class ZxtCstLM1 extends RecyclerView.LayoutManager {
    /* Consistent size applied to all child views */
    private int mDecoratedChildWidth;
    private int mDecoratedChildHeight;

    private int mFirstVisiblePosition;//记录第一个可见的postion 不太好算啊

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        Log.d("TAG", "onLayoutChildren() called with: recycler = [" + recycler + "], state = [" + state + "]" + "[isPreLayout:" + state.isPreLayout());
        //是 LayoutManager 的主入口。 它会在 view 需要初始化布局时调用， 当适配器的数据改变时(或者整个适配器被换掉时)会再次调用。
        if (getItemCount() <= 0) {
            removeAndRecycleAllViews(recycler);
            return;
        }
        if (getChildCount() <= 0 && state.isPreLayout()) {
            //Nothing to do during prelayout when empty
            return;
        }
        if (getChildCount() == 0) {
            View topView = recycler.getViewForPosition(0);
            addView(topView);
            measureChildWithMargins(topView, 0, 0);
            mDecoratedChildHeight = getDecoratedMeasuredHeight(topView);
            mDecoratedChildWidth = getDecoratedMeasuredWidth(topView);
            detachAndScrapView(topView, recycler);
            mFirstVisiblePosition = 0;
        }


        //onLayoutChildren()初始化时会走两遍。。所以每次布局之前先detach掉所有的
        detachAndScrapAttachedViews(recycler);


        int leftOffset = getPaddingLeft();
        int topOffset = getPaddingTop();
        fillChild(recycler, leftOffset, topOffset, 0);
    }


    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        //Take top measurements from the top-left child
        final View topView = getChildAt(0);
        //Take bottom measurements from the bottom-right child.
        final View bottomView = getChildAt(getChildCount() - 1);

        //Optimize the case where the entire data set is too small to scroll
        int viewSpan = getDecoratedBottom(bottomView) - getDecoratedTop(topView);
        if (viewSpan <= getVerticalSpace()) {//不足一屏幕不滑动
            //We cannot scroll in either direction
            return 0;
        }
        //上述都是优化

        int delta = 0;
        boolean isSeeTop = mFirstVisiblePosition == 0;
        boolean isSeeBottom = mFirstVisiblePosition + getScreenChildCount() >= getItemCount();
        //边界处理
        if (dy > 0) {// + 上啦 显示底端
            if (isSeeBottom) {
                // TODO: 2016/10/19 应该还有问题
                delta = getDecoratedBottom(bottomView) - (getHeight() - getPaddingBottom());
            } else {
                delta = dy;
            }
        } else {// - 下拉 显示顶端
            if (isSeeTop) {
                //算出topView的Top+topMargin的坐标
                int topViewTop = getDecoratedTop(topView) - ((RecyclerView.LayoutParams) topView.getLayoutParams()).topMargin;
                delta = Math.max(-(getPaddingTop() - topViewTop), dy);
            } else {
                delta = dy;
            }
        }

        int count;
        if (delta > 0) {// + 上啦 显示底端,可能隐藏topView，mFirstVisiblePosition增加
            count = (-getDecoratedTop(topView) + delta) / mDecoratedChildHeight;
            if (count > 0) {
                //mFirstVisiblePosition = mFirstVisiblePosition + count;
            } else {
                count = 0;
            }
        } else {// - 下拉 显示顶端
            int gap = getDecoratedTop(topView) - delta;
            if (gap >= 0) {
                count = gap / mDecoratedChildHeight   +1;
                count = -count;
            }else {
                count = 0;
            }
        }


        offsetChildrenVertical(-delta);//平移View,-向上 ,+ 向下

        fillChild(recycler, 0, 0, count);

        return -delta;

    }


    /**
     * 填充Child
     *
     * @param recycler
     * @param leftOffset
     * @param topOffset
     * @param delta      上拉+，下拉-
     */
    private void fillChild(RecyclerView.Recycler recycler, int leftOffset, int topOffset, int count) {
        //边界处理
        if (mFirstVisiblePosition < 0) mFirstVisiblePosition = 0;
        if (mFirstVisiblePosition >= getItemCount()) mFirstVisiblePosition = (getItemCount() - 1);

        int fillLeftOffset = leftOffset;
        int fillTopOffset = topOffset;

        //滑动时，临时detach屏幕上的可见的View，不可见的View应该recycler掉,
        SparseArray<View> tempCache = new SparseArray<>();
        if (getChildCount() > 0) {

            // 缓存屏幕上所有的View
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                tempCache.put(i + mFirstVisiblePosition, child);//以postion，View缓存它们
                //detachView(child);//并且detach
            }

            //屏幕上有View 那么这个偏移量按照屏幕上的TopView来
            View topView /*= getChildAt(0);
            int topGap = getDecoratedTop(topView) - getPaddingTop();
            if (topGap != 0) {
                if (topGap > 0) {//mFirstVisiblePosition要减少咯
                    int count = topGap / mDecoratedChildHeight;
*//*                    if (topGap % mDecoratedChildHeight > 0) {
                        count++;
                    }*//*
                    mFirstVisiblePosition = mFirstVisiblePosition - count;
                } else {//mFirstVisiblePosition要增加咯
                    topGap = -topGap;
                    int count = topGap / mDecoratedChildHeight;
*//*                    if (topGap % mDecoratedChildHeight > 0) {
                        count++;
                    }*//*
                    mFirstVisiblePosition = mFirstVisiblePosition + count;
                }
            }
            topView*/ = getVisibleTopViewAndRecycleAboveView(recycler);
            View bottomView = getVisibleBottomViewAndRecycleBelowView(recycler);
            fillLeftOffset = getDecoratedLeft(topView);
            fillTopOffset = getDecoratedTop(topView);

        }
        mFirstVisiblePosition = mFirstVisiblePosition + count;
        //布局所有应该可见的View，如果缓存里有，不需要做任何操作，如果没有，layout出来
        for (int i = 0; i < getScreenChildCount(); i++) {//还没考虑刷新
            int position = i + mFirstVisiblePosition;
            //// TODO: 16/10/20 已有的view有问题 
            View child = tempCache.get(position);
            if (child == null) {
                child = recycler.getViewForPosition(position);
                addView(child);
                measureChildWithMargins(child, 0, 0);
                int childHeight = getDecoratedMeasuredHeight(child);
                int childWidth = getDecoratedMeasuredWidth(child);
                layoutDecoratedWithMargins(child, fillLeftOffset, fillTopOffset, fillLeftOffset + childWidth, fillTopOffset + childHeight);

            } else {
/*                tempCache.remove(position);//从缓存移除
                attachView(child);*/
            }
            fillTopOffset += getDecoratedMeasuredHeight(child);
        }

/*        for (int i = 0; i < tempCache.size(); i++) {
            recycler.recycleView(tempCache.get(i));//detachView 后 没有attachView的话 就要真的回收掉他们
        }*/

        Log.d("TAG", "childCout:" + getChildCount());
    }

    /**
     * 获取可见的BottomView，并且回收在其之下的View
     *
     * @param recycler
     * @return
     */
    private View getVisibleBottomViewAndRecycleBelowView(RecyclerView.Recycler recycler) {
        View bottomView = getChildAt(getChildCount() - 1);
        //如果top》paddingBottom 不可见 回收

        int decoratedTop = getDecoratedTop(bottomView);
        int bottom = getHeight() - getPaddingBottom();

        if (decoratedTop > bottom) {
            removeAndRecycleView(bottomView, recycler);
            return getVisibleBottomViewAndRecycleBelowView(recycler);
        } else {
            return bottomView;
        }
    }


    /**
     * 获取可见的TopView，并且回收不可见的TOpView之上的View
     *
     * @param recycler
     * @return
     */
    public View getVisibleTopViewAndRecycleAboveView(RecyclerView.Recycler recycler) {
        View topView = getChildAt(0);
        //如果bottom 《paddingTop ,该回收了
        if (getDecoratedBottom(topView) < getPaddingTop()) {
            removeAndRecycleView(topView, recycler);
            return getVisibleTopViewAndRecycleAboveView(recycler);
        } else {
            return topView;
        }
    }

    /**
     * 返回屏幕里的ChildCount
     *
     * @return
     */
    public int getScreenChildCount() {
        int count = 0;
        count = getVerticalSpace() / mDecoratedChildHeight;
        if (getVerticalSpace() % mDecoratedChildHeight > 0) {
            count++;
        }
        return count;
    }

    public int getVerticalSpace() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    public int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }
}
