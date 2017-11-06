package com.qicode.annotationdr.runtimeannotation;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by chenming on 2017/10/31
 * View的注解处理器
 */

public class InjectContentViewAnnotationProcessor implements ProcessorInterface<Class> {
    @Override
    public boolean accept(AnnotatedElement e) {
        return e.isAnnotationPresent(InjectContentView.class);
    }

    /**
     *
     * @param object 属性成员变量所属对象,一般为Activity/Fragment/View
     * @param view
     * @param clazz
     */
    @Override
    public void process(Object object, View view, Class clazz) {
        //1.取到id
        //2.findViewById
        //3.设置field成员变量
        InjectContentView iv = (InjectContentView) clazz.getAnnotation(InjectContentView.class);
        int id = iv.value();
        Log.e("ContentViewId", id+"");
        try {
            if(object instanceof Activity){

                Class[] cArg = new Class[1];
                cArg[0] = int.class;
                Method method = clazz.getMethod("setContentView", cArg);
                method.invoke(object, id);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
