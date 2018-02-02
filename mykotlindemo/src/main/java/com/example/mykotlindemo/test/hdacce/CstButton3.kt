package com.example.mykotlindemo.test.hdacce

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.widget.Button

/**
 * Created by zhangxutong on 2018/1/26.
 */
class CstButton3(context: Context?, attrs: AttributeSet?) : Button(context, attrs) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Log.i("TAG", "onMeasure() called");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        Log.i("TAG", "onLayout() called");
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        Log.i("TAG", "onDraw() called");
        super.onDraw(canvas)
    }
}