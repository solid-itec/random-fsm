package org.random.fsm;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public abstract class StateNode implements IStateObject {

    protected final List<IStateTransition> nextTransitions = new LinkedList<>();
    protected final List<IStateTransition> prevTransitions = new LinkedList<>();
    private String id;

    //	private final List<IStateMachineListener> listeners = new LinkedList<>();
    private int value;

    // 是否允许自动进入
    private boolean autoActive = true;

    // 是否允许自动退出
    private boolean autoDisable = true;

    // 进入时是否通知父状态机实例
    private boolean notifyOnActive;

    // 退出时是否通知父状态机实例
    private boolean notifyOnDisable;

    public List<IStateTransition> getNextTransitions() {
        return this.nextTransitions;
    }

    public List<IStateTransition> getPrevTransitions() {
        return this.prevTransitions;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @param transition
     */
    public abstract void addNextTransition(IStateTransition transition);

    /**
     * @param transition
     */
    public abstract void addPrevTransition(IStateTransition transition);

    //	public List<IStateMachineListener> getListeners() {
    //		return listeners;
    //	}
    //
    //	public void registEventListener(IStateMachineListener listener) {
    //		listeners.add(listener);
    //	}
    //
    public boolean isAutoActive() {
        return autoActive;
    }

    public void setAutoActive(boolean autoActive) {
        this.autoActive = autoActive;
    }

    public boolean isAutoDisable() {
        return autoDisable;
    }

    public void setAutoDisable(boolean autoDisable) {
        this.autoDisable = autoDisable;
    }

    public boolean isNotifyOnActive() {
        return notifyOnActive;
    }

    public void setNotifyOnActive(boolean notifyOnActive) {
        this.notifyOnActive = notifyOnActive;
    }

    public boolean isNotifyOnDisable() {
        return notifyOnDisable;
    }

    public void setNotifyOnDisable(boolean notifyOnDisable) {
        this.notifyOnDisable = notifyOnDisable;
    }
}
