package com.mcxtzhang.cstviewdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.mcxtzhang.cstviewdemo.outpututils.PathAnimHelper;

/**
 * 介绍：一个路径动画的BaseView
 * 利用源Path绘制“底”
 * 利用动画Path 绘制 填充动画
 * <p>
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/11/2.
 */

public abstract class BasePathAnimView extends View {
    protected Path mSourcePath;//源Path
    protected Path mAnimPath;//用于绘制动画的Path
    protected Paint mPaint;
    protected PathAnimHelper mPathAnimHelper;//Path动画工具类

    public BasePathAnimView(Context context) {
        this(context, null);
    }

    public BasePathAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasePathAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * GET SET FUNC
     **/
    public Path getSourcePath() {
        return mSourcePath;
    }

    public BasePathAnimView setSourcePath(Path sourcePath) {
        mSourcePath = sourcePath;
        return this;
    }

    public Paint getPaint() {
        return mPaint;
    }

    public BasePathAnimView setPaint(Paint paint) {
        mPaint = paint;
        return this;
    }

    public PathAnimHelper getPathAnimHelper() {
        return mPathAnimHelper;
    }

    public BasePathAnimView setPathAnimHelper(PathAnimHelper pathAnimHelper) {
        mPathAnimHelper = pathAnimHelper;
        return this;
    }

    /**
     * INIT FUNC
     **/
    protected void init() {
        //Paint
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        //源路径 通过 abstract函数返回
        mSourcePath = initSourcePath();

        //动画路径只要初始化即可
        mAnimPath = new Path();

        //初始化动画帮助类
        mPathAnimHelper = new PathAnimHelper(this, mSourcePath, mAnimPath);
        //mPathAnimHelper = new PathAnimHelper(this, mSourcePath, mAnimPath, 1500, true);

    }

    /**
     * 返回需要做动画的SourcePath
     *
     * @return
     */
    protected abstract Path initSourcePath();

    /**
     * draw FUNC
     **/
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //平移
        canvas.translate(20, 20);
        setBackgroundColor(Color.BLACK);
        mPaint.setColor(Color.GRAY);
        canvas.drawPath(mSourcePath, mPaint);


        mPaint.setColor(Color.WHITE);
        canvas.drawPath(mAnimPath, mPaint);
    }

    /**
     * 设置动画 循环
     */
    public BasePathAnimView setAnimInfinite(boolean infinite) {
        mPathAnimHelper.setInfinite(infinite);
        return this;
    }

    /**
     * 设置动画 总时长
     */
    public BasePathAnimView setAnimTime(long animTime) {
        mPathAnimHelper.setAnimTime(animTime);
        return this;
    }

    /**
     * 执行循环动画
     */
    public void startAnim() {
        mPathAnimHelper.startAnim();
    }

    /**
     * 停止动画
     */
    public void stopAnim() {
        mPathAnimHelper.stopAnim();
    }

    /**
     * 重置
     */
    public void resetAnim() {
        stopAnim();
        mAnimPath.reset();
        mAnimPath.lineTo(0, 0);
        invalidate();
    }
}
