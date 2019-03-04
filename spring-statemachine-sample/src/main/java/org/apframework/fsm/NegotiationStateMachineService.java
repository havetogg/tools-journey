package org.apframework.fsm;

import org.apframework.fsm.annotation.NegotiationAction;
import org.apframework.fsm.annotation.NegotiationInterceptor;
import org.apframework.utils.AnnotationUtil;
import org.apframework.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.support.StateMachineInterceptor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 *
 * @description 谈价暴露service
 * @author wenxing.liang
 * @date 2019/3/2
 */
@Service
public class NegotiationStateMachineService {

    private ThreadLocal<StateMachine> stateMachineThreadLocal = new ThreadLocal<StateMachine>();

    @Autowired
    private StateMachinePersister<NegotiationStatus, NegotiationEvents, Long> stateMachinePersist;

    @Autowired
    private StateMachineFactory<NegotiationStatus, NegotiationEvents> stateMachineFactory;

    public boolean execute(Long businessId, NegotiationEvents event) {
        //获取当前线程的stateMachine
        StateMachine<NegotiationStatus, NegotiationEvents> stateMachine = this.getStateMachine();
        //开启状态机器
        stateMachine.start();
        //设置返回值
        boolean result = false;
        try {
            // 在BizStateMachinePersist的restore过程中，绑定turnstileStateMachine状态机相关事件监听
            stateMachinePersist.restore(stateMachine, businessId);
            //stateMachine.getStateMachineAccessor().withRegion().addStateMachineInterceptor(this.getInterceptorByStateMachine(stateMachine.getState().getId(), event));
            // 本处写法较为繁琐，实际为注入Map<String, Object> context内容到message中
            MessageBuilder<NegotiationEvents> messageBuilder = MessageBuilder
                    .withPayload(event)
                    .setHeader("BusinessId", businessId);
            Message<NegotiationEvents> message = messageBuilder.build();

            // 发送事件，返回是否执行成功
            boolean success = stateMachine.sendEvent(message);
            if (success) {
                try{
                    stateMachinePersist.persist(stateMachine, businessId);
                    result = true;
                }catch (RuntimeException r){
                    System.out.println(r.toString());
                }
            } else {
                System.out.println("状态机处理未执行成功，请处理，ID：" + businessId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stateMachine.stop();
            return result;
        }
    }

    /**
     * 获取当前线程的stateMachine,如果没有就新建
     *
     * @return
     */
    private StateMachine<NegotiationStatus, NegotiationEvents> getStateMachine() {
        StateMachine<NegotiationStatus, NegotiationEvents> statusNegotiationEventsStateMachine =
                stateMachineThreadLocal.get();
        if (null == statusNegotiationEventsStateMachine) {
            statusNegotiationEventsStateMachine = stateMachineFactory.getStateMachine("negotiationStateMachine");
            stateMachineThreadLocal.set(statusNegotiationEventsStateMachine);
        }
        return statusNegotiationEventsStateMachine;
    }


    /**
     * 获取event对应的Interceptor
     * @param events
     * @return
     * @throws RuntimeException
     */
    private StateMachineInterceptor<NegotiationStatus, NegotiationEvents> getInterceptorByStateMachine(NegotiationStatus from, NegotiationEvents events)
            throws RuntimeException {
        EventCorrelation eventCorrelation = EventCorrelation.find(from, events);
        if(eventCorrelation == null){
            throw new RuntimeException("没有找到对应的Status!");
        }
        Map<String, Object> beansWithAnnotation = SpringUtil.getBeansWithAnnotation(NegotiationInterceptor.class);
        Object o = null;
        for(Map.Entry<String, Object> entry: beansWithAnnotation.entrySet()){
            NegotiationInterceptor annotation = AnnotationUtil.getAnnotation(entry.getValue(), NegotiationInterceptor.class);
            if(annotation.from() == eventCorrelation.getFrom() && annotation.to() == eventCorrelation.getTo()){
                o = entry.getValue();
            }
        }
        if(null == o|| !(o instanceof  AbstractNegotiationInterceptor)){
            throw new RuntimeException("没有找到对应的Interceptor!");
        }
        return (StateMachineInterceptor<NegotiationStatus, NegotiationEvents>)o;
    }
}
