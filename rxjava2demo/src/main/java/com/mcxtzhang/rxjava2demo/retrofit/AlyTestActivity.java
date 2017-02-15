package com.mcxtzhang.rxjava2demo.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mcxtzhang.rxjava2demo.R;
import com.mcxtzhang.rxjava2demo.retrofit.base.gson.AlyGsonConverterFactory;
import com.mcxtzhang.rxjava2demo.retrofit.base.removewrapper.rookie.RxHelper;
import com.mcxtzhang.rxjava2demo.retrofit.base.wrapper.BaseBean;
import com.mcxtzhang.rxjava2demo.retrofit.model.bf.BfService;
import com.mcxtzhang.rxjava2demo.retrofit.model.bf.PostBean;

import java.io.File;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

import static com.mcxtzhang.rxjava2demo.retrofit.model.bf.BfService.httpsCreateOrder;
import static com.mcxtzhang.rxjava2demo.retrofit.model.bf.BfService.httpsParams;

public class AlyTestActivity extends AppCompatActivity {

    private static final String TAG = "TAG";

    TextView tvResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aly_test);

        tvResult = (TextView) findViewById(R.id.tvResult);

        //header 追加统一参数
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Log.d(TAG, "header intercept() called with: chain = [" + chain + "]");
                Request original = chain.request();
                //append this to url
                HttpUrl url = original.url().newBuilder().addQueryParameter("appplt", "aph")
                        .addQueryParameter("appid", "1")
                        .addQueryParameter("appver", getAppVersion())
                        .addQueryParameter("token", Constant.loginToken).build();

                //append this to request header
                Request request = original.newBuilder()
                        .url(url)
/*                        .header("appplt", "aph")
                        .header("appid", "1")
                        .header("appver", getAppVersion())
                        .header("token", Constant.loginToken)
                        .method(original.method(), original.body())*/
                        .build();


                return chain.proceed(request);
            }
        };


        Interceptor responseIntercept = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Log.i(TAG, "request====111111111111111111111111111111");
                Log.i(TAG, "request header ====" + request.headers().toString());
                Response proceed = chain.proceed(request);
                Log.i(TAG, "Response proceed ====" + proceed.toString());
                int code = proceed.code();
                if (code != 200) {
                    Log.d(TAG, "code:" + code + ",不是200，说明有错误，我要改成200，否则Retrofit不让我通过。");
                    Response adapterResponse = proceed.newBuilder()
                            .code(200)
                            .build();
/*                            Response response422 = new Response.Builder()
                                    .code(200)
                                    .request(request)
                                    .headers(proceed.headers())
                                    .body(proceed.body())
                                    .protocol(proceed.protocol())
                                    .build();*/
                    return adapterResponse;
                }
                return proceed;
            }
        };


        //返回拦截器
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (true) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }
        //开启okhttp缓存
        File httpCacheDirectory = new File(getExternalCacheDir(), "zxtokhttpcache");
        builder.cache(new Cache(httpCacheDirectory,10 * 1024 * 1024));


        //注意添加顺序 ，按顺序处理的
        builder.addInterceptor(headerInterceptor)
                .addInterceptor(responseIntercept);
        OkHttpClient okHttpClient = builder.build();






        final String jsonBody = "{\"address\":\"gg\",\"address_id\":\"721866\",\"consignee\":\"gghh\",\"consignee_tel\":\"18616320845\",\"floor_id\":\"98448\",\"gender\":1,\"notice_way\":1,\"order_list\":[{\"comment\":\"\",\"delivery_date\":\"20161123\",\"goods\":[{\"goods_sale_id\":\"54\",\"number\":\"3\",\"price\":0.02},{\"goods_sale_id\":\"45\",\"number\":\"7\",\"price\":0.1}]}],\"payway\":3,\"user_coupon_id\":\"-1\"}";

        final PostBean postBean = new Gson().fromJson(jsonBody
                , PostBean.class);

        //"https://breakfast.anlaiye.com.cn"
        final String baseUrl = /*"http://breakfast.imcoming.com.cn"*/    "https://breakfast.anlaiye.com.cn";

        //自动剥离方法3： 构造一个特殊的gson (没学会)
        //Gson gson1 = (new GsonBuilder()).registerTypeAdapterFactory(new ItemTypeAdapterFactory()).create();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                //gson 自动解析实体
                //.addConverterFactory(GsonConverterFactory.create())

                //注册自定义的工厂类
                .addConverterFactory(AlyGsonConverterFactory.create())
                //自动剥离方法3： 构造一个特殊的gson (没学会)
                //.addConverterFactory(AlyGsonConverterFactory.create(gson1))

                //可以自动转成rxjava的Observable
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(okHttpClient)
                .build();

        final BfService movieService = retrofit.create(BfService.class);
        //Call<BaseBean> baseBeanCall = movieService.test1(postBean);


        final RequestBody body = RequestBody.create(MediaType.parse("application/json"), /*jsonBody.toString()*/httpsParams);
        //Call<String> baseBeanCall = movieService.test1WithJson(body);

        findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Call<String> baseBeanCall = movieService.testWithAnnotationURL(baseUrl + pathUrl, body);

                Observable<BaseBean<WxPayBean>> stringObservable = movieService.testRxjava(/*baseUrl + pathUrl*/ httpsCreateOrder, body);
/*                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());*/
                // gson工厂不剥离，但是compose 剥离
                stringObservable
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(RxHelper.<WxPayBean>helper())
                        .subscribe(new Observer<WxPayBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(WxPayBean value) {
                                Log.d(TAG, "gson工厂不剥离，但是compose 剥离 onNext() called with: value = [" + value + "]");
                                tvResult.setText(value.toString());
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "gson工厂不剥离，但是compose 剥离 onError() called with: e = [" + e + "]");
                                tvResult.setText(e.getMessage());
                            }

                            @Override
                            public void onComplete() {

                            }
                        });


                //1 Gson工厂不剥离，所以返回带BaseBean的
/*                stringObservable
                        .subscribe(new Observer<BaseBean<WxPayBean>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.d(TAG, "onSubscribe() called with: d = [" + d + "]");
                            }

                            @Override
                            public void onNext(BaseBean<WxPayBean> value) {
                                Log.d(TAG, "onNext() called with: value = [" + value + "]");
                                //((Button) findViewById(R.id.btnSend)).setText(value.getData().getOrderId());
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                Toast.makeText(AlyTestActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();

                                Log.d(TAG, "onError() called with: e = [" + throwable.getMessage() + "]");
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "onComplete() called");
                            }
                        });*/
                //1 Gson 工厂不剥离，返回带BaseBean 利用map剥离
/*                stringObservable
                        .map(new RemoveWrapper())
                        .subscribe(new Observer<WxPayBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.d(TAG, "onSubscribe() called with: d = [" + d + "]");
                            }

                            @Override
                            public void onNext(WxPayBean value) {
                                Log.d(TAG, "onNext() called with: value = [" + value + "]");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "onError() called with: e = [" + e + "]");
                                Toast.makeText(AlyTestActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "onComplete() called");
                            }
                        });*/


/*                Observable<WxPayBean> testRxjavaNoBaseWrapper = movieService.testRxjavaNoBaseWrapper(httpsCreateOrder, body);
                testRxjavaNoBaseWrapper.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<WxPayBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.d(TAG, "onSubscribe() called with: d = [" + d + "]");
                            }

                            @Override
                            public void onNext(WxPayBean value) {
                                Log.d(TAG, "onNext() called with: value = [" + value + "]");
                                ((Button) findViewById(R.id.btnSend)).setText(value.getMsg());
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "onError() called with: e = [" + e + "]   ,msg = [" + e.getMessage() + "]");
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "onComplete() called");
                            }
                        });*/


/*                baseBeanCall.enqueue(new Callback<String>() {
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
                });*/
            }
        });
    }


    protected static String getAppVersion() {
        return "3.10";
    }

}
