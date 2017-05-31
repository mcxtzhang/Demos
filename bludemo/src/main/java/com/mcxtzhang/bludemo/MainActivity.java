package com.mcxtzhang.bludemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mcxtzhang.bludemo.myprinter.DrawerService;
import com.mcxtzhang.bludemo.myprinter.Global;

import java.lang.ref.WeakReference;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    String strEnglish = "~!@#$%^&*()_+`[]{}\\|;',./:\"<>?1234567890-=abcdefghijklmnopqrstuvwxyz\n";
    String strChinese = "待到山花烂漫时，她在丛中笑。\n";


    private static final String TAG = "zxt";
    private static Handler mHandler = null;

    static class MHandler extends Handler {

        WeakReference<MainActivity> mActivity;

        MHandler(MainActivity activity) {
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity theActivity = mActivity.get();
            switch (msg.what) {

                case Global.CMD_POS_STEXTOUTRESULT:
                case Global.CMD_POS_WRITERESULT: {
                    int result = msg.arg1;
                    Toast.makeText(theActivity, (result == 1) ? "成功" : "失败",
                            Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "Result: " + result);
                    break;
                }

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new MHandler(this);
        DrawerService.addHandler(mHandler);

        Intent intent = new Intent(this, DrawerService.class);
        startService(intent);


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                print();
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ConnectBTPairedActivity.class));
            }
        });


    }

    private static int nFontSize, nTextAlign, nScaleTimesWidth,
            nScaleTimesHeight, nFontStyle, nLineHeight = 32, nRightSpace;


    private void print() {
        // 不要直接和Pos打交道，要通过workThread来交流
        Map<String, Charset> charsetMap = Charset.availableCharsets();
        Collection<Charset> charsetColl = charsetMap.values();
        Iterator<Charset> iter = charsetColl.iterator();
        for (int i = 0; i < charsetColl.size(); i++) {
            Log.v(TAG, iter.next().displayName());
        }
        if (DrawerService.workThread.isConnected()) {
            int charset = 0, codepage = 0;
            String text = "";
            String encoding = "";
            byte[] addBytes = new byte[0];

            text = strEnglish + strChinese;
            encoding = "GBK";
            charset = 15;
            codepage = 255;
            addBytes = new byte[]{0x1c, 0x26}; // 设定汉字模式


            Bundle dataCP = new Bundle();
            Bundle dataAlign = new Bundle();
            Bundle dataRightSpace = new Bundle();
            Bundle dataLineHeight = new Bundle();
            Bundle dataTextOut = new Bundle();
            Bundle dataWrite = new Bundle();
            dataCP.putInt(Global.INTPARA1, charset);
            dataCP.putInt(Global.INTPARA2, codepage);
            dataAlign.putInt(Global.INTPARA1, nTextAlign);
            dataRightSpace.putInt(Global.INTPARA1, nRightSpace);
            dataLineHeight.putInt(Global.INTPARA1, nLineHeight);
            dataTextOut.putString(Global.STRPARA1, text);
            dataTextOut.putString(Global.STRPARA2, encoding);
            dataTextOut.putInt(Global.INTPARA1, 0);
            dataTextOut.putInt(Global.INTPARA2, nScaleTimesWidth);
            dataTextOut.putInt(Global.INTPARA3, nScaleTimesHeight);
            dataTextOut.putInt(Global.INTPARA4, nFontSize);
            dataTextOut.putInt(Global.INTPARA5, nFontStyle);
            dataWrite.putByteArray(Global.BYTESPARA1, addBytes);
            dataWrite.putInt(Global.INTPARA1, 0);
            dataWrite.putInt(Global.INTPARA2, addBytes.length);

            DrawerService.workThread.handleCmd(
                    Global.CMD_POS_SETCHARSETANDCODEPAGE, dataCP);
            DrawerService.workThread.handleCmd(Global.CMD_POS_SALIGN,
                    dataAlign);
            DrawerService.workThread.handleCmd(
                    Global.CMD_POS_SETRIGHTSPACE, dataRightSpace);
            DrawerService.workThread.handleCmd(Global.CMD_POS_STEXTOUT,
                    dataTextOut);
            DrawerService.workThread.handleCmd(Global.CMD_POS_WRITE,
                    dataWrite);
            DrawerService.workThread.handleCmd(
                    Global.CMD_POS_SETLINEHEIGHT, dataLineHeight);

        } else {
            Toast.makeText(this, "请先连接打印机", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DrawerService.delHandler(mHandler);
        mHandler = null;
    }
}
