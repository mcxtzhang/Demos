package anlaiye.com.cn.performancedemo.monitor.list

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.ImageView
import anlaiye.com.cn.performancedemo.R
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
    }


    fun initDatas(): List<ListDataBean> {
        val datas = ArrayList<ListDataBean>()
        datas.add(ListDataBean("http://imgs.ebrun.com/resources/2016_03/2016_03_25/201603259771458878793312_origin.jpg", "张"))
        datas.add(ListDataBean("http://p14.go007.com/2014_11_02_05/a03541088cce31b8_1.jpg", "旭童"))
        datas.add(ListDataBean("http://news.k618.cn/tech/201604/W020160407281077548026.jpg", "多种type"))
        datas.add(ListDataBean("http://www.kejik.com/image/1460343965520.jpg", "多种type"))
        datas.add(ListDataBean("http://cn.chinadaily.com.cn/img/attachement/jpg/site1/20160318/eca86bd77be61855f1b81c.jpg", "多种type"))
        datas.add(ListDataBean("http://imgs.ebrun.com/resources/2016_04/2016_04_12/201604124411460430531500.jpg", "多种type"))
        datas.add(ListDataBean("http://imgs.ebrun.com/resources/2016_04/2016_04_24/201604244971461460826484_origin.jpeg", "多种type"))
        datas.add(ListDataBean("http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg", "多种type"))
        return datas
    }
}
