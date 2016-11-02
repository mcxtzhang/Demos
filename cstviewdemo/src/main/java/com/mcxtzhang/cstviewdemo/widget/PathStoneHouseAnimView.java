package com.mcxtzhang.cstviewdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;

import com.mcxtzhang.cstviewdemo.outpututils.AnimUtils;
import com.mcxtzhang.cstviewdemo.widget.res.StoreHousePath;

import java.util.ArrayList;

/**
 * 介绍：一个路径动画的View
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/11/2.
 */

public class PathStoneHouseAnimView extends View {
    private Path mPath;
    private Path mDstPath;
    private Paint mPaint;

    public PathStoneHouseAnimView(Context context) {
        this(context, null);
    }

    public PathStoneHouseAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathStoneHouseAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);

        mPath = new Path();
        ArrayList<float[]> path = StoreHousePath.getPath("AnLaiYe"/*"Ultra PTR"*/);
        for (int i = 0; i < path.size(); i++) {
            float[] floats = path.get(i);
            mPath.moveTo(floats[0], floats[1]);
            mPath.lineTo(floats[2], floats[3]);
        }

        mDstPath = new Path();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimUtils.startAnim(PathStoneHouseAnimView.this, mPath, mDstPath);
            }
        });
    }


    private final static long MAX_LENGTH = 400;
    PathMeasure pathMeasure = new PathMeasure();
    Path stonePath;
    private ArrayList<Float> mPathLengthArray;
    private SparseArray<Boolean> mPathNeedAddArray;
    private int partIndex;//残缺的index
    private float partLength;//残缺部分的长度

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(20, 20);
        setBackgroundColor(Color.BLACK);
        mPaint.setColor(Color.GRAY);
        canvas.drawPath(mPath, mPaint);


        mPaint.setColor(Color.WHITE);
        //高仿StoneHouse效果 ,现在的做法很挫
        stonePath = new Path();

        mPathLengthArray = new ArrayList<>();//顺序存放path的length
        pathMeasure.setPath(mDstPath, false);
        while (pathMeasure.getLength() != 0) {
            mPathLengthArray.add(pathMeasure.getLength());
            pathMeasure.nextContour();
        }
        mPathNeedAddArray = new SparseArray<>();
        float totalLength = 0;
        partIndex = 0;
        partLength = 0;
        for (int i = mPathLengthArray.size() - 1; i >= 0; i--) {
            if (totalLength + mPathLengthArray.get(i) <= MAX_LENGTH) {//加上了也没满
                mPathNeedAddArray.put(i, true);
                totalLength = totalLength + mPathLengthArray.get(i);
            } else if (totalLength < MAX_LENGTH) {//加上了满了，但是不加就没满
                partIndex = i;
                partLength = MAX_LENGTH - totalLength;
                totalLength = totalLength + mPathLengthArray.get(i);
            }
        }

        pathMeasure.setPath(mDstPath, false);
        int i = 0;
        while (pathMeasure.getLength() != 0) {
            if (mPathNeedAddArray.get(i, false)) {
                pathMeasure.getSegment(0, pathMeasure.getLength(), stonePath, true);
            } else if (i == partIndex) {
                pathMeasure.getSegment(pathMeasure.getLength() - partLength, pathMeasure.getLength(), stonePath, true);
            }
            pathMeasure.nextContour();
            i++;
        }

        canvas.drawPath(stonePath, mPaint);
    }
}
