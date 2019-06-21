package org.random.fsm.impl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.random.fsm.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;

/**
 *
 */
public final class TransitionActionInterceptor implements Ordered {

    private static final Logger logger = LoggerFactory.getLogger(TransitionActionInterceptor.class);

    private IStateMachineManager manager;

    /**
     * @param point
     * @return
     */
    public Object doForward(ProceedingJoinPoint point) {
        // 获取被代理的com.ymm.saas.tms.fsm.ITransitionAction对象实例
        Object target = point.getTarget();
        ITransitAction action = (ITransitAction) target;
        // 获取调用参数
        //Object args = point.getArgs()[0];
        // 判断被方法拦截的方法名及参数类型
        // 其实可以不需要判断(在spring-fsm.xml中已经限定)
        ActionContext context = (ActionContext) point.getArgs()[0];
        context.setForward(true);
        String stateInstanceId = context.getStateInstanceId();
        String referenceId = context.getReferenceId();
        if (!StringUtils.isEmpty(stateInstanceId) || !StringUtils.isEmpty(referenceId)) {
            // 跟进状态机实例ID构造实例对象
            StateMachineInstance machineInstance;
            machineInstance = getManager().retrieveMachineInstance(stateInstanceId);
            if (!machineInstance.isAvailable()) {
                throw new StateMachineException("状态机实例[id=" + machineInstance.getInstanceId() + "]已处于最终状态无法继续流转");
            }
            StateNodeInstance prev = machineInstance.getStateNodeInstance();
            context.setProvider(getManager());
            StateNodeInstance next = prev.forward(action, context);
            getManager().getInstanceDao()
                    .updateByInstanceId(IContextProvider.createInstanceEntity(machineInstance, next));
            return context.getResult();
        }
        throw new StateMachineException("状态机实例id为空");
    }

    public Object doReverse(ProceedingJoinPoint point) {
        Object target = point.getTarget();
        ITransitAction reversible = (ITransitAction) target;
        ActionContext context = (ActionContext) point.getArgs()[0];
        context.setForward(false);
        String stateInstanceId = context.getStateInstanceId();
        if (!StringUtils.isEmpty(stateInstanceId)) {
            // 跟进状态机实例ID构造实例对象
            StateMachineInstance machineInstance = getManager().retrieveMachineInstance(stateInstanceId);
            StateNodeInstance prev = machineInstance.getStateNodeInstance();
            if (prev.getStateNode() instanceof FinalStateNode && !machineInstance.isAvailable()) {
                // 当前状态机实例已经完成的情况下需要先回复状态机有效性
                machineInstance.setAvailable(true);
            }
            StateNodeInstance next;
            context.setProvider(getManager());
            if (prev.getStateNode() instanceof InitialStateNode && machineInstance.isAvailable()) {
                // 撤销当前状态机实例
                machineInstance.setAvailable(false);
                // 下个状态节点依旧为当前本身
                next = prev;
            } else {
                // 回退
                next = prev.reverse(reversible, context);
            }
            getManager().getInstanceDao()
                    .updateByInstanceId(IContextProvider.createInstanceEntity(machineInstance, next));
            return context.getResult();
        }
        throw new StateMachineException("状态机实例id为空");
    }

    @Override
    public int getOrder() {
        //使当前拦截器对象被框架最后加载即在AOP的最里层
        // 如此可以将事务的上下文围绕在整个请求的最外层
        return Integer.MAX_VALUE;
    }

    public IStateMachineManager getManager() {
        return manager;
    }

    public void setManager(IStateMachineManager manager) {
        this.manager = manager;
    }
}
