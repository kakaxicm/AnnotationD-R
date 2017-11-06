package com.qicode.annotationdr.runtimeannotation;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by chenming on 2017/10/31
 * 通过运行时注解findViewById
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface InjectContentView {
    @LayoutRes int value(); //value为ContentView Id
}