package com.mcxtzhang.rxjava2demo.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mcxtzhang.rxjava2demo.R;
import com.mcxtzhang.rxjava2demo.retrofit.model.bf.BaseBean;
import com.mcxtzhang.rxjava2demo.retrofit.model.bf.BfService;
import com.mcxtzhang.rxjava2demo.retrofit.model.bf.PostBean;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mcxtzhang.rxjava2demo.retrofit.model.bf.BfService.pathUrl;

public class AlyTestActivity extends AppCompatActivity {

    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aly_test);
        //拦截器
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Log.i(TAG, "request====111111111111111111111111111111");
                        Log.i(TAG, "request====" + request.headers().toString());
                        okhttp3.Response proceed = chain.proceed(request);
                        Log.i(TAG, "proceed====" + proceed.toString());
                        switch (proceed.code()) {
                            case 422:
                                Log.d(TAG, "操你大爷 老子睡觉了");
                        }
                        return proceed;
                    }
                })
                .build();







        final String jsonBody = "{\"address\":\"gg\",\"address_id\":\"721866\",\"consignee\":\"gghh\",\"consignee_tel\":\"18616320845\",\"floor_id\":\"98448\",\"gender\":1,\"notice_way\":1,\"order_list\":[{\"comment\":\"\",\"delivery_date\":\"20161123\",\"goods\":[{\"goods_sale_id\":\"54\",\"number\":\"3\",\"price\":0.02},{\"goods_sale_id\":\"45\",\"number\":\"7\",\"price\":0.1}]}],\"payway\":3,\"user_coupon_id\":\"-1\"}";

        final PostBean postBean = new Gson().fromJson(jsonBody
                , PostBean.class);

        final String baseUrl = "http://breakfast.imcoming.com.cn";
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                //gson 自动解析实体
                .addConverterFactory(GsonConverterFactory.create())
                //可以自动转成rxjava的Observable
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        final BfService movieService = retrofit.create(BfService.class);
        //Call<BaseBean> baseBeanCall = movieService.test1(postBean);
        final RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonBody.toString());
        //Call<String> baseBeanCall = movieService.test1WithJson(body);

        findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<String> baseBeanCall = movieService.testWithAnnotationURL(baseUrl + pathUrl, body);

                Observable<BaseBean<String>> stringObservable = movieService.testRxjava(baseUrl + pathUrl, body);
                stringObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<BaseBean<String>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.d(TAG, "onSubscribe() called with: d = [" + d + "]");
                            }

                            @Override
                            public void onNext(BaseBean<String> value) {
                                Log.d(TAG, "onNext() called with: value = [" + value + "]");
                            }

                            @Override
                            public void onError(Throwable throwable) {

                                Log.d(TAG, "onError() called with: e = [" + throwable.getMessage() + "]");
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "onComplete() called");
                            }
                        });

                baseBeanCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d("TAG", "2222222onResponse() called with: call = [" + call + "], response = [" + response + "]");
                        if (response.isSuccessful()) {
                            String body = response.body();
                            Log.d(TAG, "1111111111111111111onResponse() called with: body = [" + body);
                        } else {
                            ResponseBody responseBody = response.errorBody();
                            try {
                                Log.d(TAG, "11111111111111onResponse() error called with: responseBody = [" + responseBody.string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
                    }
                });
            }
        });
    }
}
