package com.example.mykotlindemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.example.mykotlindemo.caozuofu_1.Operation
import org.jetbrains.anko.find

class Main3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        val textView = find<TextView>(R.id.textView)
        textView.text = Operation("mcxtzhang")[1].toString()
    }
}
