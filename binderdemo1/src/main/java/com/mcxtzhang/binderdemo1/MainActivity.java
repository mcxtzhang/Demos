package com.mcxtzhang.binderdemo1;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "zxt/MainActivity";
    private MusicPlayService.MusicPlayBinder mMusicPlayBinder;
    private MusicPlayService mMusicPlayService;
    private boolean mBinded;

    private TextView mTextViewPlay,mTextViewStop,mTextViewIsPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewPlay = (TextView)findViewById(R.id.start_music);
        mTextViewStop = (TextView)findViewById(R.id.stop_music);
        mTextViewPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"call Server method playMusic(),music start play!");
                if (mMusicPlayBinder.isMusicPlay()){
                    Log.e(TAG,"call Server method playMusic(),music has already play!");
                }else{
                    mMusicPlayService.playMusic();
                }
            }
        });
        mTextViewStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"call Server method playMusic(),music start play!");
                mMusicPlayService.stopMusic();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mBinded) {
            Log.d(TAG, "onStart() called");
            Intent intent = new Intent(this, MusicPlayService.class);
            bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBinded) {
            unbindService(mServiceConnection);
            mBinded = false;
        }
    }

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinded = true;
            mMusicPlayBinder = (MusicPlayService.MusicPlayBinder) service;
            mMusicPlayService = mMusicPlayBinder.getMusicPlayService();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBinded = false;
        }
    };
}
