package com.example.mykotlindemo

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by zhangxutong on 2017/12/27.
 */
class ForecastListAdapter(val datas: List<String>) : RecyclerView.Adapter<VH>() {
    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.text.text = datas[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(TextView(parent.context))
    }
}

class VH(val text: TextView) : RecyclerView.ViewHolder(text)
