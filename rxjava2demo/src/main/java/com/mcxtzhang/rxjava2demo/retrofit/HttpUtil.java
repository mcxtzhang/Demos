package com.mcxtzhang.rxjava2demo.retrofit;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mcxtzhang.rxjava2demo.retrofit.model.MovieService;
import com.mcxtzhang.rxjava2demo.retrofit.model.bean.Base.HttpResult;
import com.mcxtzhang.rxjava2demo.retrofit.model.bean.DouBanMovieBean;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2017/1/5.
 */

public class HttpUtil {
    public static final String BASE_URL = "https://api.douban.com/v2/movie/";
    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit mRetrofit;
    private MovieService mMovieService;

    private HttpUtil() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mMovieService = mRetrofit.create(MovieService.class);
    }

    private static class HttpUtilInner {
        private static final HttpUtil INSTANCE = new HttpUtil();
    }

    public static HttpUtil getInstance() {
        return HttpUtilInner.INSTANCE;
    }

    //暴漏的接口

    public Observable<HttpResult<List<DouBanMovieBean>>> getDoubanTopMovie(int start, int count) {
        return mMovieService.getDoubanTopMovie(start, count)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
