package com.mcxtzhang.rxjava2demo.rxjava2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mcxtzhang.rxjava2demo.R;

import java.util.EmptyStackException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
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
                                       }
                                   }
                                , new Consumer<Throwable>() {

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

                Observable.just(1, 2, 3, 4)
                        .filter(new Predicate<Integer>() {
                            @Override
                            public boolean test(Integer integer) throws Exception {
                                return false;
                            }
                        });
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

        findViewById(R.id.btnZipDemo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable<String> ob1 = Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        e.onError(new EmptyStackException());
                    }
                });
                Observable<String> ob2 = Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        e.onNext("能收到吗？");
                        e.onNext("我猜你能收到吧？");
                        e.onComplete();
                    }
                });
                Observable.zip(ob1, ob2, new BiFunction<String, String, String>() {
                    @Override
                    public String apply(String s, String s2) throws Exception {
                        return s + s2;
                    }
                }).subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(String value) {
                                Log.d(TAG, "onNext() called with: value = [" + value + "]");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "onError: " + e);
                            }

                            @Override
                            public void onComplete() {

                            }
                        });

            }
        });

        deferValue = 1;
        final Observable<Integer> o1 = /*Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(deferValue);
                e.onComplete();
            }
        })*/Observable.fromArray(deferValue).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        final Observable<Integer> o2 = Observable.defer(new Callable<ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> call() throws Exception {
                return Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        e.onNext(deferValue);
                        e.onComplete();
                    }
                });
            }
        }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        findViewById(R.id.btnDefer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deferValue += 1;
                o1.subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept() called with: integer = [" + integer + "]");
                    }
                });
                o2.subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept() called with: integer = [" + integer + "]");
                    }
                });


            }
        });

        findViewById(R.id.btnRangeRepeat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable.range(5, 9).repeat(2).subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept() called with: integer = [" + integer + "]");
                    }
                });
            }
        });


    }

    private int deferValue, value;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
        //mCompositeDisposable.clear();
        mCompositeDisposable.dispose();

    }
}
