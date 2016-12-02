package com.mcxtzhang.rxjavademo.async;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mcxtzhang.rxjavademo.R;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MockAsyncActivity extends AppCompatActivity {
    private Button mButton;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private static final String PIC_PATH = "https://www.baidu.com/img/bd_logo1.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_async);

        mButton = (Button) findViewById(R.id.button);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);

        mButton.setOnClickListener(v -> new DownloadUtils().loadPicByUrl(PIC_PATH).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onStart() {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCompleted() {
                        Toast.makeText(MockAsyncActivity.this, "下载成功了:", Toast.LENGTH_SHORT).show();
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MockAsyncActivity.this, "下载出错了:" + e, Toast.LENGTH_SHORT).show();
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        mImageView.setImageBitmap(bitmap);
                    }
                }
                )
        );
    }

}
