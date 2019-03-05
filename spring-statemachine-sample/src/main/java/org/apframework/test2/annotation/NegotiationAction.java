package org.apframework.test2.annotation;

import org.apframework.test2.status.NegotiationStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liangwenxing
 * @description 状态机改变状态后动作
 * @date 2019-03-02
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NegotiationAction {
    NegotiationStatus[] from() default {NegotiationStatus.NEGOTIATING};
    NegotiationStatus to();
}
