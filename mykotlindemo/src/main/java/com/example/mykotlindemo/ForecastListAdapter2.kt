package com.example.mykotlindemo

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.mykotlindemo.domain.Forecast
import com.example.mykotlindemo.domain.ForecastList
import org.jetbrains.anko.find

/**
 * Created by zhangxutong on 2017/12/28.
 */
class ForecastListAdapter2(val datas: ForecastList, private val itemListener: OnItemClickListener) : RecyclerView.Adapter<ForecastListAdapter2.VH>() {
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(datas[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.ctx).inflate(R.layout.item_tem, parent, false), itemListener)
    }

    override fun getItemCount(): Int = datas.size()


    class VH(val item: View, val itemListener: OnItemClickListener) : RecyclerView.ViewHolder(item) {
        private val iconView: ImageView
        private val tvDate: TextView
        private val tvDesc: TextView
        private val tvTempHigh: TextView
        private val tvTempLow: TextView

        init {
            iconView = item.find(R.id.icon)
            tvDate = item.find(R.id.date)
            tvDesc = item.find(R.id.desc)
            tvTempHigh = item.find(R.id.temHigh)
            tvTempLow = item.find(R.id.tempLow)
        }

        fun bind(forecast: Forecast) {
            with(forecast) {
                Glide.with(item.ctx).load(icon).into(iconView)
                tvDate.text = "日期：$date"
                tvDesc.text = "描述$description"
                tvTempHigh.text = "最高温度:$high.toString()"
                tvTempLow.text = "最低温度:$low.toString()"
                item.setOnClickListener { itemListener(forecast) }
            }
        }
    }
}

interface OnItemClickListener {
    operator fun invoke(forecast: Forecast)
}

val View.ctx: Context
    get() = context