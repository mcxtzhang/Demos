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
import java.util.List;
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
 * 2017/02/04 add : Auto bind params in bundle for target Activity/Fragment.
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

            //2017 02 04 add auto bind params value in bundle for target activity
            List<? extends Element> members = mElementUtils.getAllMembers(typeElement);
            MethodSpec.Builder bindViewMethodSpecBuilder = MethodSpec.methodBuilder("bindParams")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(TypeName.VOID)
                    //RxActivity activity
                    .addParameter(ClassName.get(typeElement.asType()), "activity")
                    .addStatement("$T intent = activity.getIntent()", ClassName.get("android.content", "Intent"))
                    .beginControlFlow("if (null != intent)");
            for (Element item : members) {
                ZParams diView = item.getAnnotation(ZParams.class);
                if (diView == null) {
                    continue;
                }
                bindViewMethodSpecBuilder.addStatement(String.format("activity.%s = intent.getStringExtra(\"%s\")", item.getSimpleName(), diView.key()));
            }
            bindViewMethodSpecBuilder.endControlFlow();

            TypeSpec typeSpec = TypeSpec.classBuilder("ZParams" + element.getSimpleName() + "Binding")
                    //extends xxx
/*                    .superclass(TypeName.get(typeElement.asType()))*/
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(bindViewMethodSpecBuilder.build())
                    .build();
            JavaFile javaFile = JavaFile.builder(AptUtils.getPkgName(mElementUtils, typeElement), typeSpec).build();
            try {
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        MethodSpec constructor = constructorBuilder
                .build();

        //inner class
        ClassName className = ClassName.get(AptUtils.PKG_NAME, "ZRouter");
        FieldSpec INSTANCE = FieldSpec.builder(className, "INSTANCE")
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
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
        ClassName activityClass = ClassName.get("android.app", "Activity");
        ClassName intentClass = ClassName.get("android.content", "Intent");
        ClassName componentNameClass = ClassName.get("android.content", "ComponentName");
        ClassName bundleClass = ClassName.get("android.os", "Bundle");

        ClassName textUtilsClass = ClassName.get("android.text", "TextUtils");
        ClassName logClass = ClassName.get("android.util", "Log");


        MethodSpec jump = MethodSpec.methodBuilder("jump")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(activityClass, "activity")
                .addParameter(String.class, "where")
                .addParameter(bundleClass, "bundle")
                .addStatement("String clsFullName = routerMap.get(where)")
                .beginControlFlow("if ($T.isEmpty(clsFullName))"
                        , textUtilsClass)
                .addStatement("$T.e(TAG, \"Error in jump() where = [\" + where + \"] not found in routerMap!\")"
                        , logClass)
                .endControlFlow()
                .beginControlFlow("else")
                .addStatement("$T intent = new $T()"
                        , intentClass, intentClass)
                .addStatement("intent.setComponent(new $T(activity.getPackageName(), clsFullName))"
                        , componentNameClass)
                .beginControlFlow("if (null != bundle)")
                .addStatement("intent.putExtras(bundle)")
                .endControlFlow()
                .addStatement("activity.startActivity(intent)")
                .addStatement("$T.d(TAG, \"Jump success:\" + where)"
                        , logClass)
                .endControlFlow()
                .build();


        //for result
        MethodSpec jump2 = MethodSpec.methodBuilder("jump")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(activityClass, "activity")
                .addParameter(String.class, "where")
                .addParameter(bundleClass, "bundle")
                .addParameter(int.class, "requestCode")
                .addStatement("String clsFullName = routerMap.get(where)")
                .beginControlFlow("if ($T.isEmpty(clsFullName))"
                        , textUtilsClass)
                .addStatement("$T.e(TAG, \"Error in jump() where = [\" + where + \"] not found in routerMap!\")"
                        , logClass)
                .endControlFlow()
                .beginControlFlow("else")
                .addStatement("$T intent = new $T()"
                        , intentClass, intentClass)
                .addStatement("intent.setComponent(new $T(activity.getPackageName(), clsFullName))"
                        , componentNameClass)
                .beginControlFlow("if (null != bundle)")
                .addStatement("intent.putExtras(bundle)")
                .endControlFlow()
                .addStatement("activity.startActivityForResult(intent, requestCode)")
                .addStatement("$T.d(TAG, \"Jump success:\" + where)"
                        , logClass)
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
                .addMethod(jump2)
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
