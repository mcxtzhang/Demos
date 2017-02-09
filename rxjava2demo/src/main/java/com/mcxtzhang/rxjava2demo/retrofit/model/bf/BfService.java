package com.mcxtzhang.rxjava2demo.retrofit.model.bf;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/2/9.
 * History:
 */

public interface BfService {
    @POST("/breakfast/order/info?appid=1&token=a24074255a02eafbf9c11c11c258e8ed&appver=3.0.9&appplt=aph")
    Call<BaseBean> test1( @Body PostBean postBean);
}
