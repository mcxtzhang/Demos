package mcxtzhang.recyclerviewdemo;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import static android.R.attr.topOffset;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * CSDN：http://blog.csdn.net/zxt0601
 * 时间： 16/10/18.
 */

public class CstLM extends RecyclerView.LayoutManager {
    private static final int DEFAULT_COUNT = 1;


    private static final int DIRECTION_NONE = -1;


    /* First (top-left) position visible at any point */
    private int mFirstVisiblePosition;
    /* Consistent size applied to all child views */
    private int mDecoratedChildWidth;
    private int mDecoratedChildHeight;
    /* Number of columns that exist in the grid */
    private int mTotalColumnCount = DEFAULT_COUNT;
    /* Metrics for the visible window of our data */
    private int mVisibleColumnCount;
    private int mVisibleRowCount;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //We have nothing to show for an empty data set but clear any existing views
        if (getItemCount() == 0) {
            detachAndScrapAttachedViews(recycler);
            return;
        }
        if (getChildCount() == 0 && state.isPreLayout()) {
            //Nothing to do during prelayout when empty
            return;
        }


        //Scrap measure one child
        View scrap = recycler.getViewForPosition(0);
        addView(scrap);
        measureChildWithMargins(scrap, 0, 0);

    /*
     * We make some assumptions in this code based on every child
     * view being the same size (i.e. a uniform grid). This allows
     * us to compute the following values up front because they
     * won't change.
     */
        mDecoratedChildWidth = getDecoratedMeasuredWidth(scrap);
        mDecoratedChildHeight = getDecoratedMeasuredHeight(scrap);
        detachAndScrapView(scrap, recycler);


        //Always update the visible row/column counts
        updateWindowSizing();
        int childLeft;
        int childTop;

    /*
     * Reset the visible and scroll positions
     */
        mFirstVisiblePosition = 0;
        childLeft = childTop = 0;

        //Clear all attached views into the recycle bin
        detachAndScrapAttachedViews(recycler);
        //Fill the grid for the initial layout of views
        fillGrid(DIRECTION_NONE, childLeft, childTop, recycler);


    }

    private void fillGrid(int direction, int emptyLeft, int emptyTop, RecyclerView.Recycler recycler) {
        if (mFirstVisiblePosition < 0) mFirstVisiblePosition = 0;
        if (mFirstVisiblePosition >= getItemCount()) mFirstVisiblePosition = (getItemCount() - 1);


        //1 清点目前我们所有的视图。将他们 Detach 以便稍后重新连接。
        SparseArray<View> viewCache = new SparseArray<View>(getChildCount());
        int startLeftOffset = emptyLeft;
        int startTopOffset = emptyTop;

        if (getChildCount() != 0) {
            final View topView = getChildAt(0);
            startLeftOffset = getDecoratedLeft(topView);
            startTopOffset = getDecoratedTop(topView);

            //Cache all views by their existing position, before updating counts
            for (int i = 0; i < getChildCount(); i++) {
                int position = positionOfIndex(i);
                final View child = getChildAt(i);
                viewCache.put(position, child);
            }
            //Temporarily detach all views.
            // Views we still need will be added back at the proper index.
            for (int i = 0; i < viewCache.size(); i++) {
                detachView(viewCache.valueAt(i));
            }
        }

        //2 测量/布局每一个当前可见的子视图。重新连接已有的视图很简单； 新的视图是从 Recycler 之中获取的。
        int leftOffset = startLeftOffset;
        int topOffset = startTopOffset;
        for (int i = 0; i < getVisibleChildCount(); i++) {
            int nextPosition = positionOfIndex(i);
            //...

            //Layout this position
            View view = viewCache.get(nextPosition);
            if (view == null) {
            /*
            * The Recycler will give us either a newly constructed view,
            * or a recycled view it has on-hand. In either case, the
            * view will already be fully bound to the data by the
            * adapter for us.
            */
                view = recycler.getViewForPosition(nextPosition);
                addView(view);

            /*
            * It is prudent to measure/layout each new view we
            * receive from the Recycler. We don't have to do
            * this for views we are just re-arranging.
            */
                measureChildWithMargins(view, 0, 0);
                layoutDecorated(view, leftOffset, topOffset,
                        leftOffset + mDecoratedChildWidth,
                        topOffset + mDecoratedChildHeight);
            } else {
                //Re-attach the cached view at its new index
                attachView(view);
                viewCache.remove(nextPosition);
            }

            if (i % mVisibleColumnCount == (mVisibleColumnCount - 1)) {
                leftOffset = startLeftOffset;
                topOffset += mDecoratedChildHeight;


            } else {
                leftOffset += mDecoratedChildWidth;
            }
        }

        for (int i = 0; i < viewCache.size(); i++) {
            recycler.recycleView(viewCache.valueAt(i));
        }


    }

    /*
     * Rather than continuously checking how many views we can fit
     * based on scroll offsets, we simplify the math by computing the
     * visible grid as what will initially fit on screen, plus one.
     */
    private void updateWindowSizing() {
        //求出可见的能容纳多少列
        mVisibleColumnCount = (getHorizontalSpace() / mDecoratedChildWidth) + 1;
        if (getHorizontalSpace() % mDecoratedChildWidth > 0) {//不整除 多加一列
            mVisibleColumnCount++;
        }

        //Allow minimum value for small data sets
        if (mVisibleColumnCount > getTotalColumnCount()) {
            mVisibleColumnCount = getTotalColumnCount();//边界处理 不能大于一共的ItemCount
        }

        //求出可见的能容纳多少行
        mVisibleRowCount = (getVerticalSpace() / mDecoratedChildHeight) + 1;
        if (getVerticalSpace() % mDecoratedChildHeight > 0) {
            mVisibleRowCount++;
        }

        if (mVisibleRowCount > getTotalRowCount()) {
            mVisibleRowCount = getTotalRowCount();
        }
    }


    /*
     * Mapping between child view indices and adapter data
    * positions helps fill the proper views during scrolling.
    */
    //根据index 返回当前item的postion
    private int positionOfIndex(int childIndex) {
        int row = childIndex / mVisibleColumnCount;
        int column = childIndex % mVisibleColumnCount;

        return mFirstVisiblePosition + (row * getTotalColumnCount()) + column;
    }


    private int getVisibleChildCount() {
        return mVisibleColumnCount * mVisibleRowCount;
    }

    /**
     * 定义的列数
     *
     * @return
     */
    private int getTotalColumnCount() {
        if (getItemCount() < mTotalColumnCount) {//一共的item 都不够列数，那么就返回itemCount
            return getItemCount();
        }

        return mTotalColumnCount;
    }

    /**
     * 一共的行数
     *
     * @return
     */
    private int getTotalRowCount() {
        if (getItemCount() == 0 || mTotalColumnCount == 0) {//异常情况处理
            return 0;
        }
        int maxRow = getItemCount() / mTotalColumnCount;//最大行数
        //Bump the row count if it's not exactly even
        if (getItemCount() % mTotalColumnCount != 0) {//不能整除要+1
            maxRow++;
        }
        return maxRow;
    }

    private int getHorizontalSpace() {
        return getWidth() - getPaddingRight() - getPaddingLeft();
    }

    private int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }
}
