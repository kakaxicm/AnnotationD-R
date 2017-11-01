package com.qicode.annotationdr.runtimeannotation;

import android.util.Log;
import android.view.View;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;

/**
 * Created by chenming on 2017/10/31
 * View的注解处理器
 */

public class InjectViewAnnotationProcessor implements ProcessorInterface<Field> {
    @Override
    public boolean accept(AnnotatedElement e) {
        return e.isAnnotationPresent(InjectView.class);
    }

    /**
     *
     * @param object 属性成员变量所属对象,一般为Activity/Fragment/View
     * @param view
     * @param field
     */
    @Override
    public void process(Object object, View view, Field field) {
        //1.取到id
        //2.findViewById
        //3.设置field成员变量
        InjectView iv = field.getAnnotation(InjectView.class);
        int id = iv.value();
        View v = view.findViewById(id);
        field.setAccessible(true);
        try {
            field.set(object, v);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Log.e("tag", "没有该属性");
        }

    }
}
