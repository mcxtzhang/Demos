package com.mcxtzhang.rxjava2demo.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.mcxtzhang.rxjava2demo.R;
import com.mcxtzhang.rxjava2demo.retrofit.model.bf.BaseBean;
import com.mcxtzhang.rxjava2demo.retrofit.model.bf.BfService;
import com.mcxtzhang.rxjava2demo.retrofit.model.bf.PostBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AlyTestActivity extends AppCompatActivity {

    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aly_test);


        final PostBean postBean = new Gson().fromJson("{\"address\":\"gg\",\"address_id\":\"721866\",\"consignee\":\"gghh\",\"consignee_tel\":\"18616320845\",\"floor_id\":\"98448\",\"gender\":1,\"notice_way\":1,\"order_list\":[{\"comment\":\"\",\"delivery_date\":\"20161123\",\"goods\":[{\"goods_sale_id\":\"54\",\"number\":\"3\",\"price\":0.02},{\"goods_sale_id\":\"45\",\"number\":\"7\",\"price\":0.1}]}],\"payway\":3,\"user_coupon_id\":\"-1\"}"
                , PostBean.class);

        findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String baseUrl = "http://breakfast.imcoming.com.cn";
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        //gson 自动解析实体
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                BfService movieService = retrofit.create(BfService.class);
                Call<BaseBean> baseBeanCall = movieService.test1(postBean);
                baseBeanCall.enqueue(new Callback<BaseBean>() {
                    @Override
                    public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                        Log.d("TAG", "onResponse() called with: call = [" + call + "], response = [" + response + "]");
                        BaseBean body = response.body();
                        Log.d(TAG, "onResponse() called with: body = [" + body );
                    }

                    @Override
                    public void onFailure(Call<BaseBean> call, Throwable t) {
                        Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
                    }
                });
            }
        });
    }
}
