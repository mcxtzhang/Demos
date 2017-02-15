package com.mcxtzhang.rxjava2demo.retrofit.base.gson;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.mcxtzhang.rxjava2demo.retrofit.base.ResultException;
import com.mcxtzhang.rxjava2demo.retrofit.base.wrapper.BaseBean;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 注册一个自定义的转换类GsonResponseBodyConverter
 * 这个类会自动将 flag msg 都剥离掉
 *
 * @param <T>
 */
class GsonEntityResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    GsonEntityResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        Type BaseBeanTtype = $Gson$Types.newParameterizedTypeWithOwner(null, BaseBean.class, type);
        BaseBean result = gson.fromJson(response, BaseBeanTtype);
        Log.d("TAG", "GsonEntityResponseBodyConverter convert response>>" + response + " T" + "BaseBean result:" + result);
        if ("1".equals(result.getFlag())) {
            //result==0表示成功返回，继续用本来的Model类解析
            //return gson.fromJson(response, type);
            //剥离无用字段
            T data = (T) result.getData();

            return data;
        } else {
            //ErrResponse 将msg解析为异常消息文本
/*                ErrResponse errResponse = gson.fromJson(response, ErrResponse.class);*/
            throw new ResultException(result.getFlag(), result.getMessage());
        }

    }
}