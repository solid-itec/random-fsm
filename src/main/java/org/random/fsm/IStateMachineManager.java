package org.random.fsm;

import org.random.fsm.event.StateEventType;

/**
 *
 */
public interface IStateMachineManager extends IContextProvider {

    /**
     * @param stateMachineId
     * @return
     * @throws StateMachineException
     */
    StateMachineInstance createMachineInstance(String stateMachineId) throws StateMachineException;

    /**
     * @param stateMachineId
     * @param parentInstanceId
     * @param referenceId
     * @return
     * @throws StateMachineException
     */
    StateMachineInstance createMachineInstance(String stateMachineId, String parentInstanceId, String referenceId)
            throws StateMachineException;

    /**
     * @param stateMachineId
     * @param parentInstanceId
     * @return
     * @throws StateMachineException
     */
    StateMachineInstance createMachineInstance(String stateMachineId, String parentInstanceId)
            throws StateMachineException;

    /**
     * @param stateMachineInstanceId
     * @return
     * @throws StateMachineException
     */
    StateMachineInstance retrieveMachineInstance(String stateMachineInstanceId) throws StateMachineException;

    /**
     * @param source
     * @param type
     * @param context
     */
    void notifyStateEvent(StateNodeInstance source, StateEventType type, ActionContext context);

    //	/**
    //	 * @param machineInstance
    //	 * @param listeners
    //	 */
    //	void registerEventListener(StateMachineInstance machineInstance, List<IStateMachineListener> listeners);

}
