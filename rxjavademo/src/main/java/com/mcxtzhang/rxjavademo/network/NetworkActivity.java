package com.mcxtzhang.rxjavademo.network;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.mcxtzhang.rxjavademo.R;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NetworkActivity extends AppCompatActivity {
    private EditText mEtId, mEtDesc, mEtType;
    private CheckBox mCb;
    private Button mButton;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

        mEtId = (EditText) findViewById(R.id.etId);
        mEtDesc = (EditText) findViewById(R.id.etDesc);
        mEtType = (EditText) findViewById(R.id.editText);
        mCb = (CheckBox) findViewById(R.id.checkBox);

        mButton = (Button) findViewById(R.id.btnCommit);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("提交中...");
        mButton.setOnClickListener(v -> {
            Add2GankBean add2GankBean = new Add2GankBean();
            add2GankBean
                    .setUrl("http://my.csdn.net/?ref=toolbar_logo")
                    .setDesc(mEtDesc.getText().toString().trim())
                    .setWho(mEtId.getText().toString().trim())
                    .setType("Android")
                    .setDebug(mCb.isChecked());


            new Add2GankUtils().add2Gank(add2GankBean)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<String>() {
                        public static final String TAG = "zxt";

                        @Override
                        public void onStart() {
                            super.onStart();
                            mProgressDialog.show();
                        }

                        @Override
                        public void onCompleted() {
                            mProgressDialog.hide();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "onError() called with: e = [" + e + "]");
                            Toast.makeText(NetworkActivity.this, "Error:" + e, Toast.LENGTH_SHORT).show();
                            mProgressDialog.hide();
                        }

                        @Override
                        public void onNext(String s) {
                            try {
                                String msg = new JSONObject(s).optString("msg");
                                Log.e(TAG, "onNext()  error called with: s = [" + msg + "]");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.d(TAG, "onNext() called with: s = [" + s + "]");
                        }
                    });
        });

    }
}
