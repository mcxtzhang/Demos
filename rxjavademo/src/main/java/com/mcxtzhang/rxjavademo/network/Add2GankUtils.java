package com.mcxtzhang.rxjavademo.network;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;

/**
 * 介绍：使用okhttp访问网络
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/12/2.
 */

public class Add2GankUtils {
    public final String POST_URL = "https://gank.io/api/add2gank";

    public static final String CREATE_CLASS_URL = "http://relation.imcoming.com.cn/v1/school/grade/create?appid=1&token=0a46e99862386f369806b373924b2ccf&appver=3.1.0&appplt=aph";

    private OkHttpClient mOkHttpClient;

    public Add2GankUtils() {
        mOkHttpClient = new OkHttpClient();
    }

/*    public Observable<String> login(String userName, String pwd) {

    }*/


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public Observable<String> add2Gank(Add2GankBean add2GankBean) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    FormEncodingBuilder builder = new FormEncodingBuilder();
                    builder.add("url", add2GankBean.getUrl());
                    builder.add("desc", add2GankBean.getDesc());
                    builder.add("who", add2GankBean.getWho());
                    builder.add("type", add2GankBean.getType());
                    builder.add("debug", "true");



                    RequestBody body = RequestBody.create(JSON, new Gson().toJson(new TestAlyCreateClassBean()));

                    Request request = new Request.Builder()
                            .url(CREATE_CLASS_URL)
                            .post(/*builder.build()*/body )
                            .build();

                    mOkHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            subscriber.onError(e);
                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            if (response.isSuccessful()) {
                                subscriber.onNext(response.body().string());
                                subscriber.onCompleted();
                            } else {
                                subscriber.onError(new Exception(response.message()));
                            }
                        }
                    });
                }
            }
        });
    }

}
