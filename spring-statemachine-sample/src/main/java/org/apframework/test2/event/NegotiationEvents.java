package org.apframework.test2.event;

/**
 * @author by zhujun
 * @since 2019-03-01
 */
public enum NegotiationEvents {

    /**
     * 系统超时
     */
    EXPIRE,

    /**
     * 司机禁止
     */
    CARRIER_FORBID,

    /**
     * 支付定金成功
     */
    DEPOSIT_PAID,

    /**
     * 其他议价单支付定金成功，导致本议价关闭
     */
    CLOSE_FOR_OTHERS_DEPOSIT_PAID,


}
