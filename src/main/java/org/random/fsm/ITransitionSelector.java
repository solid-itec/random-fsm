package org.random.fsm;

import org.random.fsm.impl.DefaultTransitionSelector;
import org.random.fsm.impl.NotifyTransitionSelector;
import org.random.fsm.impl.ReverseTransitionSelector;

/**
 *
 */
public interface ITransitionSelector {

    ITransitionSelector DEFAULT = new DefaultTransitionSelector();

    ReverseTransitionSelector REVERSE = new ReverseTransitionSelector();

    NotifyTransitionSelector NOTIFY = new NotifyTransitionSelector();

    /**
     * @param node
     * @param callback
     * @param context
     * @return
     * @throws StateMachineException
     */
    IStateTransition doSelect(StateNode node, ITransitAction callback, ActionContext context)
            throws StateMachineException;
}
