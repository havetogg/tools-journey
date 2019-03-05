package org.apframework.test2.status;

import java.util.Objects;

/**
 * @author by wenxing.liang
 * @since 2019-03-05
 */
public enum NegotiationStatus {

    /**
     * 议价中
     */
    NEGOTIATING(1),

    /**
     * 议价超时
     */
    NEGOTIATION_TIMEOUT(2),

    /**
     * 议价成功
     */
    NEGOTIATION_SUCCESS(3),

    /**
     * 订单成交
     */
    ORDER_GENERATED(4),

    /**
     * 司机终止
     */
    CARRIER_TERMINATED(5),

    /**
     * 货主终止
     */
    CONSIGNOR_TERMINATED(6),

    /**
     * 议价结束
     */
    NEGOTIATION_CLOSE(7);

    private Integer index;

    NegotiationStatus(Integer index) {
        this.index = index;
    }

    public Integer getIndex() {
        return index;
    }

    public static NegotiationStatus getByIndex(Integer index){
        for(NegotiationStatus n : NegotiationStatus.values()){
            if(Objects.equals(index, n.getIndex())){
                return n;
            }
        }
        return null;
    }
}
