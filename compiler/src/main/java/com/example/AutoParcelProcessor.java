package com.example;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;


@AutoService(Processor.class)
public class AutoParcelProcessor extends AbstractProcessor {

    private Filer mFileUtils;
    private Elements mElementUtils;
    private Messager mMessager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFileUtils = processingEnv.getFiler();
        mElementUtils = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();
    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationTypes = new LinkedHashSet<String>();
        annotationTypes.add(TestHelloWorld.class.getCanonicalName());
        return annotationTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> set2 = roundEnvironment.getElementsAnnotatedWith(TestHelloWorld.class);
        for (Element element : set2) {
            if (element.getKind() != ElementKind.CLASS) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "only support class");
            }
            TestHelloWorld annotation = element.getAnnotation(TestHelloWorld.class);

            ClassName classLog = ClassName.get("android.util", "Log");

            MethodSpec main = MethodSpec.methodBuilder("main")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(void.class)
                    .addParameter(String[].class, "args")
                    .addStatement("$T.out.println($S)", System.class, "u input value is :" + annotation.value())
                    .build();

            MethodSpec register = MethodSpec.methodBuilder("register")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(void.class)
                    .addParameter(String.class, "where")
                    .addStatement("android.util.Log.e(\"zxt\", \"Auto created by apt = [\" + $S + \"]\")", annotation.value())
                    .build();

            MethodSpec jump = MethodSpec.methodBuilder("jump")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(void.class)
                    .addParameter(String.class, "where")
                    .addStatement("$T.i($S, $S+ $N)", classLog, "zxt/test2","由于$S里的字符串会被自动追加转移字符和引号，所以参数不要在$S里写, get the params is : [","where")
                    .build();

            //First router method, params is Class
            MethodSpec zJump = MethodSpec.methodBuilder("zJump")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(void.class)
                    .addParameter(ClassName.get(element.asType()), "context")
                    .addParameter(Class.class, "aClass")
                    .addStatement("$T.e(\"zxt\", \"Auto created22222 by apt = [\" + $S + \"], begin first zJump \" )", classLog, annotation.value())
                    .addStatement("context.startActivity(new android.content.Intent(context,aClass ));")
                    .build();

            //bindViewMethodSpecBuilder.addStatement(String.format("activity.%s = (%s) activity.findViewById(%s)", item.getSimpleName(), ClassName.get(item.asType()).toString(), diView.value()));


            TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(main)
                    .addMethod(jump)
                    .addMethod(zJump)
                    .build();
            JavaFile javaFile = JavaFile.builder("com.mcxtzhang", helloWorld)
                    .build();
            try {
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return (false);
    }

}

