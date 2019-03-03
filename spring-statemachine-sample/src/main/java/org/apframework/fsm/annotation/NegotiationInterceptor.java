package org.apframework.fsm.annotation;

import org.apframework.fsm.NegotiationStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liangwenxing
 * @description
 * @date 2019-03-02
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NegotiationInterceptor {
    NegotiationStatus from() default NegotiationStatus.S1;
    NegotiationStatus to();
}
