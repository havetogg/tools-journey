package org.apframework.fsm;

/**
 *
 * @description 状态机流程
 * @author wenxing.liang
 * @date 2019/3/2
 */
public enum EventCorrelation {

    EXPIRE(NegotiationStatus.S1, NegotiationEvents.EXPIRE, NegotiationStatus.S2);
    //CARRIER_FORBID(NegotiationStatus.S1, NegotiationEvents.CARRIER_FORBID, NegotiationStatus.S5);


    private NegotiationStatus from;

    private NegotiationEvents event;

    private NegotiationStatus to;

    public static EventCorrelation find(NegotiationStatus from, NegotiationEvents event){
        for(EventCorrelation e : EventCorrelation.values()){
            if(e.from == from && e.event == event){
                return e;
            }
        }
        return null;
    }

    EventCorrelation(NegotiationStatus from, NegotiationEvents event, NegotiationStatus to) {
        this.from = from;
        this.event = event;
        this.to = to;
    }

    public NegotiationStatus getFrom() {
        return from;
    }

    public NegotiationEvents getEvent() {
        return event;
    }

    public NegotiationStatus getTo() {
        return to;
    }}
