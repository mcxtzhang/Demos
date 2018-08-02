package com.example.mykotlindemo.test.hdacce

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView

/**
 * Created by zhangxutong on 2018/1/26.
 */
class CstButton(context: Context?, attrs: AttributeSet?) : TextView(context, attrs) {


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Log.d("TAG", "onMeasure() called");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        Log.d("TAG", "onLayout() called");
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        Log.d("TAG", "onDraw() called");
        super.onDraw(canvas)
    }
}