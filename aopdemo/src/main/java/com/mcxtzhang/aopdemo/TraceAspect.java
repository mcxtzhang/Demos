package com.mcxtzhang.aopdemo;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

//这是我写的切面类
@Aspect
public class TraceAspect {

    private static final String TAG = "zxt";

    @Pointcut("execution(* *..checkAspectJ(..)) && args(a,b)")
    public void createPoint(int a, int b) {
        Log.d(TAG, "createPoint() called with: a = [" + a + "], b = [" + b + "]");
    }

    @After("createPoint(a,b)")
    public void logAfter(JoinPoint joinPoint, int a, int b) {
        Log.d(TAG, "After!!!:" + joinPoint.toShortString() + "----" + a + "---" + b);
    }

    @Before("createPoint(a,b)")
    public void logBefore(JoinPoint joinPoint, int a, int b) {
        Log.d(TAG, "Before!!!:" + joinPoint.toShortString() + "----" + a + "---" + b);
    }

    @Around("createPoint(a,b)")
    public Object logAround(ProceedingJoinPoint joinPoint, int a, int b) {
        Log.d(TAG, "Around!!!!:" + joinPoint.toShortString() + "----" + a + "---" + b);
        try {
            return joinPoint.proceed(new Object[]{a, b});
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}