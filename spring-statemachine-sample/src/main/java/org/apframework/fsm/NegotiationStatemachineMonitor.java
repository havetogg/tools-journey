package org.apframework.fsm;

import org.springframework.statemachine.annotation.OnStateChanged;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

@WithStateMachine(id = "negotiationStateMachine")
public class NegotiationStatemachineMonitor {

    @OnTransition
    public void anyTransition() {
        System.out.println("--- OnTransition --- init");
    }

    @OnTransition(target = "S2")
    public void toState1() {
        System.out.println("--- OnTransition --- toState1");
    }

    @OnTransition(target = "S2")
    public void toState2() {
        System.out.println("--- OnTransition --- toState2");
    }

    @OnStateChanged(source = "S2")
    public void fromState1() {
        System.out.println("--- OnTransition --- fromState1");
    }
}

