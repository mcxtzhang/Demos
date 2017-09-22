package com.mcxtzhang.aidldemoserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

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
    public final String TAG = "zxt/" + this.getClass().getSimpleName();

    //private CopyOnWriteArrayList<OnBookAddObserver> mListener = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<OnBookAddObserver> mNewListeners = new RemoteCallbackList<>();
    private AtomicBoolean mIsDestroy = new AtomicBoolean(false);
    ExecutorService mExecutorService = Executors.newSingleThreadExecutor();


    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate() called");
        super.onCreate();
        Book book = new Book();
        book.setBookName("Android开发");
        book.setBookPrice("28");
        book.setBookAuthor("XXX");
        mBooks.add(book);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (!mIsDestroy.get()) {
                    try {
                        Thread.sleep(3000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mBooks.add(new Book(mBooks.size() + "1"));
                    notifyNewBookAdded();
                }
            }
        };

/*        new Thread(runnable).start();*/
        mExecutorService.submit(runnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsDestroy.set(true);
        mExecutorService.shutdown();
        Log.d(TAG, "onDestroy() called");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind() called with: intent = [" + intent + "]");
        //权限验证方式1
        int check = checkCallingOrSelfPermission("com.mcxtzhang.permission.ACCESS_BOOK_SERVICE");
/*
        if (check == PackageManager.PERMISSION_DENIED) return null;*/

        return new BookManager.Stub() {

            @Override
            public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                //权限验证二：
                Log.d(TAG, "onTransact() called with: code = [" + code + "], data = [" + data + "], reply = [" + reply + "], flags = [" + flags + "]");
                int check = checkCallingOrSelfPermission("com.mcxtzhang.permission.ACCESS_BOOK_SERVICE");



                String pkgName = "";
                String[] packagesForUid = getPackageManager().getPackagesForUid(getCallingUid());
                if (null != packagesForUid && packagesForUid.length > 0) {
                    pkgName = packagesForUid[0];
                }
                if (!pkgName.startsWith("com.mcxtzhang")) {
                    return false;
                }


                return super.onTransact(code, data, reply, flags);
            }

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

            @Override
            public void registerListener(OnBookAddObserver listener) throws RemoteException {
                Log.d(TAG, "registerListener() called with: listener = [" + listener + "]");
/*                if (!mListener.contains(listener)) {
                    mListener.add(listener);
                } else {
                    Log.e(TAG, "registerListener() called with: listener = [" + listener + " is exist]");
                }*/
                mNewListeners.register(listener);
            }

            @Override
            public void unRegisterListener(OnBookAddObserver listener) throws RemoteException {
                Log.d(TAG, "unRegisterListener() called with: listener = [" + listener + "]");
/*                if (mListener.contains(listener)) {
                    mListener.remove(listener);
                } else {
                    Log.e(TAG, "unRegisterListener: not found" + listener);
                }*/
                mNewListeners.unregister(listener);
            }
        };
    }

    public void notifyNewBookAdded() {
        Book book = mBooks.get(mBooks.size() - 1);
        Log.d(TAG, "notifyNewBookAdded() called:size:" + mBooks.size());
/*        for (OnBookAddObserver onBookAddObserver : mListener) {
            try {
                onBookAddObserver.onBookAdded(book);
                Log.d(TAG, "notifyNewBookAdded:onBookAddObserver:" + onBookAddObserver);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }*/
        int n = mNewListeners.beginBroadcast();
        for (int i = 0; i < n; i++) {
            OnBookAddObserver broadcastItem = mNewListeners.getBroadcastItem(i);
            if (null != broadcastItem) {
                try {
                    broadcastItem.onBookAdded(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        mNewListeners.finishBroadcast();

    }


}
