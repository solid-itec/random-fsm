package org.random.fsm.impl;

import org.random.fsm.*;
import org.random.fsm.entity.StateInstanceTraceEntity;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 *
 */
public class ReverseTransitionSelector implements ITransitionSelector {
    @Override
    public IStateTransition doSelect(StateNode node, ITransitAction callback, ActionContext context)
            throws StateMachineException {
        List<StateInstanceTraceEntity> entities =
                context.getProvider().getTracerDao().selectByInstanceId(context.getStateInstanceId());
        if (!CollectionUtils.isEmpty(entities)) {
            StateInstanceTraceEntity first = entities.get(0);
            if (node.getId().equals(first.getNextNodeInstanceId())) {
                List<IStateTransition> transitions = node.getPrevTransitions();
                for (IStateTransition transition : transitions) {
                    if (transition.getId().equals(callback.getId()) && transition.match(callback, context)) {
                        return transition;
                    }
                }
            }
        }
        return null;
    }
}
