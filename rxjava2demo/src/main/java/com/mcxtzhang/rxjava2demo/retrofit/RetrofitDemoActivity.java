package com.mcxtzhang.rxjava2demo.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mcxtzhang.rxjava2demo.R;
import com.mcxtzhang.rxjava2demo.retrofit.model.MovieService;
import com.mcxtzhang.rxjava2demo.retrofit.model.bean.Base.HttpResult;
import com.mcxtzhang.rxjava2demo.retrofit.model.bean.DouBanMovieBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitDemoActivity extends AppCompatActivity {
    private static final String TAG = "zxt/RetrofitDemo";

    @Bind(R.id.click_me_BN)
    Button clickMeBN;
    @Bind(R.id.result_TV)
    TextView resultTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_demo);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.click_me_BN)
    public void onClick() {
        getMovie();
    }

    //进行网络请求
    private void getMovie() {
        String baseUrl = "https://api.douban.com/v2/movie/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                //gson 自动解析实体
                .addConverterFactory(GsonConverterFactory.create())
                //可以自动转成rxjava的Observable
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        MovieService movieService = retrofit.create(MovieService.class);
        // 1 原始模式
/*        Call<DouBanMovieBean> topMovieCall = movieService.getTopMovie(0, 10);
        topMovieCall.enqueue(new Callback<DouBanMovieBean>() {
            @Override
            public void onResponse(Call<DouBanMovieBean> call, Response<DouBanMovieBean> response) {
                DouBanMovieBean body = response.body();
                resultTV.setText(body.toString());
            }

            @Override
            public void onFailure(Call<DouBanMovieBean> call, Throwable t) {
                resultTV.setText(t.getMessage());
            }
        });*/

        //2 结合Rxjava
/*        movieService.getDoubanTopMovie(0, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RawMovieBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe() called with: d = [" + d + "]");
                    }

                    @Override
                    public void onNext(RawMovieBean value) {
                        Log.d(TAG, "onNext() called with: value = [" + value + "]");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError() called with: e = [" + e + "]");
                    }

                    @Override
                    public void onComplete() {

                        Log.d(TAG, "onComplete() called:" ;
                    }
                });*/

        //3 封装
        HttpUtil.getInstance().getDoubanTopMovie(0, 10)
                .subscribe(new Consumer<HttpResult<List<DouBanMovieBean>>>() {
                    @Override
                    public void accept(HttpResult<List<DouBanMovieBean>> listHttpResult) throws Exception {
                        resultTV.setText(listHttpResult.getData().get(0).getTitle());
                    }
                });
    }

}
