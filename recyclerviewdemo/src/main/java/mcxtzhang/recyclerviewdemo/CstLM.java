package mcxtzhang.recyclerviewdemo;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

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
    private static final int DIRECTION_START = 0;
    private static final int DIRECTION_END = 1;
    private static final int DIRECTION_UP = 2;
    private static final int DIRECTION_DOWN = 3;


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
            detachAndScrapAttachedViews(recycler);//轻量回收所有View
            return;
        }
        if (getChildCount() == 0 && state.isPreLayout()) {
            //Nothing to do during prelayout when empty
            return;
        }


        //Scrap measure one child
        View scrap = recycler.getViewForPosition(0);//获取postion的View
        addView(scrap);
        measureChildWithMargins(scrap, 0, 0);//测量View

    /*
     * We make some assumptions in this code based on every child
     * view being the same size (i.e. a uniform grid). This allows
     * us to compute the following values up front because they
     * won't change.
     */
        mDecoratedChildWidth = getDecoratedMeasuredWidth(scrap);//获取+上Decorated的 宽 高、上下 左右
        mDecoratedChildHeight = getDecoratedMeasuredHeight(scrap);
        detachAndScrapView(scrap, recycler);//轻量回收指定View


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
        fillGrid(DIRECTION_NONE, childLeft, childTop, recycler, state);


    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        Log.d("TAG", "scrollVerticallyBy() called with: dy = [" + dy + "]");
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

        int delta;//位移
        int maxRowCount = getTotalRowCount();//所有Item的行数，这里应该是20
        boolean topBoundReached = getFirstVisibleRow() == 0;//边界处理
        boolean bottomBoundReached = getLastVisibleRow() >= maxRowCount;

        if (dy > 0) { // Contents are scrolling up
            //Check against bottom bound
            if (bottomBoundReached) {
                //If we've reached the last row, enforce limits
                int bottomOffset;
                if (rowOfIndex(getChildCount() - 1) >= (maxRowCount - 1)) {
                    //We are truly at the bottom, determine how far
                    bottomOffset = getVerticalSpace() - getDecoratedBottom(bottomView)
                            + getPaddingBottom();
                } else {
                /*
                 * Extra space added to account for allowing bottom space in the grid.
                 * This occurs when the overlap in the last row is not large enough to
                 * ensure that at least one element in that row isn't fully recycled.
                 */
                    bottomOffset = getVerticalSpace() - (getDecoratedBottom(bottomView)
                            + getDecoratedMeasuredHeight(bottomView)) + getPaddingBottom();
                }
                delta = Math.max(-dy, bottomOffset);
            } else {
                //No limits while the last row isn't visible
                delta = -dy;
            }
        } else { // Contents are scrolling down
            //Check against top bound
            if (topBoundReached) {
                int topOffset = -getDecoratedTop(topView) + getPaddingTop();
                delta = Math.min(-dy, topOffset);//下拉，dy 本来是负数，取- ，正数，所以
            } else {
                delta = -dy;
            }
        }

        offsetChildrenVertical(delta);//这里是先平移，再填充

        if (dy > 0) {
            if (getDecoratedBottom(topView) < 0 && !bottomBoundReached) {//第一个View移出屏幕 且没到底部 可见要+1
                //mFirstVisiblePosition++;
                fillGrid(DIRECTION_DOWN, recycler, state);

            } else if (!bottomBoundReached) {
                fillGrid(DIRECTION_NONE, recycler, state);
            }
        } else {
            if (getDecoratedTop(topView) > 0 && !topBoundReached) {//第一个View 离顶部有距离了，且没到顶部  可见要-1
                //mFirstVisiblePosition--;
                fillGrid(DIRECTION_UP, recycler, state);
            } else if (!topBoundReached) {
                fillGrid(DIRECTION_NONE, recycler, state);
            }
        }


        return -delta;
    }

    private void fillGrid(int direction, RecyclerView.Recycler recycler, RecyclerView.State state) {
        fillGrid(direction, 0, 0, recycler, state);
    }

    private void fillGrid(int direction, int emptyLeft, int emptyTop, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (mFirstVisiblePosition < 0) mFirstVisiblePosition = 0;//边界处理
        if (mFirstVisiblePosition >= getItemCount()) mFirstVisiblePosition = (getItemCount() - 1);


        //1 清点目前我们所有的视图。将他们 Detach 以便稍后重新连接。(主要还是for scroll，平移后 view移动了 )
        SparseArray<View> viewCache = new SparseArray<View>(getChildCount());
        int startLeftOffset = emptyLeft;
        int startTopOffset = emptyTop;

        if (getChildCount() != 0) {
            final View topView = getChildAt(0);
            startLeftOffset = getDecoratedLeft(topView);
            startTopOffset = getDecoratedTop(topView);
            switch (direction) {
/*                case DIRECTION_START:
                    startLeftOffset -= mDecoratedChildWidth;
                    break;
                case DIRECTION_END:
                    startLeftOffset += mDecoratedChildWidth;
                    break;*/
                case DIRECTION_UP:
                    startTopOffset -= getDecoratedMeasuredHeight(getChildAt(getChildCount() - 1));
                    break;
                case DIRECTION_DOWN:
                    startTopOffset += getDecoratedMeasuredHeight(topView);
                    break;
            }

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

                /*
         * Next, we advance the visible position based on the fill direction.
         * DIRECTION_NONE doesn't advance the position in any direction.
         */
        switch (direction) {
            case DIRECTION_START:
                mFirstVisiblePosition--;
                break;
            case DIRECTION_END:
                mFirstVisiblePosition++;
                break;
            case DIRECTION_UP:
                mFirstVisiblePosition -= getTotalColumnCount();
                break;
            case DIRECTION_DOWN:
                mFirstVisiblePosition += getTotalColumnCount();
                break;
        }


        //2 测量/布局每一个当前可见的子视图。重新连接已有的视图很简单； 新的视图是从 Recycler 之中获取的。
        int leftOffset = startLeftOffset;
        int topOffset = startTopOffset;
        int nextPosition = 0;
        for (int i = 0; i < getVisibleChildCount(); i++) {
            nextPosition = positionOfIndex(i);
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
                        leftOffset + getDecoratedMeasuredWidth(view),
                        topOffset + getDecoratedMeasuredHeight(view));
            } else {
                //Re-attach the cached view at its new index
                attachView(view);//将detach的View add回来
                viewCache.remove(nextPosition);
            }

            if (i % mVisibleColumnCount == (mVisibleColumnCount - 1)) {//换行
                leftOffset = startLeftOffset;
                topOffset += getDecoratedMeasuredHeight(view);


            } else {//增加左边距
                leftOffset += getDecoratedMeasuredWidth(view);
            }
        }
/*        //add by zhangxutong Feature1: 不同大小的Item也适应 add完以后看看是否没填满
        while (getVerticalSpace() > topOffset) {
            View additionalView = viewCache.get(++nextPosition);//取出缓存
            if (additionalView != null) {
                attachView(additionalView);
                viewCache.remove(nextPosition);
            } else {
                additionalView = recycler.getViewForPosition(nextPosition);
                addView(additionalView);
                measureChildWithMargins(additionalView, 0, 0);
                layoutDecoratedWithMargins(additionalView, leftOffset, topOffset,
                        leftOffset + getDecoratedMeasuredWidth(additionalView),
                        topOffset + getDecoratedMeasuredHeight(additionalView));
            }
            if (nextPosition % mVisibleColumnCount == (mVisibleColumnCount - 1)) {//换行
                leftOffset = startLeftOffset;
                topOffset += getDecoratedMeasuredHeight(additionalView);
            } else {//增加左边距
                leftOffset += getDecoratedMeasuredWidth(additionalView);
            }
        }*/


        for (int i = 0; i < viewCache.size(); i++) {
            recycler.recycleView(viewCache.valueAt(i));//detachView 后 没有attachView的话 就要真的回收掉他们
        }


    }

    /*
     * Rather than continuously checking how many views we can fit
     * based on scroll offsets, we simplify the math by computing the
     * visible grid as what will initially fit on screen, plus one.
     */
    // 计算出 一个屏幕的行列数
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
     * 第几行
     *
     * @param childIndex
     * @return
     */
    private int rowOfIndex(int childIndex) {
        int position = positionOfIndex(childIndex);

        return position / getTotalColumnCount();
    }

    /**
     * 可见的第一行,是第几行
     *
     * @return
     */
    private int getFirstVisibleRow() {
        return (mFirstVisiblePosition / getTotalColumnCount());
    }

    /**
     * 可见的最后一行，是第几行
     *
     * @return
     */
    private int getLastVisibleRow() {
        return getFirstVisibleRow() + mVisibleRowCount;
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
