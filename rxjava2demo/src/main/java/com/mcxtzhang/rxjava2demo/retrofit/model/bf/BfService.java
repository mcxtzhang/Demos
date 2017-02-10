package com.mcxtzhang.rxjava2demo.retrofit.model.bf;

import com.mcxtzhang.rxjava2demo.retrofit.WxPayBean;

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

    String httpsParams = "{\"address\":\"提提\",\"address_id\":\"779950\",\"consignee\":\"洗香香\",\"consignee_tel\":\"18616328542\",\"floor_id\":\"102179\",\"gender\":0,\"notice_way\":1,\"order_list\":[{\"comment\":\"\",\"delivery_date\":\"20170211\",\"goods\":[{\"goods_sale_id\":\"3734\",\"number\":\"1\",\"price\":0.02}],\"notice_way\":1}],\"payway\":3,\"user_coupon_id\":\"-1\"}";

    String httpsCreateOrder = "https://breakfast.anlaiye.com.cn/breakfast/order/info";

    //https://breakfast.anlaiye.com.cn/breakfast/order/user/delete/60160908411710001?appid=1&token=71ba067a2106caaa9589a25dd252ebe4&appver=3.1.4&appplt=aph
    public static final String pathUrl = "/breakfast/order/info";

    @POST(pathUrl)
    Call<String> test1(@Body PostBean postBean);

    @POST(pathUrl)
    Call<String> test1WithJson(@Body RequestBody body);

    @POST
    Call<String> testWithAnnotationURL(@Url String url, @Body RequestBody body);


    //微信创建订单
    @POST
    Observable<BaseBean<WxPayBean>> testRxjava(@Url String url, @Body RequestBody body);

    //
    //微信创建订单
    @POST
    Observable<WxPayBean> testRxjavaNoBaseWrapper(@Url String url, @Body RequestBody body);

}
