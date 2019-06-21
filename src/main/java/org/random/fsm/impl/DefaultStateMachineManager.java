package org.random.fsm.impl;

import com.alibaba.fastjson.JSON;
import org.random.fsm.*;
import org.random.fsm.dao.StateEventListenerDao;
import org.random.fsm.dao.StateInstanceTraceDao;
import org.random.fsm.dao.StateMachineInstanceDao;
import org.random.fsm.entity.StateEventListenerEntity;
import org.random.fsm.entity.StateMachineInstanceEntity;
import org.random.fsm.event.StateEventType;
import org.random.fsm.event.StateMachineEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 *
 */
public class DefaultStateMachineManager implements IStateMachineManager, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(DefaultStateMachineManager.class);

    @Autowired
    private StateMachineFactory stateMachineFactory;

    @Autowired
    private StateMachineInstanceDao stateMachineInstanceDao;

    @Autowired
    private StateInstanceTraceDao stateInstanceTraceDao;

    @Autowired
    private StateEventListenerDao stateEventListenerDao;

    @Autowired
    private IActionRegister register;

    private static StateMachineInstance from(String objectString) {
        return JSON.parseObject(objectString, StateMachineInstance.class);
    }

    private static List<StateMachineInstance> fromStateEventListeners(List<StateEventListenerEntity> entities) {
        return null;
    }

    @Override
    public StateMachineInstance createMachineInstance(String stateMachineId) throws StateMachineException {
        return createMachineInstance(stateMachineId, null);
    }

    @Override
    public StateMachineInstance createMachineInstance(String stateMachineId, String parentInstanceId,
            String referenceId) throws StateMachineException {
        StateMachineInstance machineInstance = new StateMachineInstance();
        StateMachine machine;
        if ((machine = stateMachineFactory.createStateMachine(stateMachineId)) != null) {
            machineInstance.setMachine(machine);
            StateNodeInstance nodeInstance = new StateNodeInstance(machine.getInitialNode(), machineInstance);
            machineInstance.setStateNodeInstance(nodeInstance);
            // 设置父状态机实例
            if (!StringUtils.isEmpty(parentInstanceId)) {
                StateMachineInstance parentInstance = retrieveMachineInstance(parentInstanceId);
                machineInstance.setParentInstance(parentInstance);
            }
            machineInstance.setInstanceId(
                    StateMachineHelper.encrypt16(StringUtils.replace(UUID.randomUUID().toString(), "-", "")));
            machineInstance.setAvailable(true);
            stateMachineInstanceDao.insert(IContextProvider.createInstanceEntity(machineInstance, nodeInstance));
            return machineInstance;
        }
        throw new StateMachineException("状态机定义不存在[stateMachineId=" + stateMachineId + "]");
    }

    @Override
    public StateMachineInstance createMachineInstance(String stateMachineId, String parentInstanceId)
            throws StateMachineException {
        return createMachineInstance(stateMachineId, parentInstanceId, null);
    }

    @Override
    public StateMachineInstance retrieveMachineInstance(String stateMachineInstanceId) throws StateMachineException {
        StateMachineInstanceEntity entity = stateMachineInstanceDao.selectByInstanceId(stateMachineInstanceId);
        if (entity == null) {
            throw new StateMachineException("状态机实例不存在[id=" + stateMachineInstanceId + "]");
        }
        return buildMachineInstance(entity);
    }

    private StateMachineInstance buildMachineInstance(StateMachineInstanceEntity entity) {
        StateMachineInstance machineInstance = new StateMachineInstance();
        machineInstance.setInstanceId(entity.getInstanceId());
        StateMachine stateMachine = stateMachineFactory.createStateMachine(entity.getMachineId());
        if (stateMachine != null) {
            machineInstance.setMachine(stateMachine);
            machineInstance.setAvailable(entity.isAvailable());
            StateNodeInstance nodeInstance =
                    new StateNodeInstance(stateMachine.getStateNode(entity.getNodeId()), machineInstance,
                            entity.getNodeInstanceId());
            machineInstance.setStateNodeInstance(nodeInstance);
            return machineInstance;
        }
        throw new StateMachineException(
                "无法获取法状态机定义[stateMachineInstanceId=" + entity.getInstanceId() + "，stateMachineId=" +
                        entity.getMachineId() + " " + "]");
    }

    //	@Override
    //	public void registerEventListener(StateMachineInstance machineInstance, List<IStateMachineListener> listeners) {
    //		for (IStateMachineListener listener : listeners) {
    //			machineInstance.registerListener(listener);
    //		}
    ////		getListenerDao().batchInsert(null);
    //	}

    @Override
    public void notifyStateEvent(StateNodeInstance source, StateEventType type, ActionContext context) {
        //	StateMachineInstance machineInstance = source.getMachineInstance();
        String sourceId = source.getId();
        // 根本当前状态机实例id找到对应注册监听器的相关实例
        List<StateEventListenerEntity> entities = stateEventListenerDao.selectBySourceId(sourceId);
        List<StateMachineInstance> registered = fromStateEventListeners(entities);
        StateMachineEvent event = new StateMachineEvent();
        event.setSource(source);
        event.setType(type);
        Iterator<StateMachineInstance> iterator = registered.iterator();
        while (iterator.hasNext()) {
            iterator.next().notifyEvent(event, context);
        }
    }

    public void setStateMachineInstanceDao(StateMachineInstanceDao stateMachineInstanceDao) {
        this.stateMachineInstanceDao = stateMachineInstanceDao;
    }

    public void setStateInstanceTraceDao(StateInstanceTraceDao stateInstanceTraceDao) {
        this.stateInstanceTraceDao = stateInstanceTraceDao;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info(">>>>>>>>>>>>>>>>>>>>>>状态机引擎初始化完成>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Override
    public StateInstanceTraceDao getTracerDao() {
        return this.stateInstanceTraceDao;
    }

    @Override
    public StateMachineInstanceDao getInstanceDao() {
        return this.stateMachineInstanceDao;
    }

    @Override
    public StateEventListenerDao getListenerDao() {
        return this.stateEventListenerDao;
    }

    @Override
    public IActionRegister getRegister() {
        return this.register;
    }

    public StateMachineFactory getStateMachineFactory() {
        return stateMachineFactory;
    }

    public void setStateMachineFactory(StateMachineFactory stateMachineFactory) {
        this.stateMachineFactory = stateMachineFactory;
    }
}
