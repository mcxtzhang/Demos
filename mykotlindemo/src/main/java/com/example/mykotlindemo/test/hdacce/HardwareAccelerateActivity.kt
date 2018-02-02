package com.example.mykotlindemo.test.hdacce

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mykotlindemo.R
import kotlinx.android.synthetic.main.activity_hardware_accelerate.*

class HardwareAccelerateActivity : AppCompatActivity() {
    var time: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hardware_accelerate)

        button4.setOnClickListener { v ->
            if (System.currentTimeMillis()-time>1000){
                //Log.w("TAG", "invalidate() called")
                //v.invalidate()
                v.requestLayout()
            }
            time = System.currentTimeMillis()

        }
    }
}
