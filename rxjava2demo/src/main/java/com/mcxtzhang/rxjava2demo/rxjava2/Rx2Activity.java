package com.mcxtzhang.rxjava2demo.rxjava2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mcxtzhang.rxjava2demo.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class Rx2Activity extends AppCompatActivity {

    private static final String TAG = "zxt/Rx2";
    CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx2);
        mCompositeDisposable = new CompositeDisposable();

        findViewById(R.id.btnInterval).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable.interval(0, 1, TimeUnit.SECONDS)
                        .subscribe(new Observer<Long>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                mCompositeDisposable.add(d);
                                Log.d(TAG, "onSubscribe() called with: d = [" + d + "]");
                            }

                            @Override
                            public void onNext(Long value) {
                                Log.d(TAG, "onNext() called with: value = [" + value + "]");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "onError() called with: e = [" + e + "]");
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "onComplete() called");
                            }
                        });
            }
        });


        findViewById(R.id.btnLeak).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
/*                        for (int i = 0; ; i++) {
                            if (null != e && !e.isDisposed()) {
                                Log.e(TAG, "subscribe() called with: e = [" + e + "]" + Thread.currentThread());
                                e.onNext(i);
                            } else {
                                Log.e(TAG, "subscribe: break!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                                e.onComplete();
                                break;
                            }
                            //Thread.sleep(500);
                        }*/
                        e.onNext(5);
                        e.onError(new Exception("dadasdas"));

                    }
                }).subscribeOn(Schedulers.computation())
                        .observeOn(Schedulers.io())
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                Log.d(TAG, "accept() called with: integer = [" + integer + "]");
                            }}
                        ,new Consumer<Throwable>(){

                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        Log.d(TAG, "accept() called with: throwable = [" + throwable + "]");
                                    }
                                });
            }
        });

        findViewById(R.id.btnStop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCompositeDisposable.dispose();
            }
        });


/*        findViewById(R.id.btnLeak).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; ; i++) {
                            Log.d(TAG, "run() called");

                        }
                    }
                }).start();
            }
        });*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
        //mCompositeDisposable.clear();
        mCompositeDisposable.dispose();

    }
}
