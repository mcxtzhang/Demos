package com.example.butter_test.meminfo.badcase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.butter_test.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 启动线程、线程池，退出Activity时，任务没执行完 ，会leak，线程也不会结束。
 * <p>
 * 启动线程、线程池，退出Activity时，任务已经执行完 ，不会leak，线程执行完当时就会结束，而线程池在触发GC时，也会结束线程。
 * <p>
 * 启动线程、线程池，退出Activity时，任务没执行完 ，手动停止线程、线程池，不会leak，线程也不会结束。
 */
public class ThreadLeakActivity extends AppCompatActivity {
    private ExecutorService mSingleExecutor = Executors.newSingleThreadExecutor();
    private SubInfiniteThread mSubThread;
    private InfiniteRunnable mInfiniteRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_leak);
        //leak 1  线程未执行完毕,退出Activity 会leak
        (mSubThread = new SubInfiniteThread()).start();

        //leak 2  线程池未执行完毕,退出Activity 会leak
        mInfiniteRunnable = new InfiniteRunnable();
        mSingleExecutor.execute(mInfiniteRunnable);

        //test 3 线程执行完毕,退出Activity 不会leak
//        new SubThread().start();
//
//        //test 4  线程池执行完毕,退出Activity 不会leak
//        mSingleExecutor.submit(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(Thread.currentThread().getName() + ":a");
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubThread.stopMe();
        mSingleExecutor.shutdownNow();
        mInfiniteRunnable.stopMe();
    }

    class SubThread extends Thread {
        public SubThread() {
            super("zxt-subThread");
        }

        @Override
        public void run() {
            super.run();
            System.out.println(Thread.currentThread().getName() + ":a");
        }
    }

    class SubInfiniteThread extends Thread {
        private volatile boolean stoped = false;

        public SubInfiniteThread() {
            super("zxt-SubInfiniteThread");
        }

        public void stopMe() {
            this.stoped = true;
            interrupt();
        }

        @Override
        public void run() {
            super.run();
            while (!stoped) {
                System.out.println(Thread.currentThread().getName() + ":a");

            }
        }
    }

    class InfiniteRunnable implements Runnable {
        private volatile boolean stoped = false;

        public void stopMe() {
            this.stoped = true;
        }

        @Override
        public void run() {
            Thread.currentThread().setName("zxt-InfiniteRunnable");
            while (!stoped) {
                System.out.println(Thread.currentThread().getName() + ":a");
            }
        }
    }

}

