package com.example.mykotlindemo

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import com.example.mykotlindemo.domain.ForecastList

/**
 * Created by zhangxutong on 2017/12/28.
 */
class ForecastListAdapter2(val datas: ForecastList) : RecyclerView.Adapter<ForecastListAdapter2.VH>() {
    override fun onBindViewHolder(holder: VH, position: Int) {
        with(datas.dailyForecast[position]) {
            holder.tv.text = "$date - $description $high/$low"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = VH(TextView(parent.context))

    override fun getItemCount(): Int = datas.dailyForecast.size


    class VH(val tv: TextView) : RecyclerView.ViewHolder(tv)
}