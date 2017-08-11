package com.mcxtzhang.newwebdemoo;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/8/11.
 * History:
 */

public class AlyWebView extends WebView {
    WebSettings mWebSettings;
    Context mContext;

    public AlyWebView(Context context) {
        super(context);
        init(context);
    }

    public AlyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AlyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        initSettings();
        setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

    }

    private void initSettings() {
        mWebSettings = getSettings();
//        mWebSettings.setUserAgentString(Env.userAgent);
        mWebSettings.setSupportZoom(false);
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setBuiltInZoomControls(false);
        mWebSettings.setDatabaseEnabled(true);
        String dir = mContext.getDir("database", Context.MODE_PRIVATE).getPath();
        mWebSettings.setDatabasePath(dir);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        addJavascriptInterface(new InJavaScriptLocalObj(), "GETHTML");
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);
    }
}
