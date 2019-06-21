package org.random.fsm;

/**
 *
 */
public interface ITransitAction extends IStateObject {

    /**
     * @param context
     * @return
     * @throws StateMachineException
     */
    Object doAction(ActionContext context) throws StateMachineException;
}
