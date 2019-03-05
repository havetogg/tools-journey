package org.apframework.test2.guard;


/**
 * @author liangwenxing
 * @description
 * @date 2019-03-05
 */
public interface Guard<S, E> {
    boolean evaluate() throws Exception;
}
