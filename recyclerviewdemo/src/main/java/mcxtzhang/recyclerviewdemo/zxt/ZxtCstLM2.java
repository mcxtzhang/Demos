package mcxtzhang.recyclerviewdemo.zxt;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * 介绍：换一种思路实现LM
 * 支持不同大小的View
 * 显示底端更多的，就从上往下排列，
 * 显示头部更多的，就从下往上排.
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/10/20.
 */

public class ZxtCstLM2 extends RecyclerView.LayoutManager {
    private final static int LAYOUT_FROM_TOP = 1;
    private final static int LAYOUT_FROM_BOTTOM = 2;

    private int mLayoutDirection;


    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private int mFirstVisiblePosition;//记录第一个可见的postion 不太好算啊
    private int mLastVisiblePosition;

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {

        detachAndScrapAttachedViews(recycler);

        mFirstVisiblePosition = 0;
        mLastVisiblePosition = getItemCount() - 1;

/*        int leftOffset = getPaddingLeft();
        int topOffset = getPaddingTop();*/
        mLayoutDirection = LAYOUT_FROM_TOP;
        fill(recycler, state);
    }

    private void fill(RecyclerView.Recycler recycler, RecyclerView.State state) {
        int topOffset = getPaddingTop();
        int leftOffset = getPaddingLeft();
        int bottomOffset = getBottomBorder();//

        //没有变化的View的下标 - View
        //SparseArray<View> unChangedIndexView = new SparseArray<View>();
        if (getChildCount() > 0) {
            //有子View topOffset终于找到了 感动到哭，遍历一遍找到基准的Top Bottom
            if (mLayoutDirection == LAYOUT_FROM_TOP) {
                View topView = getChildAt(0);
                topOffset = getDecoratedTop(topView);
                leftOffset = getDecoratedLeft(topView);
            } else {
                View bottomView = getChildAt(getChildCount() - 1);
                bottomOffset = getDecoratedBottom(bottomView);
                leftOffset = getDecoratedLeft(bottomView);
            }
            //先将屏幕上不显示的移除掉，并且累加offset
            int beforeRemoveFirstPosition = mFirstVisiblePosition;
            for (int i = getChildCount() - 1; i >= 0; i--) {
                //step1 recycle回收越界的View
                View child = getChildAt(i);
                //View下边超出上界 回收,isSHowDown true
                if (getDecoratedBottom(child) < getPaddingTop()) {
                    removeAndRecycleView(child, recycler);
                    mFirstVisiblePosition++;
                    topOffset += getDecoratedMeasuredHeight(child);
                    continue;
                }
                //View上边超过下界 回收,isshowDOwn false
                if (getDecoratedTop(child) > getBottomBorder()) {
                    removeAndRecycleView(child, recycler);
                    mLastVisiblePosition--;
                    bottomOffset -= getDecoratedMeasuredHeight(child);
                    continue;
                }
                //step2 将没越界的View不做任何处理
                //unChangedIndexView.put(beforeRemoveFirstPosition + i, child);
                //step3 为topOffset bottomOffset设置正确的值

            }
            detachAndScrapAttachedViews(recycler);
        }

        //理论上不会 不过还是做个边界修正
        if (mFirstVisiblePosition < 0) {
            mFirstVisiblePosition = 0;
        }
        if (mLastVisiblePosition > getItemCount() - 1) {
            mLastVisiblePosition = getItemCount() - 1;
        }

        if (mLayoutDirection == LAYOUT_FROM_TOP) {//从上往下显示
            mLastVisiblePosition = getItemCount() - 1;
            for (int i = mFirstVisiblePosition; i <= mLastVisiblePosition; i++) {
                View child /*= unChangedIndexView.get(i);
                if (child != null) {//如果是没变化的，不变

                }else { //否则是一个取出来 布局的过程
                    child */ = recycler.getViewForPosition(i);
                addView(child);
                measureChildWithMargins(child, 0, 0);
                layoutDecoratedWithMargins(child, leftOffset, topOffset, leftOffset + getDecoratedMeasuredWidth(child), topOffset + getDecoratedMeasuredHeight(child));
                //}
                topOffset += getDecoratedMeasuredHeight(child);
                if (/*getDecoratedBottom(child)*/topOffset > (getBottomBorder())) {
                    //下越界  不再add
                    mLastVisiblePosition = i;
                }
            }
        } else {//从下往上显示
            mFirstVisiblePosition = 0;//从下往上看，上面最小应该是0
            for (int i = mLastVisiblePosition; i >= mFirstVisiblePosition; i--) {
                View child /*= unChangedIndexView.get(i);
                if (child != null) {//如果是没变化的，不变

                }else {
                    //否则是一个取出来 布局的过程
                    child */ = recycler.getViewForPosition(i);
                addView(child, 0);
                measureChildWithMargins(child, 0, 0);
                layoutDecoratedWithMargins(child, leftOffset, bottomOffset - getDecoratedMeasuredHeight(child), leftOffset + getDecoratedMeasuredWidth(child), bottomOffset);
                //}
                bottomOffset -= getDecoratedMeasuredHeight(child);
                if (bottomOffset < getPaddingTop()) {//达到上届 不再add
                    mFirstVisiblePosition = i;
                }
            }
        }
        Log.d("TAG", "count= [" + getChildCount() + "]" + ",[recycler.getScrapList().size():" + recycler.getScrapList().size());


    }


    @Override
    public boolean canScrollVertically() {
        return true;
    }

    private int mVerticalScrollOffset = 0;//滑动了多少距离

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        //位移0、没有子View 当然不移动
        if (dy == 0 || getChildCount() == 0) {
            return 0;
        }

        int offset = dy;

        //边界处理 和滑动距离处理
        if (offset > 0) {
/*            View bottomView = getChildClosestToEnd();
            //最后一个子View隐藏起来的距离
            int bottomViewHeightHind = getDecoratedMeasuredHeight(bottomView) - (getDecoratedBottom(bottomView) - getBottomBorder());
            if (offset > bottomViewHeightHind) {//下边界 bottom , 拉的太大 要留白了
                Log.d("TAG", "scrollVerticallyBy() called with: offset = [" + offset + "], getDecoratedBottom(bottomView()) = [" + getDecoratedBottom(bottomView) + "], getBottomBorder() = [" + getBottomBorder() + "]");
                offset = bottomViewHeightHind;//这里还是正值
            }
            //如果是最后一个子View 而且没有隐藏的距离了
            if (mLastVisiblePosition == getItemCount() - 1 && bottomViewHeightHind >= 0) {
                offset = -bottomViewHeightHind;
            }*/
        } else {//,offset 负
            if (mVerticalScrollOffset + offset < 0) {//处理上边界 top
                offset = -mVerticalScrollOffset;//这里还是负值
            }
        }
         /*else if (mVerticalScrollOffset + offset > mTotalHeight - getVerticalSpace()) {
            offset = mTotalHeight - getVerticalSpace() - mVerticalScrollOffset;
        }*/

        offsetChildrenVertical(-offset);//平移childView,传入负值向上平移View，正值向下平移View
        mVerticalScrollOffset += offset;

        if (offset > 0) {
            mLayoutDirection = LAYOUT_FROM_TOP;
            fill(recycler, state);
        } else if (offset < 0) {
            mLayoutDirection = LAYOUT_FROM_BOTTOM;
            fill(recycler, state);
        }

        return offset;
    }

    /**
     * 抄源码的
     *
     * @return
     */
    private View getChildClosestToStart() {
        return getChildAt(false ? getChildCount() - 1 : 0);
    }

    private View getChildClosestToEnd() {
        /*return getChildAt(false ? 0 : getChildCount() - 1);*/
        return findViewByPosition(mLastVisiblePosition);
    }

    public int getVerticalSpace() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    public int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    /**
     * 返回下边界
     *
     * @return
     */
    public int getBottomBorder() {
        return getHeight() - getPaddingBottom();
    }
}
