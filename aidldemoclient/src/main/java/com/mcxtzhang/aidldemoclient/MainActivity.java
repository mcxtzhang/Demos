package com.mcxtzhang.aidldemoclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mcxtzhang.aidldemoserver.Book;
import com.mcxtzhang.aidldemoserver.BookManager;
import com.mcxtzhang.aidldemoserver.OnBookAddObserver;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "zxt/Aidl_Client";
    private TextView mTextView, mBookCount;

    BookManager mBookManager;

    OnBookAddObserver mOnBookAddObserver = new OnBookAddObserver.Stub() {
        @Override
        public void onBookAdded(Book newBook) throws RemoteException {
            Log.w(TAG, "In Client:onBookAdded() called with: newBook = [" + newBook + "], and run in" + Thread.currentThread());
        }
    };

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //主线程
            Log.d(TAG, "onServiceConnected() called with: name = [" + name + "], service = [" + service + "], and run in" + Thread.currentThread());
            mBookManager = BookManager.Stub.asInterface(service);
            //死亡代理
            try {
                service.linkToDeath(new IBinder.DeathRecipient() {
                    @Override
                    public void binderDied() {
                        //客户端Binder线程池里    子线程
                        Log.d(TAG, "binderDied() called,mBookManager:" + mBookManager+ "], and run in" + Thread.currentThread());
                        if (mBookManager == null) return;
                        mBookManager.asBinder().unlinkToDeath(this, 0);
                        mBookManager = null;
                    }
                }, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            //业务函数
            if (null != mBookManager) {
                try {
                    List<Book> books = mBookManager.getBooks();
                    Log.d(TAG, "onServiceConnected() called with: books = [" + books+ "], and run in" + Thread.currentThread());
                    Toast.makeText(MainActivity.this, "" + books, Toast.LENGTH_SHORT).show();


                    //监听
                    mBookManager.registerListener(mOnBookAddObserver);

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //主线程
            Log.d(TAG, "onServiceDisconnected() called with: name = [" + name + "]"+ "], attempttoReBind ,and run in" + Thread.currentThread());
            attemptToBindService();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.textview);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Book book = new Book();
                    book.setBookName("张旭童自传");
                    mBookManager.addBook(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        mBookCount = (TextView) findViewById(R.id.books);
        mBookCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Toast.makeText(MainActivity.this, "" + mBookManager.getBookCount(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onClick() called with: mBookManager.getBookCount() = [" + mBookManager.getBookCount() + "]");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        attemptToBindService();

        findViewById(R.id.btnStopService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unbindService(mServiceConnection);
            }
        });
    }

    private void attemptToBindService() {
        ComponentName componentName = new ComponentName("com.mcxtzhang.aidldemoserver", "com.mcxtzhang.aidldemoserver.BookManagerService");
        Intent intent = new Intent();
        intent.setComponent(componentName);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //加不加这一行代码 Service都会执行onDestroy() 但是还是要加上 防止内存泄漏
        unbindService(mServiceConnection);

        //取消注册监听器
        if (null != mOnBookAddObserver && mOnBookAddObserver.asBinder().isBinderAlive() && null != mBookManager) {
            try {
                mBookManager.unRegisterListener(mOnBookAddObserver);
                Log.w(TAG, "onDestroy() unRegisterListener");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

    }
}
