package com.mcxtzhang.rxjavademo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static rx.Observable.interval;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "RxJava";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted() called");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError() called with: e = [" + e + "]");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext() called with: s = [" + s + "]");

            }
        };

        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onNext(String s) {
                Log.d(TAG, "Item: " + s);
            }

            @Override
            public void onCompleted() {
                Log.d(TAG, "Completed!");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "Error!");
            }
        };

        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("Aloha");
                subscriber.onCompleted();
            }
        });
        /**
         * create() 方法是 RxJava 最基本的创造事件序列的方法。基于这个方法， RxJava 还提供了一些方法用来快捷创建事件队列，例如：
         just(T...): 将传入的参数依次发送出来。
         */
        Observable observable2 = Observable.just("Hello", "Hi", "Aloha");
// 将会依次调用：
// onNext("Hello");
// onNext("Hi");
// onNext("Aloha");
// onCompleted();

//from(T[]) / from(Iterable<? extends T>) : 将传入的数组或 Iterable 拆分成具体对象后，依次发送出来
        String[] words = {"Hello", "Hi", "Aloha"};
        Observable observable3 = Observable.from(words);
// 将会依次调用：
// onNext("Hello");
// onNext("Hi");
// onNext("Aloha");
// onCompleted();

        observable3.subscribe(subscriber);


        /**
         * a. 打印字符串数组
         将字符串数组 names 中的所有字符串依次打印出来：
         */
        String[] names = new String[]{"张旭童", "大童阁", "Android"};
        Observable.from(names).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(TAG, "call() called with: s = [" + s + "]");
            }
        });


        /**
         * b. 由 id 取得图片并显示
         由指定的一个 drawable 文件 id drawableRes 取得图片，并显示在 ImageView 中，并在出现异常的时候打印 Toast 报错：
         */
        final ImageView iv = (ImageView) findViewById(R.id.iv);
        final int drawableResId = R.mipmap.ic_launcher;
        Observable<Drawable> imgObservable1 = Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getResources().getDrawable(drawableResId);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        });

        imgObservable1.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new Subscriber<Drawable>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, "出错了", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Drawable drawable) {
                        iv.setImageDrawable(drawable);
                    }
                });


        //[map]：
        // 利用变化做上面的demo
        final ImageView iv2 = (ImageView) findViewById(R.id.iv2);
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(drawableResId);
                subscriber.onCompleted();
            }
        }).map(new Func1<Integer, Drawable>() {
            @Override
            public Drawable call(Integer integer) {
                return getTheme().getDrawable(integer);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Drawable>() {
                    @Override
                    public void call(Drawable drawable) {
                        iv2.setImageDrawable(drawable);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "call: 出错了加载图片出错了");
                    }
                });
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick() called with: view = [" + view + "]");
            }
        });

        //flatmap使用：
        List<Student> students = new ArrayList<>();
        students.add(new Student());
        students.add(new Student());
        students.add(new Student());
        students.add(new Student());
        students.add(new Student());

        Subscriber<Course> courseSubscriber = new Subscriber<Course>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "course onCompleted() called");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "course onError() called with: e = [" + e + "]");
            }

            @Override
            public void onNext(Course course) {
                Log.d(TAG, "onNext() called with: course = [" + course.getScroe() + "]");
            }
        };

        Observable.from(students).flatMap(new Func1<Student, Observable<Course>>() {
            @Override
            public Observable<Course> call(Student student) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return Observable.from(student.getCourse());
            }
        }).subscribeOn(Schedulers.io())//加上这句话  Observable的动作都在io线程里
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(courseSubscriber);


        final Observable<String> throttleFirst = Observable.just("什么鬼")
                .throttleFirst(500, TimeUnit.MILLISECONDS);//500秒不会重复发出事件;
        findViewById(android.R.id.content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                throttleFirst.subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                Log.d(TAG, "防止重复点击吗？call() called with: s = [" + s + "]");
                            }
                        });
            }
        });


        final TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText("切换前");

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run() called" + Thread.currentThread());
                tv.setText("变化之前ddddddd");
                Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        Log.d(TAG, "处理时 called" + Thread.currentThread());
                        subscriber.onNext("呵呵哒");
                        subscriber.onCompleted();
                    }
                })
                        .subscribeOn(Schedulers.computation())
                        .subscribeOn(AndroidSchedulers.mainThread())//即使重复写 但是以上面那个为准
                        .doOnSubscribe(new Action0() {//可以设置start所在现成的onStart方法
                            @Override
                            public void call() {
                                Log.d(TAG, "变化之前 called" + Thread.currentThread());
                                tv.setText("变化之前");
                            }
                        })
                        .subscribeOn(Schedulers.io())//改变doOnSubscribe的线程，这离doOnSubscribe这句话最近
                        .subscribeOn(AndroidSchedulers.mainThread())//即使重复写 但是以上面那个为准
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                Log.d(TAG, "call() called with: s = [" + s + "]" + Thread.currentThread());
                            }
                        });
            }
        }).start();


        Observable.just("On", "Off", "On", "Off")
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return s != null;
                    }
                }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.d("DDDDDD", "结束观察...\n");
            }

            @Override
            public void onError(Throwable e) {
                //出现错误会调用这个方法
            }

            @Override
            public void onNext(String s) {
                //处理事件
                Log.d("DDDDD", "handle this---" + s);
            }
        });


        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.just(1, 2)
                        .subscribeOn(Schedulers.io())
                        .map(new Func1<Integer, String>() {
                            @Override
                            public String call(Integer integer) {
                                Log.e(TAG, "map1: " + integer + " map1" + ", xiancheng:" + Thread.currentThread());
                                return integer + " map1";
                            }
                        })
                        .observeOn(Schedulers.io())
                        .map(new Func1<String, String>() {
                            @Override
                            public String call(String s) {
                                Log.e(TAG, "map2: " + s + " map2" + ", xiancheng:" + Thread.currentThread());
                                return s + " map2";
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new Func1<String, String>() {
                            @Override
                            public String call(String s) {
                                Log.e(TAG, "map3: " + s + " map3" + ", xiancheng:" + Thread.currentThread());
                                return s + " map3";
                            }
                        })
/*                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())*/
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {
                                Log.e(TAG, "onCompleted: " + ", xiancheng:" + Thread.currentThread());
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "onError: " + ", xiancheng:" + Thread.currentThread());
                            }

                            @Override
                            public void onNext(String s) {
                                Log.e(TAG, "onNext: " + s + ", xiancheng:" + Thread.currentThread());
                            }
                        });
            }
        });


        findViewById(R.id.btnJust).setOnClickListener(new View.OnClickListener() {
            String TAG = "zxt";

            @Override
            public void onClick(View v) {
                Integer[] array1 = new Integer[]{1, 2, 3, 4, 5, 6};
                Integer[] array2 = new Integer[]{11, 12, 13, 14, 15, 16};
                Observable<Integer[]> observable1 = Observable.just(array1, array2);
                observable1.subscribe(new Subscriber<Integer[]>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted() called");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError() called with: e = [" + e + "]");
                    }

                    @Override
                    public void onNext(Integer[] integers) {
                        Log.d(TAG, "onNext() called with: integers = [" + integers + "]");
                    }
                });
            }
        });


        findViewById(R.id.btnInterval).setOnClickListener(new View.OnClickListener() {
            String TAG = "zxt";

            @Override
            public void onClick(View v) {
                Observable.just(1, 2, 3, 4)
                        .interval(3, 3, TimeUnit.SECONDS).
                        subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                Log.d(TAG, "call() called with: aLong = [" + aLong + "]");
                            }
                        });
            }
        });

        findViewById(R.id.btnRange).setOnClickListener(new View.OnClickListener() {
            String TAG = "zxt";

            @Override
            public void onClick(View v) {
                Observable.range(5, 10).subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted() called");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError() called with: e = [" + e + "]");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext() called with: integer = [" + integer + "]");
                    }
                });
            }
        });

        findViewById(R.id.btnFilter).setOnClickListener(new View.OnClickListener() {
            String TAG = "zxt";

            @Override
            public void onClick(View v) {
                Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(1);
                            subscriber.onNext(50);
                            subscriber.onNext(25);
                            subscriber.onCompleted();
                        }
                    }
                }).filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return (integer&1)  == 0 ;
                    }
                }).subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted() called");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError() called with: e = [" + e + "]");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext() called with: integer = [" + integer + "]");
                    }
                });
            }
        });

    }


}
