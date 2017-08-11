package com.mcxtzhang.newwebdemoo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private WebView mWebView;
    private Button mBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtn = (Button) findViewById(R.id.btn);

        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        mWebView.addJavascriptInterface(this, "native_android");
        mWebView.loadUrl("http://m-starfood.imcoming.com/user/login?loginname=xxx&password=xxx");

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用JS
                mWebView.loadUrl("javascript:sum(3,8)");
            }
        });

    }

    //为了让JS调用Native
    @JavascriptInterface
    public void nativeToast(String message) {
        Toast.makeText(getApplicationContext(), "通过Natvie传递的Toast:" + message, Toast.LENGTH_LONG).show();
    }
}