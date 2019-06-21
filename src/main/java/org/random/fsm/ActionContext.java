package org.random.fsm;

import org.random.fsm.event.IStateMachineListener;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public final class ActionContext {

    private List<IStateMachineListener> listeners = new LinkedList<>();

    private String stateInstanceId;

    private String referenceId;

    private String prevStateNodeInstanceId;

    private String prevStateNodeId;

    private int prevStateNodeValue;

    private String nextStateNodeInstanceId;

    private String nextStateNodeId;

    private int nextStateNodeValue;

    private Map parameter;

    private Object result;

    private IContextProvider provider;

    private boolean forward;

    public String getStateInstanceId() {
        return stateInstanceId;
    }

    public void setStateInstanceId(String stateInstanceId) {
        this.stateInstanceId = stateInstanceId;
    }

    public Map getParameter() {
        return parameter;
    }

    public void setParameter(Map parameter) {
        this.parameter = parameter;
    }

    public IContextProvider getProvider() {
        return provider;
    }

    public void setProvider(IContextProvider provider) {
        this.provider = provider;
    }

    public String getPrevStateNodeInstanceId() {
        return prevStateNodeInstanceId;
    }

    public void setPrevStateNodeInstanceId(String prevStateNodeInstanceId) {
        this.prevStateNodeInstanceId = prevStateNodeInstanceId;
    }

    public String getNextStateNodeInstanceId() {
        return nextStateNodeInstanceId;
    }

    public void setNextStateNodeInstanceId(String nextStateNodeInstanceId) {
        this.nextStateNodeInstanceId = nextStateNodeInstanceId;
    }

    public String getPrevStateNodeId() {
        return prevStateNodeId;
    }

    public void setPrevStateNodeId(String prevStateNodeId) {
        this.prevStateNodeId = prevStateNodeId;
    }

    public String getNextStateNodeId() {
        return nextStateNodeId;
    }

    public void setNextStateNodeId(String nextStateNodeId) {
        this.nextStateNodeId = nextStateNodeId;
    }

    public void addEventListener(IStateMachineListener listener) {
        this.listeners.add(listener);
    }

    public int getPrevStateNodeValue() {
        return prevStateNodeValue;
    }

    public void setPrevStateNodeValue(int prevStateNodeValue) {
        this.prevStateNodeValue = prevStateNodeValue;
    }

    public int getNextStateNodeValue() {
        return nextStateNodeValue;
    }

    public void setNextStateNodeValue(int nextStateNodeValue) {
        this.nextStateNodeValue = nextStateNodeValue;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public boolean isForward() {
        return forward;
    }

    public void setForward(boolean forward) {
        this.forward = forward;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }
}
