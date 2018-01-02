package com.example.mykotlindemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.mykotlindemo.bianliangAndshuxing.ShuXing
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        msg.text = "Hello kotlin!"

        val shuxing = ShuXing()
        shuxing.name = "name"
        println(shuxing.name)
        println(shuxing)
        println(shuxing.toString())

        val person = Persion("name", "surname")
        println(person)
        println(person.hashCode())
        println(person.toString())


        //myToast(shuxing.name)

        //lambda表达式

        msg.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                toast("msg被点击了")
            }
        })

        //我们定义了一个方法 所以必须用大括号包围， 然后在箭头的左边指定参数，右边返回结果值
        msg.setOnClickListener({ view -> toast("msg被点击了2") })

        //如果左边的参数没有使用 可以省略
        msg.setOnClickListener({ toast("省略了左边的参数") })

        //如果这个函数的最后一个参数是一个函数，可以把函数移动到圆括号外面：
        msg.setOnClickListener() { toast(("函数的最后一个参数是一个函数，可以把函数移动到圆括号外面")) }

        //如果这个函数只有一个参数，可以省略这个括号
        msg.setOnClickListener { toast("如果函数最后一个参数是一个函数，且只有这一个参数，可以省略圆括号") }

    }

}



