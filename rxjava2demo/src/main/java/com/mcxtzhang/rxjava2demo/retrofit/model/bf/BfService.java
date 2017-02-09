package com.mcxtzhang.rxjava2demo.retrofit.model.bf;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/2/9.
 * History:
 */

public interface BfService {
    public static final String pathUrl = "/breakfast/order/info?appid=1&token=a24074255a02eafbf9c11c11c258e8ed&appver=3.0.9&appplt=aph";
    @POST(pathUrl)
    Call<String> test1(@Body PostBean postBean);

    @POST(pathUrl)
    Call<String> test1WithJson(@Body RequestBody body);

    @POST
    Call<String> testWithAnnotationURL(@Url String url, @Body RequestBody body);


    @POST
    Observable<BaseBean<String>> testRxjava(@Url String url, @Body RequestBody body);
}
