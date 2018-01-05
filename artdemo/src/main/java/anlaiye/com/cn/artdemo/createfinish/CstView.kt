package anlaiye.com.cn.artdemo.createfinish

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 * Created by zhangxutong on 2018/1/5.
 */
class CstView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    init {
        Log.d(TAG, "init() called");
    }

    companion object {
        val TAG: String = "TAG/CstView"
    }

    override fun onFinishInflate() {
        Log.d(TAG, "onFinishInflate() called");
        super.onFinishInflate()
    }

    override fun onAttachedToWindow() {
        Log.d(TAG, "onAttachedToWindow() called")
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        Log.d(TAG, "onDetachedFromWindow() called")

        super.onDetachedFromWindow()
    }
}