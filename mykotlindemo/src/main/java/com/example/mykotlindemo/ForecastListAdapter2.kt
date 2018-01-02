package com.example.mykotlindemo

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.mykotlindemo.domain.Forecast
import com.example.mykotlindemo.domain.ForecastList
import kotlinx.android.synthetic.main.item_tem.view.*

/**
 * Created by zhangxutong on 2017/12/28.
 */
class ForecastListAdapter2(val datas: ForecastList,
                           private val itemListener: (Forecast) -> Unit) : RecyclerView.Adapter<ForecastListAdapter2.VH>() {
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(datas[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.ctx).inflate(R.layout.item_tem, parent, false), itemListener)
    }

    override fun getItemCount(): Int = datas.size()


    class VH(val item: View, val itemListener: (Forecast) -> Unit) : RecyclerView.ViewHolder(item) {
        fun bind(forecast: Forecast) {
            with(forecast) {
                Glide.with(item.ctx).load(icon).into(item.ivIcon)
                item.tvDate.text = "日期：$date"
                item.tvDesc.text = "描述$description"
                item.tvTempHigh.text = "最高温度:$high.toString()"
                item.tvTempLow.text = "最低温度:$low.toString()"
                item.setOnClickListener { itemListener(forecast) }
            }
        }
    }
}


val View.ctx: Context
    get() = context