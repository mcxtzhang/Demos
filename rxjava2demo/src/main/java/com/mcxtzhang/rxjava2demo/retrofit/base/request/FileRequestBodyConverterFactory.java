package com.mcxtzhang.rxjava2demo.retrofit.base.request;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/2/21.
 * History:
 */

public class FileRequestBodyConverterFactory extends Converter.Factory  {
    @Override
    public Converter<File, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new FileRequestBodyConverter();
    }
}
class FileRequestBodyConverter implements Converter<File, RequestBody> {

    @Override
    public RequestBody convert(File file) throws IOException {
        return RequestBody.create(MediaType.parse("application/o22222222222222222222222222222222222222222222tcet-stream"), file);
    }
}
