package org.apframework.fsm.Interceptor;

import org.apframework.fsm.AbstractNegotiationInterceptor;
import org.apframework.fsm.NegotiationEvents;
import org.apframework.fsm.NegotiationStatus;
import org.apframework.fsm.annotation.NegotiationInterceptor;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

@Component()
@NegotiationInterceptor(from = NegotiationStatus.S1, to = NegotiationStatus.S2)
public class DemoInterceptor extends AbstractNegotiationInterceptor {
    @Override
    public Message<NegotiationEvents> preEvent(Message<NegotiationEvents> message, StateMachine<NegotiationStatus, NegotiationEvents> stateMachine) {
        return message;
    }
}
