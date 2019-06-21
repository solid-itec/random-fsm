package org.random.fsm;

import org.random.fsm.event.StateEventType;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 *
 */
public class StateNodeInstance {

    private String id;
    private StateMachineInstance machineInstance;
    private StateNode stateNode;

    public StateNodeInstance(StateNode stateNode, StateMachineInstance machineInstance) {
        this.id = StateMachineHelper.encrypt16(StringUtils.replace(UUID.randomUUID().toString(), "-", ""));
        this.machineInstance = machineInstance;
        this.stateNode = stateNode;
    }

    public StateNodeInstance(StateNode stateNode, StateMachineInstance machineInstance, String id) {
        this.stateNode = stateNode;
        this.machineInstance = machineInstance;
        this.id = id;
    }

    /**
     * @param callback
     * @param context
     * @return
     * @throws StateMachineException
     */
    public StateNodeInstance forward(ITransitAction callback, ActionContext context) throws StateMachineException {
        ITransitionSelector selector = machineInstance.getMachine().getSelector();
        return forward(callback, context, selector);
    }

    /**
     * @param context
     * @param selector
     * @return
     * @throws StateMachineException
     */
    public StateNodeInstance forward(ActionContext context, ITransitionSelector selector) throws StateMachineException {
        return forward(null, context, selector);
    }

    /**
     * @param parent
     * @return
     * @throws StateMachineException
     */
    public StateNodeInstance forward(ActionContext parent) throws StateMachineException {
        ITransitionSelector selector = ITransitionSelector.NOTIFY;
        // 自由选择不指定action
        ActionContext notifier = new ActionContext();
        notifier.setProvider(parent.getProvider());
        notifier.setStateInstanceId(machineInstance.getInstanceId());
        notifier.setParameter(parent.getParameter());
        StateNodeInstance next = forward(notifier, selector);
        parent.getProvider().getInstanceDao()
                .updateByInstanceId(IContextProvider.createInstanceEntity(next.getMachineInstance(), next));
        return next;
    }

    /**
     * @param callback
     * @param context
     * @param selector
     * @return
     * @throws StateMachineException
     */
    private StateNodeInstance forward(ITransitAction callback, ActionContext context, ITransitionSelector selector)
            throws StateMachineException {
        context.setPrevStateNodeInstanceId(getId());
        // 发送disable事件通知
        IStateMachineManager manager = ((IStateMachineManager) context.getProvider());
        if (stateNode.isNotifyOnDisable()) {
            manager.notifyStateEvent(this, StateEventType.STATE_DISABLE, context);
        }
        IStateTransition selected = selector.doSelect(stateNode, callback, context);
        // 预生成后继状态节点实例ID
        String nextId = StateMachineHelper.encrypt16(StringUtils.replace(UUID.randomUUID().toString(), "-", ""));
        context.setNextStateNodeInstanceId(nextId);
        if (callback != null) {
            selected.transit(callback, context);
        } else {
            selected.transit(manager.getRegister().get(selected.getId()), context);
        }

        StateNode nextStateNode = selected.getNextStateNode();
        StateNodeInstance newInstance = new StateNodeInstance(nextStateNode, machineInstance, nextId);
        // 发送active事件通知
        if (newInstance.getStateNode().isNotifyOnActive()) {
            manager.notifyStateEvent(newInstance, StateEventType.STATE_ACTIVE, context);
        }
        if (nextStateNode.getClass().equals(FinalStateNode.class)) {
            machineInstance.setAvailable(false);
        }
        machineInstance.setStateNodeInstance(newInstance);
        return newInstance;
    }

    /**
     * @param callback
     * @param context
     * @return
     * @throws StateMachineException
     */
    public StateNodeInstance reverse(ITransitAction callback, ActionContext context) throws StateMachineException {
        // 模拟伪造的forward操作
        return forward(callback, context, ITransitionSelector.REVERSE);
    }

    public StateNode getStateNode() {
        return stateNode;
    }

    public void setStateNode(StateNode stateNode) {
        this.stateNode = stateNode;
    }

    public String getId() {
        return id;
    }

    public StateMachineInstance getMachineInstance() {
        return machineInstance;
    }
}
