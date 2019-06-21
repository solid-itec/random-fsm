package org.random.fsm;

/**
 *
 */
public interface IStateTransition extends IStateObject {

    StateNode getNextStateNode();

    StateNode getPrevStateNode();

    /**
     * @param callback
     * @param context
     * @return
     * @throws StateMachineException
     */
    boolean match(ITransitAction callback, ActionContext context) throws StateMachineException;

    /**
     * @param callback
     * @param context
     * @return
     * @throws StateMachineException
     */
    void transit(ITransitAction callback, ActionContext context) throws StateMachineException;
}
