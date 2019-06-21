package org.random.fsm.event;

/**
 *
 */
public class StateMachineEvent {

    private Object source;

    private StateEventType type;

    public StateEventType getType() {
        return type;
    }

    public void setType(StateEventType type) {
        this.type = type;
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }
}
