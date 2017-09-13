package com.mcxtzhang.binder2_messenger;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Messenger mMessenger;
    boolean mBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindService(new Intent(this, DownloadService.class), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mMessenger = new Messenger(service);
                mBound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mMessenger = null;
                mBound = false;
            }
        }, BIND_AUTO_CREATE);


        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Message obtain = Message.obtain();
                    obtain.what = DownloadService.MSG_SAY_HELLO;
                    mMessenger.send(obtain);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
