package com.qicode.annotationdr.runtimeannotation;

/**
 * Created by chenming on 2017/10/31
 */
@ClassInfo("Test Class")
public class RuntimeAnnotationClass {
    @FieldInfo(value = {1, 2})
    public String fieldInfo = "FiledInfo";

    @FieldInfo(value = {10086})
    public int i = 100;

    @MethodInfo(name = "BlueBird", data = "Big")
    public static String getMethodInfo() {
        return RuntimeAnnotationClass.class.getSimpleName();
    }
}
