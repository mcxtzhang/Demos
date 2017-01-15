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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
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

    }

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
        Log.d(TAG, "onResume() called + getWindow()"+ getWindow());
    }

}
