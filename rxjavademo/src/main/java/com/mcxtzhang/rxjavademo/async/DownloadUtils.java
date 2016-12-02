package com.mcxtzhang.rxjavademo.async;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/12/2.
 */

public class DownloadUtils {
    private OkHttpClient mOkHttpClient;

    public DownloadUtils() {
        mOkHttpClient = new OkHttpClient();
    }

    public Observable<Bitmap> loadPicByUrl(String url) {
        return Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    //在这里请求图片数据
                    Request request = new Request.Builder().url(url).build();
                    mOkHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            subscriber.onError(e);
                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            if (response.isSuccessful()) {
                                byte[] bytes = response.body().bytes();
                                subscriber.onNext(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                                subscriber.onCompleted();
                            } else {
                                subscriber.onError(new IOException(response.message()));
                            }
                        }
                    });
                }
            }
        });
    }
}
