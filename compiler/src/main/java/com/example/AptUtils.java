package com.example;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/1/19.
 * History:
 */

public class AptUtils {
    public static final String PKG_NAME = "com.apt";

    private String getPkgName(Elements element, TypeElement type) throws Exception {
        PackageElement pkg = element.getPackageOf(type);
        if (null != pkg && !pkg.isUnnamed()) {
            return pkg.getQualifiedName().toString();
        } else {
            throw new Exception("Error in get package name of " + type.toString() + "");
        }
    }
}
