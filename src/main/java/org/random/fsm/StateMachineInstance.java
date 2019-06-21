package org.random.fsm;

import org.random.fsm.event.IStateMachineListener;
import org.random.fsm.event.StateMachineEvent;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class StateMachineInstance {

    // 状态机实例事件监听器
    private List<IStateMachineListener> listeners = new LinkedList<>();

    private StateMachine machine;
    private StateNodeInstance stateNodeInstance;
    private String instanceId;
    private StateMachineInstance parentInstance;
    private boolean available;

    public StateMachine getMachine() {
        return machine;
    }

    public void setMachine(StateMachine machine) {
        this.machine = machine;
    }

    public StateNodeInstance getStateNodeInstance() {
        return stateNodeInstance;
    }

    public void setStateNodeInstance(StateNodeInstance stateNodeInstance) {
        this.stateNodeInstance = stateNodeInstance;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public StateMachineInstance getParentInstance() {
        return parentInstance;
    }

    public void setParentInstance(StateMachineInstance parentInstance) {
        this.parentInstance = parentInstance;
    }

    public void notifyEvent(StateMachineEvent event, ActionContext ctx) {
        for (IStateMachineListener listener : listeners) {
            // log here
            listener.onEvent(event, ctx);
        }
    }

    public void registerListener(IStateMachineListener listener) {
        this.listeners.add(listener);
    }
}
