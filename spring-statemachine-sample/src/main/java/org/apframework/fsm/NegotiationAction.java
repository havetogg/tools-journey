package org.apframework.fsm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.annotation.OnTransitionEnd;
import org.springframework.statemachine.annotation.WithStateMachine;

/**
 * @author by zhujun
 * @since 2019-03-02
 */
@WithStateMachine(id = "negotiationStateMachine")
public class NegotiationAction {

    private static Logger logger = LoggerFactory.getLogger(NegotiationAction.class);

    @OnTransitionEnd(source = "S1", target = "S2")
    public void expire() {
        logger.info("Action expire");
    }

}
