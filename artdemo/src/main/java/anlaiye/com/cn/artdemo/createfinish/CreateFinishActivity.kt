package anlaiye.com.cn.artdemo.createfinish

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import anlaiye.com.cn.artdemo.R

class CreateFinishActivity : AppCompatActivity() {
    companion object {
        val TAG: String = "TAG/CreateFinish"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate called");

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_finish)
    }

    override fun onPause() {
        Log.d(TAG, "onPause called");
        super.onPause()
    }

    override fun onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume()
    }

    override fun onStart() {
        Log.d(TAG, "onStart called");

        super.onStart()
        finish()

    }

    override fun onStop() {
        Log.d(TAG, "onStop called");

        super.onStop()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy called");

        super.onDestroy()
    }
}
