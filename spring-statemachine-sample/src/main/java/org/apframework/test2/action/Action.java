package org.apframework.test2.action;

/**
 * 流程结束action
 * @author by wenxing.liang
 * @since 2019-03-05
 */
public interface Action<S, E> {

    boolean doAction() throws Exception;
}
