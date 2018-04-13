package anlaiye.com.cn.artdemo.forresult

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
            setResult(99)
            finish()

            startActivity(Intent(this, ForResultActivity3::class.java))

            root.post {
            }

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

    override fun onDestroy() {
        Log.w(TAG, "onDestroy: ");

        super.onDestroy()
    }

    override fun onStop() {
        Log.w(TAG, "onStop: ");

        super.onStop()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "onActivityResult: " + resultCode);
        super.onActivityResult(requestCode, resultCode, data)
    }
}
