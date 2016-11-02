package com.mcxtzhang.cstviewdemo.outpututils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 介绍：一个自定义View的动画工具类
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/11/2.
 */

public class AnimUtils {

    /**
     * 一个SourcePath 内含多段Path，循环取出每段Path，并做一个动画,
     * 默认动画时间500ms,
     * 且动画会无限循环
     *
     * @param view       需要做动画的自定义View
     * @param sourcePath 源Path
     * @param animPath   自定义View用这个Path做动画
     */
    public static void startAnim(View view, Path sourcePath, Path animPath) {
        startAnim(view, sourcePath, animPath, 1500, true);
    }

    /**
     * 自定义动画的总时间
     * 一个SourcePath 内含多段Path，循环取出每段Path，并做一个动画
     *
     * @param view           需要做动画的自定义View
     * @param sourcePath     源Path
     * @param animPath       自定义View用这个Path做动画
     * @param totalDuaration 动画一共的时间
     */
    public static void startAnim(View view, Path sourcePath, Path animPath, long totalDuaration, boolean isInfinite) {
        if (view == null || sourcePath == null) {
            return;
        }
        if (animPath == null) {
            animPath = new Path();
        }
        PathMeasure pathMeasure = new PathMeasure();
        pathMeasure.setPath(sourcePath, false);
        //先重置一下需要显示动画的path
        animPath.reset();
        animPath.lineTo(0, 0);
        pathMeasure.setPath(sourcePath, false);
        //计算一下每一段的duration
        int count = 0;
        while (pathMeasure.getLength() != 0) {
            pathMeasure.nextContour();
            count++;
        }
        //经过上面这段计算duration代码的折腾 需要重新初始化pathMeasure
        pathMeasure.setPath(sourcePath, false);
        loopAnim(view, sourcePath, animPath, totalDuaration, pathMeasure, totalDuaration / count, isInfinite);
    }

    /**
     * 循环取出每一段path ，并执行动画
     *
     * @param animPath    自定义View用这个Path做动画
     * @param pathMeasure 用于测量的PathMeasure
     */
    private static void loopAnim(final View view, final Path sourcePath, final Path animPath, final long totalDuaration, final PathMeasure pathMeasure, final long duration, final boolean isInfinite) {
        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
/*                //19版本之前要想看到效果最简单的方法就是使用rLineTo(0,0)
                animPath.rLineTo(0, 0);*/
                //获取一个段落
                pathMeasure.getSegment(0, pathMeasure.getLength() * value, animPath, true);
                view.invalidate();
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //绘制完一条线之后，再绘制另外一条线
                pathMeasure.nextContour();
                if (pathMeasure.getLength() != 0) {
                    loopAnim(view, sourcePath, animPath, totalDuaration, pathMeasure, duration, isInfinite);
                } else {
                    if (isInfinite) {//无限的动画
                        startAnim(view, sourcePath, animPath, totalDuaration, isInfinite);
                    }
                }
                Log.e("TAG", "onAnimationEnd: ");
            }
        });
        animator.start();
    }

}
