package org.random.fsm.event;

import org.random.fsm.ActionContext;
import org.random.fsm.StateNode;

/**
 *
 */
public interface IStateMachineListener {

    /**
     * @param currentNode
     * @param ctx
     */
    void onNodeActive(StateNode currentNode, ActionContext ctx);

    /**
     * @param currentNode
     * @param ctx
     */
    void onNodeDisable(StateNode currentNode, ActionContext ctx);


    void onEvent(StateMachineEvent event, ActionContext ctx);
}
