package com.mcxtzhang.rxjava2demo.retrofit.removewrapper.air;

import android.util.Log;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/2/10.
 * History:
 */

public class CstTypeAdapter <T>extends TypeAdapter<T> {
    private static final String TAG = "Gson";

    @Override
    public void write(JsonWriter out, T value) throws IOException {
        Log.d(TAG, "write() called with: out = [" + out + "], value = [" + value + "]");
    }

    @Override
    public T read(JsonReader in) throws IOException {
        Log.d(TAG, "read() called with: in = [" + in + "]");
        return null;
    }
}
