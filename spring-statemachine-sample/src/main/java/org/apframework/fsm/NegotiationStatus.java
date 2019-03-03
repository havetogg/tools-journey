package org.apframework.fsm;

/**
 * @author by zhujun
 * @since 2019-03-01
 */
public enum NegotiationStatus {

    /**
     * 议价中
     */
    S1,

    /**
     * 议价超时
     */
    S2,

    /**
     * 议价成功
     */
    S3,

    /**
     * 订单成交
     */
    S4,

    /**
     * 司机终止
     */
    S5,

    /**
     * 货主终止
     */
    S6,

    /**
     * 议价结束
     */
    S7

}
