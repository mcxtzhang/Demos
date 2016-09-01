package com.mcxtzhang.spandemos;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.mcxtzhang.zxtcommonlib.ZxtUtils;

public class MainActivity extends AppCompatActivity {
    TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv = (TextView) findViewById(R.id.tv);

        //ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
        String source = "13816320845";//源数据
        SpannableString spannableString = new SpannableString(source);//构建Span
        String key = "184";
        if (!TextUtils.isEmpty(key)) {
            for (int i = 0; i < key.length(); i++) {
                int indexB = source.indexOf(key.charAt(i));
                if (indexB > -1) {//匹配到了,加一个spans
                    spannableString.setSpan(/*span*/new ForegroundColorSpan(Color.RED), indexB, indexB + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }

        mTv.setText(ZxtUtils.getFriendColorSpannableString(source,"adsfasdfadsf"));


    }

}
