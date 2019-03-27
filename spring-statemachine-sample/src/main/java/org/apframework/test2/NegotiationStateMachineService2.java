package org.apframework.test2;


import org.apframework.test2.action.Action;
import org.apframework.test2.annotation.NegotiationAction;
import org.apframework.test2.annotation.NegotiationGuard;
import org.apframework.test2.event.NegotiationEvents;
import org.apframework.test2.guard.Guard;
import org.apframework.test2.status.NegotiationStatus;
import org.apframework.utils.AnnotationUtil;
import org.apframework.utils.SpringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class NegotiationStateMachineService2 {


    @Transactional(rollbackFor = Exception.class)
    public NegotiationStatus execute(Long businessId, NegotiationStatus status, NegotiationEvents events) throws Exception {
        NegotiationStatus initStatus = status;
        //查询关系
        EventCorrelation eventCorrelation = EventCorrelation.find(status, events);
        if (eventCorrelation == null) {
            throw new RuntimeException("没有查询到对应的状态机迁移事件");
        }
        //当前当前状态，和event对应的条件
        Guard<NegotiationStatus, NegotiationEvents> guard = getGuardByStatus(eventCorrelation.getFrom(), eventCorrelation.getTo());
        //判断条件能不能执行
        if (null != guard && !guard.evaluate()) {
            throw new RuntimeException("当前状态机前置条件无法执行");
        }
        //getAction
        Action<NegotiationStatus, NegotiationEvents> action = getActionByStatus(eventCorrelation.getFrom(), eventCorrelation.getTo());
        if (null != action && !action.doAction()) {
            throw new RuntimeException("当前Action执行失败");
        }
        //更新数据库
        updateByState(businessId, status, eventCorrelation);
        //改变状态
        initStatus = eventCorrelation.getTo();
        return initStatus;
    }

    private void updateByState(Long businessId, NegotiationStatus status, EventCorrelation eventCorrelation) {
        return;
    }

    /**
     * 获取对应的Guard
     *
     * @param from
     * @param to
     * @return
     */
    private Guard<NegotiationStatus, NegotiationEvents> getGuardByStatus(List<NegotiationStatus> from, NegotiationStatus to) {
        Map<String, Object> beansWithAnnotation = SpringUtil.getBeansWithAnnotation(NegotiationGuard.class);
        Object o = null;
        for (Map.Entry<String, Object> entry : beansWithAnnotation.entrySet()) {
            NegotiationGuard annotation = AnnotationUtil.getAnnotation(entry.getValue(), NegotiationGuard.class);
            if (from.containsAll(Arrays.asList(annotation.from())) && annotation.to() == to) {
                o = entry.getValue();
                break;
            }
        }
        if (null == o || !(o instanceof Guard)) {
            //throw new RuntimeException("没有找到对应的Guard!");
            o = null;
        }
        return (Guard<NegotiationStatus, NegotiationEvents>) o;
    }

    /**
     * 获取event对应的Action
     *
     * @param from
     * @param to
     * @return
     * @throws RuntimeException
     */
    private Action<NegotiationStatus, NegotiationEvents> getActionByStatus(List<NegotiationStatus> from, NegotiationStatus to)
            throws RuntimeException {
        Map<String, Object> beansWithAnnotation = SpringUtil.getBeansWithAnnotation(NegotiationAction.class);
        Object o = null;
        for (Map.Entry<String, Object> entry : beansWithAnnotation.entrySet()) {
            NegotiationAction annotation = AnnotationUtil.getAnnotation(entry.getValue(), NegotiationAction.class);
            if (from.containsAll(Arrays.asList(annotation.from())) && annotation.to() == to) {
                o = entry.getValue();
                break;
            }
        }
        if (null == o || !(o instanceof Action)) {
            //throw new RuntimeException("没有找到对应的Action!");
            o = null;
        }
        return (Action<NegotiationStatus, NegotiationEvents>) o;
    }

}
