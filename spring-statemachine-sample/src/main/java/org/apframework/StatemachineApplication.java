package org.apframework;

import org.apframework.fsm.NegotiationEvents;
import org.apframework.fsm.NegotiationStateMachineService;
import org.apframework.statemachine.StatemachineService;
import org.apframework.statemachine.TurnstileEvents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Shoukai Huang
 * @Date: 2018/9/28 21:12
 */
@SpringBootApplication
public class StatemachineApplication implements CommandLineRunner {

    @Autowired
    private StatemachineService statemachineService;

    @Autowired
    private NegotiationStateMachineService negotiationStateMachineService;

    public static void main(String[] args) {
        SpringApplication.run(StatemachineApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

        Map<String, Object> context = new HashMap<>(16);
        context.put("context", "some code");
//        statemachineService.execute(1, TurnstileEvents.PUSH, context);
//        statemachineService.execute(1, TurnstileEvents.PUSH, context);
//        statemachineService.execute(1, TurnstileEvents.COIN, context);
//        statemachineService.execute(1, TurnstileEvents.COIN, context);

        negotiationStateMachineService.execute(1L, NegotiationEvents.EXPIRE);
//        negotiationStateMachineService.execute(1L, NegotiationEvents.CARRIER_FORBID);
    }

}
