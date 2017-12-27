package com.example.mykotlindemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mykotlindemo.bianliangAndshuxing.ShuXing
import com.example.mykotlindemo.kuozhanhanshu.myToast
import kotlinx.android.synthetic.main.activity_main.*

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


        myToast(shuxing.name)

    }

}



