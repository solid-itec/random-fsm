package org.random.fsm.impl;

import org.random.fsm.*;

import java.util.Iterator;
import java.util.List;

/**
 *
 */
public class DefaultTransitionSelector implements ITransitionSelector {
    @Override
    public IStateTransition doSelect(StateNode node, ITransitAction callback, ActionContext context)
            throws StateMachineException {
        List<IStateTransition> nextTransitions = node.getNextTransitions();
        Iterator<IStateTransition> iterator = nextTransitions.iterator();
        IStateTransition transition;
        while (iterator.hasNext()) {
            transition = iterator.next();
            if (transition.getId().equals(callback.getId())) {
                if (transition.match(callback, context)) {
                    return transition;
                }
            }
        }
        throw new StateMachineException("状态实例在" + node.getId() + "节点不允许执行" + callback.getId() + "操作");
    }
}
