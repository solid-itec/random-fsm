package org.random.fsm;

import org.random.fsm.dao.StateEventListenerDao;
import org.random.fsm.dao.StateInstanceTraceDao;
import org.random.fsm.dao.StateMachineInstanceDao;
import org.random.fsm.entity.StateInstanceTraceEntity;
import org.random.fsm.entity.StateMachineInstanceEntity;

/**
 *
 */
public interface IContextProvider {

    /**
     * @param stateMachineInstance
     * @param stateNodeInstance
     * @return
     */
    static StateMachineInstanceEntity createInstanceEntity(StateMachineInstance stateMachineInstance,
            StateNodeInstance stateNodeInstance) {
        StateMachineInstanceEntity entity = new StateMachineInstanceEntity();
        entity.setInstanceId(stateMachineInstance.getInstanceId());
        entity.setNodeId(stateNodeInstance.getStateNode().getId());
        entity.setAvailable(stateMachineInstance.isAvailable());
        entity.setNodeInstanceId(stateNodeInstance.getId());
        entity.setNodeValue(stateNodeInstance.getStateNode().getValue());
        entity.setMachineId(stateMachineInstance.getMachine().getId());
        entity.setVersion(stateMachineInstance.getMachine().getVersion());
        if (stateMachineInstance.getParentInstance() != null) {
            entity.setParentInstanceId(stateMachineInstance.getParentInstance().getInstanceId());
        }
        return entity;
    }

    /**
     * @param stateMachineInstanceId
     * @param prevStateNodeInstanceId
     * @param prevStateNodeId
     * @param nextStateNodeInstanceId
     * @param nextStateNodeId
     * @param parameter
     * @return
     */
    static StateInstanceTraceEntity createTraceEntity(String stateMachineInstanceId, String prevStateNodeInstanceId,
            String prevStateNodeId, String nextStateNodeInstanceId, String nextStateNodeId, String parameter) {
        StateInstanceTraceEntity entity = new StateInstanceTraceEntity();
        entity.setMachineInstanceId(stateMachineInstanceId);
        entity.setPrevNodeInstanceId(prevStateNodeInstanceId);
        entity.setPrevNodeId(prevStateNodeId);
        entity.setNextNodeInstanceId(nextStateNodeInstanceId);
        entity.setNextNodeId(nextStateNodeId);
        entity.setParameter(parameter);
        return entity;
    }

    /**
     * @return
     */
    StateInstanceTraceDao getTracerDao();

    StateMachineInstanceDao getInstanceDao();

    StateEventListenerDao getListenerDao();

    /**
     * @return
     */
    IActionRegister getRegister();
}
