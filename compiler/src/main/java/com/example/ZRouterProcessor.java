package com.example;

import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * Intro：
 * Author：zhangxutong
 * E-mail：mcxtzhang@163.com
 * Home Page：http://blog.csdn.net/zxt0601
 * Created：  2017/1/17.
 * History:
 */

public class ZRouterProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(ZRouter.class.getCanonicalName());
    }
}
