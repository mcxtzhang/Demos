package anlaiye.com.cn.artdemo.forresult

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import anlaiye.com.cn.artdemo.R
import kotlinx.android.synthetic.main.activity_for_result1.*

class ForResultActivity1 : AppCompatActivity() {
    companion object {
        private val TAG = "页面A"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(R.layout.activity_for_result1)
        root.setOnClickListener {
            startActivityForResult(Intent(this, ForResultActivity2::class.java), 100)
        }
    }

    override fun onPause() {
        Log.d(TAG, "onPause called");
        super.onPause()
    }

    override fun onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "onActivityResult: " + resultCode);
        super.onActivityResult(requestCode, resultCode, data)
    }
}
