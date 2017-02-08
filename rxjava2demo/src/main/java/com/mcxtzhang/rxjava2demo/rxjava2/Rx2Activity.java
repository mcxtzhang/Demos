package com.mcxtzhang.rxjava2demo.rxjava2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mcxtzhang.rxjava2demo.R;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.Subject;

public class Rx2Activity extends AppCompatActivity {

    private static final String TAG = "zxt/Rx2";

    //▲ 统一管理 Disposable，统一注销
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
                        e.onError(new Exception("abc"));

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
                Observable<String> zip = Observable.zip(ob1, ob2, new BiFunction<String, String, String>() {
                    @Override
                    public String apply(String s, String s2) throws Exception {
                        return s + s2;
                    }
                });

                Single<List<String>> collect = ob1.flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        return Observable.just(s);
                    }
                }).filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return true;
                    }
                }).collect(new Callable<List<String>>() {
                    @Override
                    public List<String> call() throws Exception {
                        return new ArrayList<String>();
                    }
                }, new BiConsumer<List<String>, String>() {
                    @Override
                    public void accept(List<String> strings, String s) throws Exception {
                        strings.add(s);
                    }
                });

                Single.zip(Single.fromObservable(zip), collect, new BiFunction<String, List<String>, String>() {
                    @Override
                    public String apply(String s, List<String> strings) throws Exception {

                        return s;
                    }
                }).subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.d(TAG, "onSubscribe() called with: d = [" + d + "]");
                            }

                            @Override
                            public void onSuccess(String value) {
                                Log.d(TAG, "onSuccess() called with: value = [" + value + "]");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "onError() called with: e = [" + e + "]");
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

        findViewById(R.id.btnSubject).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReplaySubject<String> subject = ReplaySubject.create();
                String a = "一开始没变化之前";

                subject.onNext("a");
                Log.d(TAG, "onClick() called with: view = [" + subject.getValues() + "]");
                subject.onNext("b");
                subject.onNext(a);
                Log.d(TAG, "onClick() called with: view = [" + subject.getValues() + "]");
                subject.onComplete();
                a = "厉害了";

                subject.subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                        Log.d(TAG, "onSubscribe() called with: d = [" + d + "]" + Thread.currentThread());
                    }

                    @Override
                    public void onNext(String value) {
                        Log.d(TAG, "onNext() called with: value = [" + value + "]" + Thread.currentThread());
                        mCompositeDisposable.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError() called with: e = [" + e + "]" + Thread.currentThread());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete() called" + Thread.currentThread());
                    }
                });
            }
        });

        findViewById(R.id.btnFlatmap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query("查询")
                        .flatMap(new Function<List<String>, ObservableSource<String>>() {
                            @Override
                            public ObservableSource<String> apply(List<String> strings) throws Exception {
                                return Observable.fromIterable(strings);
                            }
                        })
                        .flatMap(new Function<String, ObservableSource<String>>() {
                            @Override
                            public ObservableSource<String> apply(String s) throws Exception {
                                return getTitle(s);
                            }
                        })

                        .subscribeOn(Schedulers.io())

                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                Log.d(TAG, "doOnNext() called with: s = [" + s + "]" + Thread.currentThread());
                            }
                        })
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                Log.d(TAG, "subscribe() called with: s = [" + s + "]" + Thread.currentThread());
                            }
                        });

            }
        });


        findViewById(R.id.btnTimer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable.timer(1, TimeUnit.SECONDS)
                        .subscribe(new Observer<Long>() {
                            @Override
                            public void onSubscribe(Disposable d) {
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

        final Observable<Integer> testTakeOb = Observable.just(1, 2, 3, 4, 5, 6, 7, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        final Observer<Integer> testTaskObserver = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.w(TAG, "onSubscribe() called with: d = [" + d + "]");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "onNext() called with: value = [" + value + "]");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError() called with: e = [" + e + "]");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete() called");
            }
        };

        findViewById(R.id.btnTakeUntil).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testTakeOb
                        .takeUntil(new Predicate<Integer>() {
                            @Override
                            public boolean test(Integer integer) throws Exception {
                                Log.i(TAG, "takeUntil() called with: integer = [" + integer + "]");
                                return integer > 2;
                            }
                        })
                        .subscribe(testTaskObserver);

            }
        });

        findViewById(R.id.btnTakeWhile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testTakeOb.takeWhile(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        Log.d(TAG, "takeWhile() called with: integer = [" + integer + "]");
                        return integer < 2;
                    }
                }).subscribe(testTaskObserver);


                Observable.just(1, 2, 3, 2, 1)
                        .takeUntil(new Predicate<Integer>() {
                            @Override
                            public boolean test(Integer integer) throws Exception {
                                Log.d(TAG, "test() called with: integer = [" + integer + "]");
                                return integer > 1;
                            }
                        }).subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe() called with: d = [" + d + "]");
                    }

                    @Override
                    public void onNext(Integer value) {
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


        final DisposableObserver observer = new DisposableObserver() {
            @Override
            public void onNext(Object value) {
                Log.v(TAG, "onNext() called with: value = [" + value + "]");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError() called with: e = [" + e + "]");
            }

            @Override
            public void onComplete() {
                Log.w(TAG, "onComplete() called");
            }
        };


        findViewById(R.id.btnReduce).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                Observable.just(4, 2, 3, 4, 5, 7)
                        .reduce(new BiFunction<Integer, Integer, Integer>() {
                            @Override
                            public Integer apply(Integer integer, Integer integer2) throws Exception {
                                Log.i(TAG, "apply() called with: integer = [" + integer + "], integer2 = [" + integer2 + "]");
                                return integer>integer2?integer:integer2;
                            }
                        })
                        .subscribeWith(new DisposableMaybeObserver<Integer>() {
                            @Override
                            public void onSuccess(Integer value) {
                                Log.d(TAG, "onSuccess() called with: value = [" + value + "]");
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
//                01-12 22:29:30.879 27202-27202/com.mcxtzhang.rxjava2demo I/zxt/Rx2: apply() called with: integer = [4], integer2 = [2]
//                01-12 22:29:30.879 27202-27202/com.mcxtzhang.rxjava2demo I/zxt/Rx2: apply() called with: integer = [4], integer2 = [3]
//                01-12 22:29:30.879 27202-27202/com.mcxtzhang.rxjava2demo I/zxt/Rx2: apply() called with: integer = [4], integer2 = [4]
//                01-12 22:29:30.879 27202-27202/com.mcxtzhang.rxjava2demo I/zxt/Rx2: apply() called with: integer = [4], integer2 = [5]
//                01-12 22:29:30.879 27202-27202/com.mcxtzhang.rxjava2demo I/zxt/Rx2: apply() called with: integer = [5], integer2 = [7]
//                01-12 22:29:30.885 27202-27202/com.mcxtzhang.rxjava2demo D/zxt/Rx2: onSuccess() called with: value = [7]*/


                DisposableMaybeObserver<Integer> maybeObserver = new DisposableMaybeObserver<Integer>() {
                    @Override
                    public void onSuccess(Integer value) {
                        Log.d(TAG, "onSuccess() called with: value = [" + value + "]");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError() called with: e = [" + e + "]");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete() called");
                    }
                };
                Observable.just(4, 2, 3, 4, 5, 7).reduce("a", new BiFunction<String, Integer, String>() {
                    @Override
                    public String apply(String s, Integer integer) throws Exception {
                        Log.d(TAG, "apply() called with: s = [" + s + "], integer = [" + integer + "]");
                        return s + integer;
                    }
                }).subscribeWith(new DisposableSingleObserver<String>() {
                    @Override
                    public void onSuccess(String value) {
                        Log.d(TAG, "onSuccess() called with: value = [" + value + "]");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError() called with: e = [" + e + "]");
                    }
                });

//                01-12 22:35:17.808 28076-28076/com.mcxtzhang.rxjava2demo D/zxt/Rx2: apply() called with: s = [a], integer = [4]
//                01-12 22:35:17.808 28076-28076/com.mcxtzhang.rxjava2demo D/zxt/Rx2: apply() called with: s = [a4], integer = [2]
//                01-12 22:35:17.808 28076-28076/com.mcxtzhang.rxjava2demo D/zxt/Rx2: apply() called with: s = [a42], integer = [3]
//                01-12 22:35:17.808 28076-28076/com.mcxtzhang.rxjava2demo D/zxt/Rx2: apply() called with: s = [a423], integer = [4]
//                01-12 22:35:17.809 28076-28076/com.mcxtzhang.rxjava2demo D/zxt/Rx2: apply() called with: s = [a4234], integer = [5]
//                01-12 22:35:17.809 28076-28076/com.mcxtzhang.rxjava2demo D/zxt/Rx2: apply() called with: s = [a42345], integer = [7]
//                01-12 22:35:17.809 28076-28076/com.mcxtzhang.rxjava2demo D/zxt/Rx2: onSuccess() called with: value = [a423457]


            }
        });

        findViewById(R.id.btnToMap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //即使不指定用新的map 订阅两次 也是两个hashmap对象
                Single<Map<Integer, String>> mapSingle = Observable.just(1, 2, 3, 4, 5)
                        .toMap(new Function<Integer, Integer>() {
                            @Override
                            public Integer apply(Integer integer) throws Exception {

                                return integer;
                            }
                        }, new Function<Integer, String>() {
                            @Override
                            public String apply(Integer integer) throws Exception {

                                return "" + integer;
                            }
                        }/*, new Callable<Map<Integer, String>>() {
                            @Override
                            public Map<Integer, String> call() throws Exception {
                                return new HashMap<Integer, String>();
                            }
                        }*/);

                Single<Map<Integer, String>> mapSingle1 = Subject.just(1, 2, 3, 4, 5, 6, 7)
                        .toMap(new Function<Integer, Integer>() {
                            @Override
                            public Integer apply(Integer integer) throws Exception {
                                Log.e(TAG, "apply() called with: integer = [" + integer + "]");
                                return integer;
                            }
                        }, new Function<Integer, String>() {
                            @Override
                            public String apply(Integer integer) throws Exception {
                                Log.w(TAG, "apply() called with: integer = [" + integer + "]");
                                return "" + integer;
                            }
                        });


                mapSingle1.subscribeWith(new DisposableSingleObserver<Map<Integer, String>>() {
                    @Override
                    public void onSuccess(Map<Integer, String> value) {
                        Log.d(TAG, "onSuccess() called with: value = [" + value + "]");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError() called with: e = [" + e + "]");
                    }
                });

                mapSingle1.subscribeWith(new DisposableSingleObserver<Map<Integer, String>>() {
                    @Override
                    public void onSuccess(Map<Integer, String> value) {
                        Log.d(TAG, "onSuccess() called with: value = [" + value + "]");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError() called with: e = [" + e + "]");
                    }
                });
            }
        });

        //cast/ofType(强转类型的)
        findViewById(R.id.btnCast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestCastBaseBean testCastBaseBean = new TestCastChildBean();
                TestCastBaseBean testCastBaseBean1 = new TestCastBaseBean();
                TestCastBaseBean testCastBaseBean2 = new TestCastChildBean();
                Observable.just(testCastBaseBean, testCastBaseBean1, testCastBaseBean2)
                        .ofType(TestCastChildBean.class)
                        .subscribe(new Consumer<TestCastChildBean>() {
                            @Override
                            public void accept(TestCastChildBean testCastChildBean) throws Exception {
                                Log.d(TAG, "accept() called with: testCastChildBean = [" + testCastChildBean.father + "]");
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.e(TAG, "accept() called with: throwable = [" + throwable + "]");
                            }
                        });
            }
        });

        //empty(测试empty会回调观察者里那些方法)
        findViewById(R.id.btnEmpty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable.empty().subscribe(observer);
                //01-13 11:35:03.816 4130-4130/com.mcxtzhang.rxjava2demo W/zxt/Rx2: onComplete() called
            }
        });


        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + getWindow() + "]");

        findViewById(R.id.btnPublish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cold = Observable.interval(200, TimeUnit.MILLISECONDS).publish();
                d0 = cold.connect();
                d1 = cold.subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.w(TAG, "11111() called with: aLong = [" + aLong + "]");
                    }
                });
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                d2 = cold.subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e(TAG, "22222() called with: aLong = [" + aLong + "]");
                    }
                });

            }
        });
        findViewById(R.id.btnStopHot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (d0.isDisposed()) {
                    // useless i don't know why
                    d0 = cold.connect();
                } else {
                    d0.dispose();
                }
            }
        });

        findViewById(R.id.btnScan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable<String> values = Observable.just("一", "二", "三");
                // 这里第一个默认值的数据 会先发出去，然后再进入apply
                Observable<Indexed<String>> scan = values.scan(new Indexed<String>(10, null)
                        , new BiFunction<Indexed<String>, String, Indexed<String>>() {
                            @Override
                            public Indexed<String> apply(Indexed<String> stringIndexed, String s) throws Exception {
                                Log.e(TAG, "scan() called with: stringIndexed = [" + stringIndexed + "], s = [" + s + "]");
                                return new Indexed<String>(stringIndexed.index + 1, s);
                            }
                        });
                scan.subscribe(new Consumer<Indexed<String>>() {
                    @Override
                    public void accept(Indexed<String> stringIndexed) throws Exception {
                        Log.d(TAG, "111accept() called with: stringIndexed = [" + stringIndexed + "]");
                    }
                });
                scan.subscribe(new Consumer<Indexed<String>>() {
                    @Override
                    public void accept(Indexed<String> stringIndexed) throws Exception {
                        Log.d(TAG, "222accept() called with: stringIndexed = [" + stringIndexed + "]");
                    }
                });


            }
        });


        //▲ onErrorReturn(测试结果：发生错误 后面onNext不再执行)
        findViewById(R.id.btnErrorReturn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        if (!e.isDisposed()) {
                            e.onNext(1);
                            e.onNext(2);
                            e.onError(new Exception("测试错误"));
                            e.onNext(3);
                            e.onComplete();
                        }
                    }
                }).onErrorReturn(new Function<Throwable, Integer>() {
                    @Override
                    public Integer apply(Throwable throwable) throws Exception {
                        return 5;
                    }
                }).subscribeWith(observer);
            }
        });
/*        01-19 19:11:29.866 25188-25188/com.mcxtzhang.rxjava2demo V/zxt/Rx2: onNext() called with: value = [1]
        01-19 19:11:29.866 25188-25188/com.mcxtzhang.rxjava2demo V/zxt/Rx2: onNext() called with: value = [2]
        01-19 19:11:29.866 25188-25188/com.mcxtzhang.rxjava2demo V/zxt/Rx2: onNext() called with: value = [5]
        01-19 19:11:29.866 25188-25188/com.mcxtzhang.rxjava2demo W/zxt/Rx2: onComplete() called*/

        final Observable<Integer> testError = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                if (!e.isDisposed()) {
                    e.onNext(1);
                    e.onNext(2);
                    e.onError(new Exception("测试错误"));
                    e.onNext(3);
                    e.onComplete();
                }
            }
        });

        findViewById(R.id.btnOnErrorResumeNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

/*                testError
                        .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Integer>>() {
                            @Override
                            public ObservableSource<? extends Integer> apply(Throwable throwable) throws Exception {
                                return Observable.just(5, 6, 7);
                            }
                        }).subscribeWith(observer);*/

                testError
                        .onErrorResumeNext(Observable.just(8, 9, 0))
                        .subscribeWith(observer);
            }
        });


        findViewById(R.id.btnRetry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testError.retry(2).subscribeWith(observer);
            }
        });
        findViewById(R.id.btnRetryWhen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testError.retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                        return throwableObservable
                                .take(6)
                                .delay(100, TimeUnit.MICROSECONDS);
                    }
                }).subscribeWith(observer);
            }
        });


        findViewById(R.id.btnSwitchOnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable.switchOnNext(new Observable<ObservableSource<?>>() {
                    @Override
                    protected void subscribeActual(Observer<? super ObservableSource<?>> observer) {
                        observer.onNext(Observable.just(1, 2, 3));
                        observer.onNext(Observable.just(11, 22, 33));
                        observer.onComplete();
                    }
                }).subscribe(observer);
            }
        });


        //▲ 使用buffer后，数据成为List类型
        findViewById(R.id.btnBuffer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*                DisposableObserver disposableObserver = Observable.interval(0, 100, TimeUnit.MILLISECONDS)
                        .buffer(2)
                        .subscribeWith(observer);
                mCompositeDisposable.add(disposableObserver);*/

                //when oble1/obelBuffer2 onSubscribe ,
                Observable<Integer> oble1 = Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        Log.d(TAG, "subscribe() called with: e = [" + e + "]");
                        e.onNext(1);
                        e.onNext(2);
                        e.onComplete();
                    }
                });
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "begin buffer");
                Observable<List<Integer>> oberBuffer2 = oble1.buffer(1);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "after buffer and subscribe with");
                oberBuffer2.subscribeWith(observer);
            }
        });

        //▲ 使用window后，数据成为Observable类型
        //再merge到一起 就和以前没经过window的一样了
        findViewById(R.id.btnWindow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable.merge(Observable.range(0, 5)
                        .window(3, 1))
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                Log.d(TAG, "accept() called with: integer = [" + integer + "]");
                            }
                        });


            }
        });

        findViewById(R.id.btnConcatFirst).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Observable.concat(Observable.range(0, 5), Observable.range(6, 10))
                        //加上只取第一个 0 ，不加上输出15个数字
                        //.firstElement()
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                Log.d(TAG, "accept() called with: integer = [" + integer + "]");
                            }
                        });
            }
        });


        findViewById(R.id.btnCache).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable<Long> cacheObservable = Observable.interval(200, 100, TimeUnit.MILLISECONDS)
                        .take(5)
                        .doOnNext(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                Log.d(TAG, "doOnNext() called with: aLong = [" + aLong + "]");
                            }
                        })
                        .cache()
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                Log.d(TAG, "doOnSubscribe() called with: disposable = [" + disposable + "]");
                            }
                        })
                        .doOnDispose(new Action() {
                            @Override
                            public void run() throws Exception {
                                Log.d(TAG, "doOnDispose() called");
                            }
                        });
                Disposable subscribe = cacheObservable.subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d(TAG, "subscribe() called with: aLong = [" + aLong + "]");
                    }
                });
                mCompositeDisposable.add(subscribe);

                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mCompositeDisposable.clear();


            }
        });


        //▲Replay(取消订阅后，Replay内部默认继续走，但是如果把connect返回后的Disposable 加入 管理，取消 就可以了)
        findViewById(R.id.btnReplay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectableObservable<Long> cacheObservable = Observable.interval(200, 100, TimeUnit.MILLISECONDS)
                        .take(5)
                        .doOnNext(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                Log.d(TAG, "doOnNext() called with: aLong = [" + aLong + "]");
                            }
                        })
                        .replay(4);
                //Replay(取消订阅后，Replay内部默认继续走，但是如果把connect返回后的Disposable 加入 管理，取消 就可以了)
                Disposable cacheConnect = cacheObservable.connect();
                mCompositeDisposable.add(cacheConnect);
                Observable<Long> wraperCacheOb = cacheObservable.doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.d(TAG, "doOnSubscribe() called with: disposable = [" + disposable + "]");
                    }
                })
                        .doOnDispose(new Action() {
                            @Override
                            public void run() throws Exception {
                                Log.d(TAG, "doOnDispose() called");
                            }
                        }).subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread());


                Disposable subscribe1 = wraperCacheOb.subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d(TAG, "subscribe1() called with: aLong = [" + aLong + "]");
                    }
                });
                mCompositeDisposable.add(subscribe1);

                try {
                    Thread.sleep(450);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Disposable subscribe2 = wraperCacheOb.subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d(TAG, "subscribe2() called with: aLong = [" + aLong + "]");
                    }
                });
                //mCompositeDisposable.clear();
            }
        });

        findViewById(R.id.btnReplayCompare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable<Long> longObservable = Observable.interval(200, 100, TimeUnit.MILLISECONDS)
                        .take(5)
                        .doOnNext(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                Log.d(TAG, "doOnNext() called with: aLong = [" + aLong + "]");
                            }
                        }).doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                Log.d(TAG, "doOnSubscribe() called with: disposable = [" + disposable + "]");
                            }
                        })
                        .doOnDispose(new Action() {
                            @Override
                            public void run() throws Exception {
                                Log.d(TAG, "doOnDispose() called");
                            }
                        }).subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread());

                longObservable.subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d(TAG, "accept111111111() called with: aLong = [" + aLong + "]");
                    }
                });

                try {
                    Thread.sleep(450);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                longObservable.subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d(TAG, "accept22222222222222222222() called with: aLong = [" + aLong + "]");
                    }
                });

            }
        });

    }

    //for test hot cold Observable
    Disposable d0, d1, d2;
    ConnectableObservable<Long> cold;


    private int deferValue, value;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
        //mCompositeDisposable.clear();
        mCompositeDisposable.dispose();

    }


    //Flatmap
    //这个方法根据输入的字符串返回一个网站的url列表
    Observable<List<String>> query(final String text) {
        return Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> e) throws Exception {
                if (!e.isDisposed()) {
                    Log.d(TAG, "subscribe() called with: e = [" + e + "]" + Thread.currentThread());
                    List<String> result = new ArrayList<String>();
                    result.add(text + "A");
                    result.add(text + "B");
                    result.add(text + "C");
                    e.onNext(result);
                    e.onComplete();
                }

            }
        });
    }

    // 返回网站的标题，如果404了就返回null
    Observable<String> getTitle(final String URL) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("标题:" + URL);
                e.onComplete();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called + getWindow()" + getWindow());
    }

}
