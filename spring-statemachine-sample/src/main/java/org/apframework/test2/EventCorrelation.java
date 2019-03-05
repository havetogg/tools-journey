package org.apframework.test2;

import org.apframework.test2.event.NegotiationEvents;
import org.apframework.test2.status.NegotiationStatus;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @description 状态机流程
 * @author wenxing.liang
 * @date 2019/3/2
 */
public enum EventCorrelation {

    EXPIRE(Arrays.asList(NegotiationStatus.NEGOTIATING), NegotiationEvents.EXPIRE, NegotiationStatus.NEGOTIATION_TIMEOUT),
    //CARRIER_FORBID(NegotiationStatus.S1, NegotiationEvents.CARRIER_FORBID, NegotiationStatus.S5);
    DEPOSIT_PAID(Arrays.asList(NegotiationStatus.NEGOTIATION_SUCCESS), NegotiationEvents.DEPOSIT_PAID, NegotiationStatus.ORDER_GENERATED),
    CLOSE_FOR_OTHERS_DEPOSIT_PAID(Arrays.asList(NegotiationStatus.NEGOTIATION_SUCCESS), NegotiationEvents.CLOSE_FOR_OTHERS_DEPOSIT_PAID, NegotiationStatus.NEGOTIATION_CLOSE),

    ;

    private List<NegotiationStatus> from;

    private NegotiationEvents event;

    private NegotiationStatus to;

    public static EventCorrelation find(NegotiationStatus from, NegotiationEvents event) {
        for (EventCorrelation e : EventCorrelation.values()) {
            if (e.from.contains(from) &&e.event == event){
                return e;
            }
        }
        return null;
    }

    EventCorrelation(List<NegotiationStatus> from, NegotiationEvents event, NegotiationStatus to) {
        this.from = from;
        this.event = event;
        this.to = to;
    }

    public List<NegotiationStatus> getFrom() {
        return from;
    }

    public NegotiationEvents getEvent() {
        return event;
    }

    public NegotiationStatus getTo() {
        return to;
    }

}
