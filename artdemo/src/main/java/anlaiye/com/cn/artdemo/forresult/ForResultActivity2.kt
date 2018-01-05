package anlaiye.com.cn.artdemo.forresult

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import anlaiye.com.cn.artdemo.R
import kotlinx.android.synthetic.main.activity_for_result2.*

class ForResultActivity2 : AppCompatActivity() {
    companion object {
        private val TAG = "页面B"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.w(TAG, "onCreate: ");
        setContentView(R.layout.activity_for_result2)
        root.setOnClickListener { 
            startActivity(Intent(this, ForResultActivity3::class.java))
            setResult(Activity.RESULT_OK)
            finish()
        }
    }


    override fun onPause() {
        Log.w(TAG, "onPause called");
        super.onPause()
    }

    override fun onResume() {
        Log.w(TAG, "onResume: ");
        super.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "onActivityResult: " + resultCode);
        super.onActivityResult(requestCode, resultCode, data)
    }
}
