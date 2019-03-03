package org.apframework.fsm;

import org.apframework.fsm.annotation.NegotiationAction;
import org.apframework.fsm.annotation.NegotiationGuard;
import org.apframework.utils.AnnotationUtil;
import org.apframework.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

import java.util.EnumSet;
import java.util.Map;

import static org.apframework.fsm.NegotiationStatus.*;


/**
 * 谈价配置
 * @author by zhujun
 * @since 2019-03-01
 */
@Configuration
@EnableStateMachineFactory(name = "negotiationStateMachineFactory")
@DependsOn("springUtil")
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<NegotiationStatus, NegotiationEvents> {

    @Autowired
    private NegotiationStateMachinePersist negotiationStateMachinePersist;

    @Bean(name = "negotiationStateStateMachinePersister")
    public StateMachinePersister<NegotiationStatus, NegotiationEvents, Long> stateMachinePersist() {
        return new DefaultStateMachinePersister<>(negotiationStateMachinePersist);
    }

    /**
     * 初始化状态
     *
     * @param states
     * @throws Exception
     */
    @Override
    public void configure(StateMachineStateConfigurer<NegotiationStatus, NegotiationEvents> states) throws Exception {
        states.withStates()
                .initial(S1)
                .end(S4).end(S7)
                .states(EnumSet.allOf(NegotiationStatus.class));
    }


    /**
     * 迁移过程
     *
     * @param transitions
     * @throws Exception
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<NegotiationStatus, NegotiationEvents> transitions) throws Exception {
        this.registerProcess(transitions);
    }


    @Override
    public void configure(StateMachineConfigurationConfigurer<NegotiationStatus, NegotiationEvents> config) throws Exception {
        config.withConfiguration().autoStartup(false).machineId("negotiationStateMachine");
    }

    /**
     * 注册所有的流程
     * @param transitions
     */
    private void registerProcess(StateMachineTransitionConfigurer<NegotiationStatus, NegotiationEvents> transitions) throws Exception {
        for (EventCorrelation e : EventCorrelation.values()) {
            Action<NegotiationStatus, NegotiationEvents> action = this.getActionByStatus(e.getFrom(), e.getTo());
            Guard<NegotiationStatus, NegotiationEvents> guard = this.getGuardByStatus(e.getFrom(), e.getTo());
            if(action == null || guard== null){
                return;
            }
            transitions = transitions.withExternal().source(e.getFrom()).target(e.getTo()).event(e.getEvent()).
                    action(action).guard(guard).and();
        }
    }


    /**
     * 获取event对应的Action
     * @param from
     * @param to
     * @return
     * @throws RuntimeException
     */
    private Action<NegotiationStatus, NegotiationEvents> getActionByStatus(NegotiationStatus from, NegotiationStatus to)
            throws RuntimeException {
        Map<String, Object> beansWithAnnotation = SpringUtil.getBeansWithAnnotation(NegotiationAction.class);
        Object o = null;
        for(Map.Entry<String, Object> entry: beansWithAnnotation.entrySet()){
            NegotiationAction annotation = AnnotationUtil.getAnnotation(entry.getValue(), NegotiationAction.class);
            if(annotation.from() == from && annotation.to() == to){
                o = entry.getValue();
            }
        }
        if(null == o||!(o instanceof  Action)){
            //throw new RuntimeException("没有找到对应的Action!");
            o = null;
        }
        return (Action<NegotiationStatus, NegotiationEvents>)o;
    }

    /**
     * 获取对应的Guard
     * @param from
     * @param to
     * @return
     */
    private Guard<NegotiationStatus, NegotiationEvents> getGuardByStatus(NegotiationStatus from, NegotiationStatus to) {
        Map<String, Object> beansWithAnnotation = SpringUtil.getBeansWithAnnotation(NegotiationGuard.class);
        Object o = null;
        for(Map.Entry<String, Object> entry: beansWithAnnotation.entrySet()){
            NegotiationGuard annotation = AnnotationUtil.getAnnotation(entry.getValue(), NegotiationGuard.class);
            if(annotation.from() == from && annotation.to() == to){
                o = entry.getValue();
            }
        }
        if(null == o||!(o instanceof  Guard)){
            //throw new RuntimeException("没有找到对应的Guard!");
            o = null;
        }
        return (Guard<NegotiationStatus, NegotiationEvents>)o;
    }
}
