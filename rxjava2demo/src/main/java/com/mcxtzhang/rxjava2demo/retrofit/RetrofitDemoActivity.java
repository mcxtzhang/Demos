package com.mcxtzhang.rxjava2demo.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.mcxtzhang.rxjava2demo.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RetrofitDemoActivity extends AppCompatActivity {
    @Bind(R.id.click_me_BN)
    Button clickMeBN;
    @Bind(R.id.result_TV)
    TextView resultTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_demo);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.click_me_BN)
    public void onClick() {
        getMovie();
    }

    //进行网络请求
    private void getMovie() {

    }
}
