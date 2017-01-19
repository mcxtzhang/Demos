package com.example;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Intro: Use ComponentName replace Class
 * Author:zhangxutong
 * E-mail:mcxtzhang@163.com
 * Home Page:http://blog.csdn.net/zxt0601
 * Created:  2017/1/17.
 * History:
 */

@AutoService(Processor.class)
public class ZRouter2Processor extends AbstractProcessor {

    private Elements mElementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mElementUtils = processingEnv.getElementUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(ZRouter.class.getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        //field
        //TAG
        FieldSpec TAG = FieldSpec.builder(String.class, "TAG")
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                .initializer("$S", "zxt/ZRouter")
                .build();
        //private Map<String, String> routerMap;
        TypeName listString = ParameterizedTypeName.get(Map.class, String.class, String.class);
        FieldSpec routerMap = FieldSpec.builder(listString, "routerMap")
                .addModifiers(Modifier.PRIVATE)
                .build();

        //Constructor
        MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .addStatement("routerMap = new $T()", HashMap.class);

        //traverse annotation named ZRtouer

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(ZRouter.class);
        for (Element element : elements) {
            TypeElement typeElement;
            if (element instanceof TypeElement) {
                typeElement = (TypeElement) element;
            } else {
                return false;
            }
            ZRouter zRouter = typeElement.getAnnotation(ZRouter.class);
            String className = typeElement.getQualifiedName().toString();
            constructorBuilder.addStatement("routerMap.put($S, $S)", zRouter.path(), className);
        }
        MethodSpec constructor = constructorBuilder
                .build();

        //inner class
        ClassName className = ClassName.get(AptUtils.PKG_NAME, "ZRouter");
        FieldSpec INSTANCE = FieldSpec.builder(className, "INSTANCE")
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .initializer("new $T()", className)
                .build();
        TypeSpec innerClass = TypeSpec.classBuilder("InnerClass")
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .addField(INSTANCE)
                .build();

        //getInstance method
        MethodSpec methodGetInstance = MethodSpec.methodBuilder("getInstance")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(className)
                .addStatement("return $N.INSTANCE", innerClass)
                .build();


        //jump method:
        ClassName contextClass = ClassName.get("android.content", "Context");
        ClassName intentClass = ClassName.get("android.content", "Intent");
        ClassName conponentNameClass = ClassName.get("android.content", "ComponentName");
        ClassName textUtilsClass = ClassName.get("android.text", "TextUtils");

        MethodSpec jump = MethodSpec.methodBuilder("jump")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(contextClass, "context")
                .addParameter(String.class, "where")
                .addStatement("String clsFullName = routerMap.get(where)")
                .beginControlFlow("if ($T.isEmpty(clsFullName))", textUtilsClass)
                // TODO: 2017/1/19 log statement
                .endControlFlow()
                .beginControlFlow("else")
                .addStatement("$T intent = new $T()", intentClass, intentClass)
                .addStatement("intent.setComponent(new $T(context.getPackageName(), clsFullName))", conponentNameClass)
                .addStatement("context.startActivity(intent)")
                // TODO: 2017/1/19 log statement
                .endControlFlow()
                .build();


        //class
        TypeSpec hello = TypeSpec.classBuilder("ZRouter")
                .addModifiers(Modifier.PUBLIC)
                .addField(TAG)
                .addField(routerMap)
                .addMethod(constructor)
                .addType(innerClass)
                .addMethod(methodGetInstance)
                .addMethod(jump)
                .build();

        try {
            JavaFile.builder(AptUtils.PKG_NAME, hello)
                    .build()
                    .writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }


}
