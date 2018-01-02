package com.example.mykotlindemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.mykotlindemo.bianliangAndshuxing.JibenLeixing
import com.example.mykotlindemo.domain.Forecast
import com.example.mykotlindemo.domain.RequestForecastCommand
import com.example.mykotlindemo.pojo.PostBean
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class Main2Activity : AppCompatActivity() {

    private val datas = mutableListOf<String>(
            "zhangxutong1",
            "zhangxutong2",
            "zhangxutong3",
            "zhangxutong4",
            "zhangxutong5",
            "zhangxutong6",
            "zhangxutong7"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rv)
        //val forecastList = findViewById<RecyclerView>(R.id.forecastList)
        val forecastList: RecyclerView = find(R.id.forecastList)
        forecastList.layoutManager = LinearLayoutManager(this)
        forecastList.adapter = ForecastListAdapter(datas)

        val jibenLeixing = JibenLeixing()
        //Toast.makeText(this, "" + jibenLeixing.bitwistXor, Toast.LENGTH_SHORT).show()
        jibenLeixing.printS()

/*
        async() {
            val result = Request("http://gank.io/api/data/Android/10/1").run()

            uiThread {
                datas.add(result)
                forecastList.adapter.notifyDataSetChanged()
                longToast("Request performed")

            }
        }*/

        //数据类
        val post = PostBean("张", "secret")
        println(post)

        //复制一个数据类
        val post2 = post.copy(url = "baidu")
        println(post2)

        //映射对象到变量中
        val post3 = PostBean("whoField", "urlField")
        val (a, b) = post3
        println("who$a,url:$b")
        //这个映射对象的特性很强大，还可以用在Map上
        val map1 = HashMap<String, String>()
        map1.put("key1", "value1")
        map1.put("key2", "value2")
        map1.put("key3", "value3")
        map1.put("key4", "value4")
        map1.put("key5", "value5")
        for ((k, v) in map1) {
            println("Key:$k,value:$v")
        }


        //在UI中绘制数据
        doAsync {
            val result = RequestForecastCommand("524901").execute()
            uiThread {
                forecastList.adapter = ForecastListAdapter2(
                        result,
                        object : OnItemClickListener {
                            override fun invoke(forecast: Forecast) {
                                toast(forecast.toString())
                            }
                        }
                )
            }
        }

        //window.decorView.post(Runnable { startActivity(Intent(this, MainActivity::class.java)) })

    }


}
