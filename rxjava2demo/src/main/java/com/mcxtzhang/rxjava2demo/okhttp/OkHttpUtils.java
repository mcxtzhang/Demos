package com.mcxtzhang.rxjava2demo.okhttp;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/5/21.
 * History:
 */

public class OkHttpUtils {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    public static OkHttpClient getClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        //注意添加顺序 ，按顺序处理的
        //先判断有没有网
        //追加统一参数
        //统一处理返回
        //打印log
        //builder
        //.addInterceptor(new NoNetPreHandleInterceptor())
        //.addInterceptor(new PhpInterceptor())
        //.addInterceptor(new JavaInterceptor())
        //.addInterceptor(responseInterceptor);

        //Log信息拦截器

            // Log信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //设置 Debug Log 模式
            builder.addInterceptor(loggingInterceptor);



        //设置超时
        builder.connectTimeout(60, TimeUnit.SECONDS);
        builder.readTimeout(60, TimeUnit.SECONDS);
        builder.writeTimeout(60, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);


        return builder.build();
    }

}
