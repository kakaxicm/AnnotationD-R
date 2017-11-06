package com.qicode.annotationdr;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.qicode.annotationdr.runtimeannotation.ClassInfo;
import com.qicode.annotationdr.runtimeannotation.FieldInfo;
import com.qicode.annotationdr.runtimeannotation.InjectContentView;
import com.qicode.annotationdr.runtimeannotation.InjectOnClick;
import com.qicode.annotationdr.runtimeannotation.InjectView;
import com.qicode.annotationdr.runtimeannotation.MethodInfo;
import com.qicode.annotationdr.runtimeannotation.RuntimeAnnotationClass;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
@InjectContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    /**
     * 动态注入测试
     */
    @InjectView(R.id.tv)
    private TextView testTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        RuntimeInjector.inject(this);
        testTv.setText("动态注入测试");
        testRuntimeAnnotation();
    }

    @InjectOnClick({R.id.tv})
    public void testTvClick(){
        Toast.makeText(this, "hello, tv", Toast.LENGTH_SHORT).show();
    }

    @InjectOnClick({R.id.btn})
    public void testBtnClick(View v){
        Toast.makeText(this, "hello, btn", Toast.LENGTH_SHORT).show();
    }

    /**
     * 通过反射获取运行时注解信息
     */
    private void testRuntimeAnnotation(){
        StringBuffer sb = new StringBuffer();
        Class clazz = RuntimeAnnotationClass.class;
        sb.append("Class注解：").append("\n");
        //获得class的注解
        ClassInfo classInfo = (ClassInfo) clazz.getAnnotation(ClassInfo.class);
        if(classInfo != null){
            sb.append(Modifier.toString(clazz.getModifiers())).append(" ")
                    .append(clazz.getSimpleName()).append("\n");
            sb.append("注解值: ").append(classInfo.value()).append("\n\n");
        }
        sb.append("Field注解：").append("\n");
        //获得属性上的注解
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            FieldInfo fieldInfo = field.getAnnotation(FieldInfo.class);
            if (fieldInfo != null) {
                sb.append(Modifier.toString(field.getModifiers())).append(" ")
                        .append(field.getType().getSimpleName()).append(" ")
                        .append(field.getName()).append("\n");
                sb.append("注解值: ").append(Arrays.toString(fieldInfo.value())).append("\n\n");
            }
        }
        //获得方法上的注解
        sb.append("Method注解：").append("\n");
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            MethodInfo methodInfo = method.getAnnotation(MethodInfo.class);
            if (methodInfo != null) {
                sb.append(Modifier.toString(method.getModifiers())).append(" ")
                        .append(method.getReturnType().getSimpleName()).append(" ")
                        .append(method.getName()).append("\n");
                sb.append("注解值: ").append("\n");
                sb.append("name: ").append(methodInfo.name()).append("\n");
                sb.append("data: ").append(methodInfo.data()).append("\n");
                sb.append("age: ").append(methodInfo.age()).append("\n");
            }
        }
        testTv.setText(sb.toString());
        Log.e("tag", sb.toString());

    }

}
