package com.mcxtzhang.binderdemo1;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/9/13.
 * History:
 */

public class MusicPlayService extends Service {

    private static final String TAG = "zxt/MusicPlayService";
    private boolean isPlay;
    private MusicPlayBinder mMusicPlayBinder;

    public MusicPlayService() {
        Log.d(TAG, "MusicPlayService() called");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() called");
        mMusicPlayBinder = new MusicPlayBinder();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind() called with: intent = [" + intent + "]");
        return mMusicPlayBinder;
    }

    public void playMusic() {
        isPlay = true;
        Log.d(TAG, "playMusic() called");
    }

    public void stopMusic() {
        isPlay = false;
        Log.i(TAG, "stopMusic() called");
    }

    public class MusicPlayBinder extends Binder {
        MusicPlayService getMusicPlayService() {
            return MusicPlayService.this;
        }

        boolean isMusicPlay() {
            return isPlay;
        }
    }
}
