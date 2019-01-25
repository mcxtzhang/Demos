package com.mcxtzhang.github;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    LinearLayout ll;
    private String storeString;
    private String dotsString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ll = (LinearLayout) findViewById(R.id.rg);
        final EditText et = (EditText) findViewById(R.id.content_title);
        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("sub thread");
                        throw new RuntimeException("sub thread runtime exception");
                        //throw new Error("sub thread Errorn");
                    }
                }).start();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("main thread ");
            }
        });

    }

    public static CharSequence ellipsize(Editable text, EditText editText, int maxLength) {
        CharSequence charSeq = "";
        if (!TextUtils.isEmpty(text)) {
            charSeq = TextUtils.ellipsize(text, editText.getPaint(), maxLength, TextUtils.TruncateAt.END);
            charSeq = TextUtils.replace(charSeq, new String[]{"\u2026"}, new String[]{"..."});
        }
        return charSeq;
    }
}
