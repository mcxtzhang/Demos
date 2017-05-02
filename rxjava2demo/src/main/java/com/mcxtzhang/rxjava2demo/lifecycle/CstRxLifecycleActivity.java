package com.mcxtzhang.rxjava2demo.lifecycle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mcxtzhang.rxjava2demo.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class CstRxLifecycleActivity extends AppCompatActivity {

    private static final String TAG = "zxt/RxLifeCycle";

    CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cst_rx_lifecycle);

        mCompositeDisposable = new CompositeDisposable();

        final Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Log.e(TAG, "subscribe: 就算被取消了 也是会执行的吗？");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e3) {
                    e3.printStackTrace();
                }
                if (!e.isDisposed()) {
                    e.onNext("1");
                    e.onNext("2");
                    Log.e(TAG, "subscribe:  也是会执行的呀");
                    e.onComplete();
                }

            }
        }).subscribeOn(Schedulers.io());

        findViewById(R.id.btnOther).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                observable.subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.computation())
                        .compose(new ObservableTransformer<String, String>() {
                            @Override
                            public ObservableSource<String> apply(Observable<String> upstream) {
                                return upstream.takeUntil(new Observable<String>() {
                                    @Override
                                    protected void subscribeActual(final Observer<? super String> observer) {
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    Thread.sleep(3000);
                                                    observer.onNext("5");
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).start();


                                    }
                                });
                            }
                        })
                        .observeOn(Schedulers.io())
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.d(TAG, "onSubscribe() called with: d = [" + d + "]");
                            }

                            @Override
                            public void onNext(String value) {
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

        findViewById(R.id.btnIf).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                observable.subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.computation())
                        .compose(new ObservableTransformer<String, String>() {
                            @Override
                            public ObservableSource<String> apply(Observable<String> upstream) {
                                return upstream.takeUntil(new Predicate<String>() {
                                    @Override
                                    public boolean test(String s) throws Exception {
                                        return true;
                                    }
                                });
                            }
                        })
                        .observeOn(Schedulers.io())
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.d(TAG, "onSubscribe() called with: d = [" + d + "]");
                            }

                            @Override
                            public void onNext(String value) {
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


        findViewById(R.id.btnDispodeTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisposableObserver<String> disposableObserver = observable.subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.computation())
                        .compose(new ObservableTransformer<String, String>() {
                            @Override
                            public ObservableSource<String> apply(Observable<String> upstream) {
                                return upstream.takeUntil(new Predicate<String>() {
                                    @Override
                                    public boolean test(String s) throws Exception {
                                        return true;
                                    }
                                });
                            }
                        })
                        .observeOn(Schedulers.io())
                        .subscribeWith(new DisposableObserver<String>() {
                            @Override
                            public void onNext(String value) {
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

                mCompositeDisposable.add(disposableObserver);
                findViewById(R.id.btnDispode).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mCompositeDisposable.dispose();
                    }
                });
            }
        });


    }


/*    public <T> ObservableTransformer<T, T> bindUntilEvent(@NonNull final ActivityLifeCycleEvent event) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                Observable<ActivityLifeCycleEvent> compareLifecycleObservable =
                        lifecycleSubject.takeFirst(new Func1<ActivityLifeCycleEvent, Boolean>() {
                            @Override
                            public Boolean call(ActivityLifeCycleEvent activityLifeCycleEvent) {
                                return activityLifeCycleEvent.equals(event);
                            }
                        });
                return upstream.takeUntil(compareLifecycleObservable);
            }
        };
    }*/
}
