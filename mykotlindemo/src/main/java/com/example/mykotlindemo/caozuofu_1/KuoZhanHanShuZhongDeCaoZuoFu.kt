package com.example.mykotlindemo.caozuofu_1

import android.view.View
import android.view.ViewGroup

/**
 * Created by zhangxutong on 2017/12/29.
 */
//可以像访问List一样简单的访问ViewGroup的View
operator fun ViewGroup.get(position: Int): View = getChildAt(position)