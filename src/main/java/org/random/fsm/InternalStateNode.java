package org.random.fsm;

public class InternalStateNode extends StateNode {

    @Override
    public void addNextTransition(IStateTransition transition) {
        this.nextTransitions.add(transition);
    }

    @Override
    public void addPrevTransition(IStateTransition transition) {
        this.prevTransitions.add(transition);
    }
}
