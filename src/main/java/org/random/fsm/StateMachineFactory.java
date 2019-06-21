package org.random.fsm;

import org.random.fsm.entity.StateNodeEntity;
import org.random.fsm.impl.DefaultStateTransition;

/**
 *
 */
public abstract class StateMachineFactory {

    /**
     * @return
     */
    public abstract StateMachine createStateMachine(String id) throws StateMachineException;

    protected StateNode createInitialStateNode(String id, int value, StateMachine machine) {
        InitialStateNode initial = new InitialStateNode();
        initial.setId(id);
        initial.setValue(value);
        machine.setInitialNode(initial);
        machine.addStateNode(initial);
        return initial;
    }

    protected StateNode createInternalStateNode(String id, int value, StateMachine machine,
            IStateTransition prevTransition) {
        InternalStateNode node = new InternalStateNode();
        node.setId(id);
        node.setValue(value);
        node.addPrevTransition(prevTransition);
        ((DefaultStateTransition) prevTransition).setNextStateNode(node);
        machine.addStateNode(node);
        return node;
    }

    protected IStateTransition createStateTransition(String id, StateNode prevNode) {
        DefaultStateTransition transition = new DefaultStateTransition();
        transition.setId(id);
        transition.setPrevStateNode(prevNode);
        prevNode.addNextTransition(transition);
        return transition;
    }


    protected StateNode createFinalStateNode(String id, int value, StateMachine machine,
            IStateTransition prevTransition) {
        FinalStateNode _final = new FinalStateNode();
        _final.setId(id);
        _final.setValue(value);
        ((DefaultStateTransition) prevTransition).setNextStateNode(_final);
        _final.addPrevTransition(prevTransition);
        machine.addStateNode(_final);
        return _final;
    }

    protected StateNode createStateNode(StateNodeEntity entity, StateMachine stateMachine) {
        int nodeType = entity.getNodeType();
        StateNode node;
        switch (nodeType) {
            case 0:
                node = new InitialStateNode();
                stateMachine.setInitialNode(node);
                break;
            case 1:
                node = new InternalStateNode();
                break;
            case 2:
                node = new FinalStateNode();
                break;
            default:
                throw new UnsupportedOperationException("非法的状态机节点类型[id=" + entity.getId() + "]");
        }
        node.setId(entity.getNodeId());
        node.setValue(entity.getValue());
        return node;
    }
}
