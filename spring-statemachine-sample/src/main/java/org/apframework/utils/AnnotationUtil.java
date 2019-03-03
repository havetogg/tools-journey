package org.apframework.utils;

import java.lang.annotation.Annotation;

/**
 * @author liangwenxing
 * @description
 * @date 2019-03-02
 */
public class AnnotationUtil {

    public static <T, A extends Annotation> A getAnnotation(T t, Class<A> aClz){
        Class<?> clz = t.getClass();
        boolean annotationPresent = clz.isAnnotationPresent(aClz);
        if(annotationPresent){
            A a = clz.getAnnotation(aClz);
            return a;
        }
        return null;
    }
}
