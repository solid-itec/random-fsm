package org.random.fsm.impl;

import org.random.fsm.*;
import org.random.fsm.dao.StateMachineDao;
import org.random.fsm.dao.StateNodeDao;
import org.random.fsm.dao.StateTransitionDao;
import org.random.fsm.entity.StateMachineEntity;
import org.random.fsm.entity.StateNodeEntity;
import org.random.fsm.entity.StateTransitionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */
public class DefaultStateMachineFactory extends StateMachineFactory {

    private static final ConcurrentHashMap<String, StateMachine> cache = new ConcurrentHashMap<>();

    @Autowired
    private StateMachineDao stateMachineDao;

    @Autowired
    private StateNodeDao stateNodeDao;

    @Autowired
    private StateTransitionDao stateTransitionDao;

    @Override
    public StateMachine createStateMachine(String id) throws StateMachineException {
        if (StringUtils.isEmpty(id)) {
            throw new StateMachineException("状态机定义id=NULL无法创建对象");
        }
        return cache.computeIfAbsent(id, this::generate);
    }

    private StateMachine generate(String id) {
        StateMachineEntity stateMachineEntity = stateMachineDao.selectMaximumById(id);
        if (stateMachineEntity == null) {
            return null;
        }

        // 构造状态机
        StateMachine stateMachine = new StateMachine();
        stateMachine.setId(stateMachineEntity.getMachineId());
        stateMachine.setSelector(ITransitionSelector.DEFAULT);
        stateMachine.setVersion(stateMachineEntity.getVersion());

        // 构造状态机节点
        List<StateNodeEntity> stateNodeEntities = stateNodeDao.selectByMachineId(stateMachineEntity.getId());
        StateNode node;
        Map<Long, StateNode> kvPair = new HashMap<>();
        for (StateNodeEntity stateNodeEntity : stateNodeEntities) {
            node = createStateNode(stateNodeEntity, stateMachine);
            stateMachine.addStateNode(node);
            kvPair.put(stateNodeEntity.getId(), node);
        }

        //构造状态机流向
        List<StateTransitionEntity> stateTransitionEntities =
                stateTransitionDao.selectByMachineId(stateMachineEntity.getId());
        IStateTransition transition;
        StateNode prev;
        StateNode next;
        for (StateTransitionEntity stateTransitionEntity : stateTransitionEntities) {
            transition = new DefaultStateTransition();
            transition.setId(stateTransitionEntity.getActionId());
            ((DefaultStateTransition) transition).setRule(stateTransitionEntity.getActionRule());
            if (stateTransitionEntity.getActionPriori() == 1) {
                ((DefaultStateTransition) transition).setPriori(true);
            }
            prev = kvPair.get(stateTransitionEntity.getPrevStateNodeId());
            next = kvPair.get(stateTransitionEntity.getNextStateNodeId());
            ((DefaultStateTransition) transition).setPrevStateNode(prev);
            ((DefaultStateTransition) transition).setNextStateNode(next);
            prev.addNextTransition(transition);
            next.addPrevTransition(transition);
        }
        return stateMachine;
    }
}
