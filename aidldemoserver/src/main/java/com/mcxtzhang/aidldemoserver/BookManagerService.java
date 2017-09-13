package com.mcxtzhang.aidldemoserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Intro: 采用Aidl实现的Service
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/9/13.
 * History:
 */

public class BookManagerService extends Service {
    private List<Book> mBooks = new ArrayList<>();
    public final String TAG = "zxt/"+this.getClass().getSimpleName();


    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate() called");
        super.onCreate();
        Book book = new Book();
        book.setBookName("Android开发");
        book.setBookPrice("28");
        book.setBookAuthor("XXX");
        mBooks.add(book);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind() called with: intent = [" + intent + "]");
        return new BookManager.Stub() {
            @Override
            public List<Book> getBooks() throws RemoteException {
                synchronized (this) {
                    Log.e(TAG, "invoking getBooks() method , now the list is : " + mBooks.toString());
                    if (mBooks != null) {
                        return mBooks;
                    }
                    return new ArrayList<>();
                }
            }

            @Override
            public Book getBook(String bookName) throws RemoteException {
                synchronized (this) {
                    if (mBooks == null || mBooks.size() <= 0) {
                        return null;
                    }
                    for (int i = 0; i < mBooks.size(); i++) {
                        if (mBooks.get(i).equals(bookName)) {
                            return mBooks.get(i);
                        }
                    }
                    return null;
                }
            }

            @Override
            public int getBookCount() throws RemoteException {
                synchronized (this) {
                    Log.e(TAG, "invoking getBookCount() method , now the list is : " + mBooks.toString());
                    if (mBooks != null) {
                        return mBooks.size();
                    }
                    return 0;
                }
            }

            @Override
            public void addBook(Book book) throws RemoteException {
                synchronized (this) {
                    if (book != null) {
                        mBooks.add(book);
                        Log.e(TAG, "invoking addBook() method , now the list is : " + mBooks.toString());
                    }
                }
            }
        };
    }



}
