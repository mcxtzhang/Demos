package com.mcxtzhang.github;

import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
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
        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    et.setText(storeString);
                } else {
                    String NOW = et.getText().toString();
                    storeString = NOW;


                    //得到合适宽度：
                    Rect indexBounds = new Rect();//存放每个绘制的index的Rect区域
                    Paint paint = new Paint();
                    //传入像素
                    DisplayMetrics metrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(metrics);
                    paint.setTextSize(et.getTextSize());

                    paint.getTextBounds(NOW, 0, NOW.length(), indexBounds);//测量计算文字所在矩形，可以得到宽高
                    Log.d("TAG", "onCreate() called with: indexBounds.width() = [" + indexBounds.width() + "]");
                    Log.d("TAG", "onCreate() called with: et.getTextSize() * NOW.length() = [" + et.getTextSize() * NOW.length() + "]");
                    Log.d("TAG", "onCreate() called with: et.getWidth() = [" + et.getWidth() + "]");


                    if (NOW != null && et.getWidth() < indexBounds.width()) {


                        for (int i = 1; i < NOW.length(); i++) {
                            paint.getTextBounds(NOW, 0, i, indexBounds);//测量计算文字所在矩形，可以得到宽高
                            //if (indexBounds.width())
                        }


                        dotsString = NOW.substring(0, (int) (et.getWidth() / et.getTextSize())) + "...";

                        et.setText(dotsString);

                    }
                }
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
