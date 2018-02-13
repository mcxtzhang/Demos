package anlaiye.com.cn.performancedemo.monitor.list

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.ImageView
import anlaiye.com.cn.performancedemo.R
import anlaiye.com.cn.performancedemo.monitor.framemetrics.ButterFactory
import com.bumptech.glide.Glide
import com.mcxtzhang.zxtcommonlib.recyclerview.CommonAdapter
import com.mcxtzhang.zxtcommonlib.recyclerview.ViewHolder
import kotlinx.android.synthetic.main.activity_rv.*
import java.util.*

class RvActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rv)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = object : CommonAdapter<ListDataBean>(this, R.layout.item_rv, initDatas()) {
            override fun convert(holder: ViewHolder?, data: ListDataBean?) {
                holder?.setText(R.id.tv, data?.name)
                Glide.with(holder?.itemView?.context).load(data?.url).into(holder?.getView<ImageView>(R.id.imageView))

            }

        }



        btnReport.setOnClickListener { _ ->
            //ActivityFrameMetrics.getInstance().report(this)
            ButterFactory.getFrameMonitor().report(this)

            ButterFactory.getInstantFpsMonitor().report(this)

        }
    }


    fun initDatas(): List<ListDataBean> {
        val datas = ArrayList<ListDataBean>()
        datas.add(ListDataBean("https://upload-images.jianshu.io/upload_images/595349-6ffdbb24d4945fa4.png", "Glide"))
        datas.add(ListDataBean("http://static.open-open.com/lib/uploadImg/20160909/20160909163002_510.jpg", "MVP"))
        datas.add(ListDataBean("http://static.open-open.com/lib/uploadImg/20160909/20160909161955_797.png", "ButterKnife"))
        datas.add(ListDataBean("http://static.open-open.com/lib/uploadImg/20160909/20160909161955_718.png", "Retrofit"))
        datas.add(ListDataBean("http://static.open-open.com/lib/uploadImg/20160909/20160909161956_36.png", "Picasso"))
        datas.add(ListDataBean("http://static.open-open.com/lib/uploadImg/20160909/20160909162002_610.jpg", "Realm"))
        datas.add(ListDataBean("http://static.open-open.com/lib/uploadImg/20160909/20160909162002_447.jpg", "RxJava"))
        datas.add(ListDataBean("https://gss1.bdstatic.com/9vo3dSag_xI4khGkpoWK1HF6hhy/baike/w%3D268/sign=818bbcabfcf2b211e42e8248f2826511/9a504fc2d5628535746e08f997ef76c6a6ef6358.jpg", "kotlin"))
        datas.add(ListDataBean("https://gss0.bdstatic.com/-4o3dSag_xI4khGkpoWK1HF6hhy/baike/w%3D268/sign=f5c6e8806959252da3171a020c9a032c/0df431adcbef76096709930527dda3cc7cd99e2d.jpg", "Java"))
        datas.add(ListDataBean("https://gss1.bdstatic.com/9vo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=3b03c837572c11dfcadcb771024e09b5/ae51f3deb48f8c54cd34cafb3a292df5e1fe7f7a.jpg", "Android"))
        return datas
    }
}
