package com.mcxtzhang.github;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ZRouter;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
@ZRouter(path = "second")
public class RxActivity extends AppCompatActivity {

    private static final String TAG = "RxJava";
    private CompositeSubscription msubscription;//管理所有的订阅

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);

        this.msubscription = new CompositeSubscription();

        findViewById(R.id.btnLoop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Subscriber<String> subscriber = new Subscriber<String>() {
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
                        Log.e(TAG, "onNext() called with: s = [" + s + "]");
                    }
                };


                Subscription i=Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        int i = 0;
                        while (true) {
                            Log.d(TAG, "call() called with: i = [" + i + "]");
                            subscriber.onNext(i++ + "");
                        }
                    }
                }).subscribeOn(Schedulers.io()).subscribe(subscriber);

                msubscription.add(i);//把订阅加入管理集合中
            }
        });

        findViewById(R.id.btnStop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RxActivity.this, "蒂娜及", Toast.LENGTH_SHORT).show();
                msubscription.unsubscribe   ();
            }
        });


        a();

    }

    void a() {
        b();
    }

    void b() {
        StringBuffer err = new StringBuffer();
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        for (int i = 0; i < stack.length; i++) {
            err.append("\tat ");
            err.append(stack[i].toString());
            err.append("\n");
        }
        Log.e("TAG", err.toString());
    }


    @Override
    protected void onStop() {
        super.onStop();
        //在activity结束生命周期的时候取消订阅，解除对context的引用
        if(msubscription != null){
            this.msubscription.unsubscribe();
        }
    }
}
