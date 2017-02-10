package com.mcxtzhang.rxjava2demo.retrofit.model.bf.base;

import android.util.Log;

import com.google.gson.Gson;
import com.mcxtzhang.rxjava2demo.retrofit.model.bf.BaseBean;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 注册一个自定义的转换类GsonResponseBodyConverter
 *
 * @param <T>
 */
class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        try {
            Log.d("TAG", "GsonResponseBodyConverter convert response>>" + response);
            //ResultResponse 只解析result字段
            BaseBean resultResponse = gson.fromJson(response, BaseBean.class);
            if ("1".equals(resultResponse.getFlag())) {
                //result==0表示成功返回，继续用本来的Model类解析
                return gson.fromJson(response, type);
            } else {
                //ErrResponse 将msg解析为异常消息文本
/*                ErrResponse errResponse = gson.fromJson(response, ErrResponse.class);*/
                throw new ResultException(resultResponse.getFlag(), resultResponse.getMessage());
            }
        } finally {

        }
    }
}