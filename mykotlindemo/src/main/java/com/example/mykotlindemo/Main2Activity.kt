package com.example.mykotlindemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.mykotlindemo.Request.Request
import com.example.mykotlindemo.bianliangAndshuxing.JibenLeixing
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.find
import org.jetbrains.anko.longToast
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


        async() {
            val result = Request("http://gank.io/api/data/Android/10/1").run()

            uiThread {
                datas.add(result)
                forecastList.adapter.notifyDataSetChanged()
                longToast("Request performed")

            }
        }

    }


}
