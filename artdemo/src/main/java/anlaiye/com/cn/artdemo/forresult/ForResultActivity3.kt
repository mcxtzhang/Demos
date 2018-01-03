package anlaiye.com.cn.artdemo.forresult

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import anlaiye.com.cn.artdemo.R
import kotlinx.android.synthetic.main.activity_for_result3.*

class ForResultActivity3 : AppCompatActivity() {
    companion object {
        private val TAG = "页面C"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG, "onCreate: ");
        setContentView(R.layout.activity_for_result3)
        root.setOnClickListener{
            setResult(110)
            finish()
        }
    }


    override fun onPause() {
        Log.e(TAG, "onPause called");
        super.onPause()
    }

    override fun onResume() {
        Log.e(TAG, "onResume: ");
        super.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "onActivityResult: " + resultCode);
        super.onActivityResult(requestCode, resultCode, data)
    }
}
