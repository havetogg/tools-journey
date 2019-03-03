package org.apframework.fsm;

import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class NegotiationStateMachinePersist implements StateMachinePersist<NegotiationStatus, NegotiationEvents, Long> {

    static Map<Long, NegotiationStatus> cache = new HashMap<>(16);

    @Override
    public void write(StateMachineContext<NegotiationStatus, NegotiationEvents> stateMachineContext, Long businessId) throws Exception {
        cache.put(businessId, stateMachineContext.getState());
    }

    @Override
    public StateMachineContext<NegotiationStatus, NegotiationEvents> read(Long businessId) throws Exception {
        // 注意状态机的初识状态与配置中定义的一致
        return cache.containsKey(businessId) ?
                new DefaultStateMachineContext<>(cache.get(businessId), null, null, null, null, "negotiationStateMachine") :
                new DefaultStateMachineContext<>(NegotiationStatus.S1, null, null, null, null, "negotiationStateMachine");
    }
}
