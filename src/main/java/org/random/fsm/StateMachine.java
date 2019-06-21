package org.random.fsm;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class StateMachine implements IStateObject {

    private String id;

    private StateNode initialNode;

    private int version;

    private Map<String, StateNode> stateNodes = new HashMap<>();

    private ITransitionSelector selector;

    public StateNode getInitialNode() {
        return initialNode;
    }

    public void setInitialNode(StateNode initialNode) {
        this.initialNode = initialNode;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }


    public ITransitionSelector getSelector() {
        return selector;
    }

    public void setSelector(ITransitionSelector selector) {
        this.selector = selector;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addStateNode(StateNode stateNode) {
        stateNodes.put(stateNode.getId(), stateNode);
    }

    public StateNode getStateNode(String id) {
        return stateNodes.get(id);
    }
}
