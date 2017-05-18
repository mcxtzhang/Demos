package com.mcxtzhang.github.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.ZRouter
import com.mcxtzhang.github.R


@ZRouter(path = "firstKotlin")
class FirstKotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_kotlin)

    }
}
