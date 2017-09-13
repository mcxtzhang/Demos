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

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "zxt/Aidl_Client";
    private TextView mTextView, mBookCount;

    BookManager mBookManager;
    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected() called with: name = [" + name + "], service = [" + service + "]");
            mBookManager = BookManager.Stub.asInterface(service);
            if (null != mBookManager) {
                try {
                    List<Book> books = mBookManager.getBooks();
                    Log.d(TAG, "onServiceConnected() called with: books = [" + books);
                    Toast.makeText(MainActivity.this, "" + books, Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected() called with: name = [" + name + "]");
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
        unbindService(mServiceConnection);
    }
}
