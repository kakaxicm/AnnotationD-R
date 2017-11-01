package com.qicode.annotationdr.runtimeannotation;

import android.view.View;

import java.lang.ref.WeakReference;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by chenming on 2017/11/1
 * Onclick方法注解处理器
 */

public class InjectOnclickAnnotationProcessor implements ProcessorInterface<Method> {
    @Override
    public boolean accept(AnnotatedElement e) {
        return e.isAnnotationPresent(InjectOnClick.class);
    }

    /**
     * @param object 注入方法的所属类对象
     * @param view   根View
     * @param method 注入方法
     */
    @Override
    public void process(Object object, View view, Method method) {
        InjectOnClick onClick = method.getAnnotation(InjectOnClick.class);
        final int[] value = onClick.value();
        for (int id : value) {
            View v = view.findViewById(id);
            v.setOnClickListener(new InvokeOnClickListener(object, method));
        }
    }

    /**
     * OnClick监听中间件,通过反射绑定
     */
    private static class InvokeOnClickListener implements View.OnClickListener {
        private Method method;
        public WeakReference<Object> obj; //标记方法所属对象
        private boolean hasParam;

        InvokeOnClickListener(Object object, Method m) {
            method = m;
            this.obj = new WeakReference<Object>(object);
            //方法参数只能接受View 0/1个参数
            Class<?>[] parameterTypes = m.getParameterTypes();
            if (parameterTypes == null || parameterTypes.length == 0) {
                hasParam = false;
            } else if (parameterTypes.length > 1 || !View.class.isAssignableFrom(parameterTypes[0])) {
                throw new IllegalArgumentException(String.format("%s方法只能拥有0个或一个参数，且只接收View", m.getName()));
            } else {
                hasParam = true;
            }
        }

        @Override
        public void onClick(View v) {
            Object o = obj.get();
            if (o != null) {
                try {
                    if (hasParam) {
                        method.invoke(o, v);
                    } else {
                        method.invoke(o);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
