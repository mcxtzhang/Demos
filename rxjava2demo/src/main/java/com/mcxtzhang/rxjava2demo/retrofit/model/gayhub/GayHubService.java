package com.mcxtzhang.rxjava2demo.retrofit.model.gayhub;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/1/24.
 * History:
 */

public interface GayHubService {
    @GET("/users/{user}/repos")
    Call<List<FullRepoBean>> getRepos1(@Path("user") String user);
}
