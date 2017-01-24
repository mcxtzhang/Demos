package com.mcxtzhang.rxjava2demo.retrofit.model.douban;

import com.mcxtzhang.rxjava2demo.retrofit.model.douban.bean.Base.HttpResult;
import com.mcxtzhang.rxjava2demo.retrofit.model.douban.bean.DouBanMovieBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/12/30.
 */

public interface MovieService {

    @GET("top250")
    Call<DouBanMovieBean> getTopMovie(@Query("start") int start, @Query("count") int count);

    //Observable<RawMovieBean>
    @GET("top250")
    Observable<HttpResult<List<DouBanMovieBean>>> getDoubanTopMovie(@Query("start") int start, @Query("count") int count);
}
