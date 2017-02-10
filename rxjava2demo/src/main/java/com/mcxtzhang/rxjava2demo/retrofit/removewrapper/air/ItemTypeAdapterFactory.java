package com.mcxtzhang.rxjava2demo.retrofit.removewrapper.air;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.mcxtzhang.rxjava2demo.retrofit.model.bf.BaseBean;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/2/10.
 * History:
 */

public class ItemTypeAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<T> rawType = (Class<T>) type.getRawType();
        if (rawType != BaseBean.class) {
            return null;
        }
        return (TypeAdapter<T>) new CstTypeAdapter<T>();
    }
}
