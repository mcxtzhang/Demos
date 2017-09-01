package com.mcxtzhang.github.web;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.mcxtzhang.github.R;

import static android.R.attr.name;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        //初始化控件
        Button btn1 = (Button) findViewById(R.id.btn1);
        final WebView wv = (WebView) findViewById(R.id.wv);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//参数 “javascript:”  +  js方法名
                wv.loadUrl("javascript:message()");
            }
        });

        Button btn2 = (Button) findViewById(R.id.btn2);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在android调用js有参的函数的时候参数要加单引号
                wv.loadUrl("javascript:message2('" + name + "')");
            }
        });


        //获取webSettings
        WebSettings settings = wv.getSettings();
        //让webView支持JS
        settings.setJavaScriptEnabled(true);

        wv.loadUrl("file:///android_asset/test.html");
        //第一个参数把自身传给js 第二个参数是this的一个名字
        //这个方法用于让H5调用android方法
        wv.addJavascriptInterface(new WebAppInterface(), "AndroidInterface");
    }

    public static class WebAppInterface{
        @JavascriptInterface
        public void printOrder(String orderId){
            Log.d("TAG", "printOrder() called with: orderId = [" + orderId + "]");
        }
    }

/*    //下面的两个方法是让网页来调的
    //这个注解必须加 因为 兼容问题
    @JavascriptInterface
    public void setMessage() {
        Toast.makeText(this, "我弹", Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void setMessage(String name) {
        Toast.makeText(this, "我弹弹" + name, Toast.LENGTH_SHORT).show();
    }*/
}
