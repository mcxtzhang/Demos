package com.mcxtzhang.rxjava2demo.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/12/30.
 */

public interface MovieService {

    @GET("top250")
    Call<DouBanBean> getTopMovie();
}
