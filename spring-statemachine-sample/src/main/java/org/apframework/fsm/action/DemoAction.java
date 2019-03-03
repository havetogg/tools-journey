package org.apframework.fsm.action;

import org.apframework.fsm.NegotiationEvents;
import org.apframework.fsm.NegotiationStatus;
import org.apframework.fsm.annotation.NegotiationAction;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component()
@NegotiationAction(from = NegotiationStatus.S1, to = NegotiationStatus.S2)
public class DemoAction implements Action<NegotiationStatus, NegotiationEvents> {

    @Override
    public void execute(StateContext<NegotiationStatus, NegotiationEvents> stateContext) {
        System.out.println("S2 Action");
    }
}
