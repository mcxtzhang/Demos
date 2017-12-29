package com.example.mykotlindemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.mykotlindemo.caozuofu_1.Operation
import com.example.mykotlindemo.caozuofu_1.get
import org.jetbrains.anko.find
import org.jetbrains.anko.toast

class Main3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        val textView = find<TextView>(R.id.textView)
        textView.text = Operation("mcxtzhang")[1].toString()

        val container = find<ViewGroup>(R.id.container)
        toast((container[2] as Button).text)
    }
}
