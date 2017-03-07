package com.mcxtzhang.daggerdemo.chap3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mcxtzhang.daggerdemo.R;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity implements LoginConrtact.View {

    TextView mTv;

    @Inject
    LoginConrtact.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mTv = (TextView) findViewById(R.id.tvResult);


        DaggerLoginComponent.builder()
                .loginModule(new LoginModule("dd",this))
                .build()
        .inject(this);


        mPresenter.loadData();;
    }

    @Override
    public void showData() {
        mTv.setText("哟结果了 ！！！@##¥%………………");
    }
}
