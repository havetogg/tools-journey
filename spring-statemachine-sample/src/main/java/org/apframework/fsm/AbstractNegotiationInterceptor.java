package org.apframework.fsm;

import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptor;
import org.springframework.statemachine.transition.Transition;

/**
 *
 * @description 拦截器抽象类
 * @author wenxing.liang
 * @date 2019/3/2
 */
public abstract class AbstractNegotiationInterceptor implements StateMachineInterceptor<NegotiationStatus, NegotiationEvents> {

    @Override
    public abstract Message<NegotiationEvents> preEvent(Message<NegotiationEvents> message, StateMachine<NegotiationStatus, NegotiationEvents> stateMachine);

    @Override
    public void preStateChange(State<NegotiationStatus, NegotiationEvents> state, Message<NegotiationEvents> message, Transition<NegotiationStatus, NegotiationEvents> transition, StateMachine<NegotiationStatus, NegotiationEvents> stateMachine) {

    }

    @Override
    public void postStateChange(State<NegotiationStatus, NegotiationEvents> state, Message<NegotiationEvents> message, Transition<NegotiationStatus, NegotiationEvents> transition, StateMachine<NegotiationStatus, NegotiationEvents> stateMachine) {

    }

    @Override
    public StateContext<NegotiationStatus, NegotiationEvents> preTransition(StateContext<NegotiationStatus, NegotiationEvents> stateContext) {
        return stateContext;
    }

    @Override
    public StateContext<NegotiationStatus, NegotiationEvents> postTransition(StateContext<NegotiationStatus, NegotiationEvents> stateContext) {
        return stateContext;
    }

    @Override
    public Exception stateMachineError(StateMachine<NegotiationStatus, NegotiationEvents> stateMachine, Exception e) {
        return e;
    }
}
