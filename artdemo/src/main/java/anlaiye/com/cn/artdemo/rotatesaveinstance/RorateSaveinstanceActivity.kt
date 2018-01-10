package anlaiye.com.cn.artdemo.rotatesaveinstance

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import anlaiye.com.cn.artdemo.R

class RorateSaveinstanceActivity : AppCompatActivity() {
    companion object {
        val TAG = "TAG/Saveinstance"
    }

    private lateinit var mValue1: String
    private lateinit var mValue2: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rorate_saveinstance)
        if (savedInstanceState == null) {
            mValue1 = intent.getStringExtra("key1")
            mValue2 = "value2";
        } else {

        }
        Log.d(TAG, "onCreate() mValue1:$mValue1, mValue2:$mValue2")
    }


}
