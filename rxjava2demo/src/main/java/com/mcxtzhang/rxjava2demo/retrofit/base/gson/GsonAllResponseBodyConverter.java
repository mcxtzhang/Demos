package com.mcxtzhang.rxjava2demo.retrofit.base.gson;

import android.util.Log;

import com.google.gson.Gson;
import com.mcxtzhang.rxjava2demo.retrofit.base.ResultException;
import com.mcxtzhang.rxjava2demo.retrofit.base.wrapper.BaseBean;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 注册一个自定义的转换类GsonResponseBodyConverter
 * <p>
 * 传入的是BaseBean<T></>
 *
 * @param <T>
 */
class GsonAllResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    GsonAllResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        BaseBean result = gson.fromJson(response, type);
        Log.d("TAG", "GsonAllResponseBodyConverter convert response>>" + response + " T" + "BaseBean result:" + result);
        if ("1".equals(result.getFlag())) {
            //result==0表示成功返回，继续用本来的Model类解析
            //return gson.fromJson(response, type);
            return (T) result;
        } else {
            //ErrResponse 将msg解析为异常消息文本
/*                ErrResponse errResponse = gson.fromJson(response, ErrResponse.class);*/
            throw new ResultException(result.getFlag(), result.getMessage());
        }

    }
}