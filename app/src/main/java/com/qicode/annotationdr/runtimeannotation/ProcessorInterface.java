package com.qicode.annotationdr.runtimeannotation;

import android.view.View;

import java.lang.reflect.AnnotatedElement;

/**
 * Created by chenming on 2017/10/31
 */

public interface ProcessorInterface<T extends AnnotatedElement> {
    /**
     * @param e 注解的目标类型
     * @return
     */
    boolean accept(AnnotatedElement e);

    /**
     * 反射处理注解元素
     * @param object
     * @param view
     * @param t
     */
    void process(Object object, View view, T t);
}
