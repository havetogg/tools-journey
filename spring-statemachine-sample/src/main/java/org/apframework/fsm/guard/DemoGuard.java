package org.apframework.fsm.guard;

import org.apframework.fsm.NegotiationEvents;
import org.apframework.fsm.NegotiationStatus;
import org.apframework.fsm.annotation.NegotiationGuard;
import org.apframework.fsm.annotation.NegotiationInterceptor;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

/**
 * @author liangwenxing
 * @description
 * @date 2019-03-03
 */
@Component
@NegotiationGuard(from = NegotiationStatus.S1, to = NegotiationStatus.S2)
public class DemoGuard implements Guard<NegotiationStatus, NegotiationEvents> {
    @Override
    public boolean evaluate(StateContext<NegotiationStatus, NegotiationEvents> stateContext) {
        return true;
    }
}
