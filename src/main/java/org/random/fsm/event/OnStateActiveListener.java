package org.random.fsm.event;

import org.random.fsm.ActionContext;
import org.random.fsm.IContextProvider;
import org.random.fsm.StateNode;
import org.random.fsm.StateNodeInstance;
import org.random.fsm.impl.NotifyTransitionSelector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OnStateActiveListener implements IStateMachineListener {
    private Logger logger = LoggerFactory.getLogger(OnStateActiveListener.class);

    @Override
    public void onNodeActive(StateNode currentNode, ActionContext ctx) {

    }

    @Override
    public void onNodeDisable(StateNode currentNode, ActionContext ctx) {

    }

    @Override
    public void onEvent(StateMachineEvent event, ActionContext ctx) {
        if (event.getType() == StateEventType.STATE_ACTIVE) {
            StateNodeInstance nodeInstance = (StateNodeInstance) event.getSource();
            NotifyTransitionSelector selector = new NotifyTransitionSelector();
            // 自由选择不指定action
            ActionContext notifyContext = new ActionContext();
            notifyContext.setProvider(ctx.getProvider());
            notifyContext.setStateInstanceId(nodeInstance.getMachineInstance().getInstanceId());
            notifyContext.setParameter(ctx.getParameter());
            StateNodeInstance next = nodeInstance.forward(notifyContext, selector);
            ctx.getProvider().getInstanceDao()
                    .updateByInstanceId(IContextProvider.createInstanceEntity(next.getMachineInstance(), next));
        }
    }
}
