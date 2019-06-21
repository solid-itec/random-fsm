package org.random.fsm.impl;

import org.random.fsm.*;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 */
public class NotifyTransitionSelector implements ITransitionSelector {
    @Override
    public IStateTransition doSelect(StateNode node, ITransitAction callback, ActionContext context)
            throws StateMachineException {
        if (callback != null && !StringUtils.isEmpty(callback.getId())) {
            throw new StateMachineException("自动流转模式下不可指定actionId");
        }
        Set<String> names = new HashSet<>();
        List<IStateTransition> transitions = node.getNextTransitions();
        for (IStateTransition transition : transitions) {
            names.add(transition.getId());
        }
        // 判断如果当前状态机节点的后继流转为[单向输出]或者[相同动作的多向输出]则可以进行自动匹配
        if (names.size() == 1) {
            // 如果单向输出则直接选择
            if (transitions.size() == 1 && node.isAutoDisable()) {
                return transitions.get(0);
            } else {
                for (IStateTransition transition : transitions) {
                    //如果为多向输出则根据参数选择匹配
                    if (transition.getNextStateNode().isAutoActive() && transition.match(callback, context)) {
                        return transition;
                    }
                }
                throw new StateMachineException(
                        "状态实例在" + node.getId() + "节点不允许执行" + names.iterator().next() + "操作[条件参数不匹配]");
            }
        }
        throw new StateMachineException("状态实例在" + node.getId() + "节点存在多路输出不允许自动流转");

    }
}
