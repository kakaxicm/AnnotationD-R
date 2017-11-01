package com.qicode.annotationdr;

import android.app.Activity;
import android.view.View;

import com.qicode.annotationdr.runtimeannotation.InjectViewAnnotationProcessor;
import com.qicode.annotationdr.runtimeannotation.ProcessorInterface;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chenming on 2017/11/1
 */

public class RuntimeInjector {
    //支持的注解处理器
    private static List<? extends ProcessorInterface<? extends AnnotatedElement>> mProcesses = Arrays.asList(
            new InjectViewAnnotationProcessor()
    );

    /**
     * Activity注入
     * @param act
     */
    public static void inject(Activity act) {
        inject(act,act.getWindow().getDecorView());
    }

    /**
     * Obj注入
     * @param obj
     * @param rootView
     */
    public static void inject(Object obj, View rootView){
        //Field注入
        //1.拿到field列表
        //2.遍历field列表，做process
        final Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            process(obj, field, rootView);
        }
        //TODO onClick方法注入
    }

    private static void process(Object obj, Field field, View rootView) {
        for(ProcessorInterface process : mProcesses){
            if(process.accept(field)){
                process.process(obj,rootView, field);
            }
        }
    }
}
