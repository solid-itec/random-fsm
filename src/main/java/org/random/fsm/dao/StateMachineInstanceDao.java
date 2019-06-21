package org.random.fsm.dao;


import org.random.fsm.entity.StateMachineInstanceEntity;
import org.springframework.stereotype.Component;

@Component
public interface StateMachineInstanceDao {

    /**
     * @param instanceId
     * @return
     */
    StateMachineInstanceEntity selectByInstanceId(String instanceId);


    /**
     * @param entity
     * @return
     */
    long insert(StateMachineInstanceEntity entity);

    /**
     * @return
     */
    long updateByInstanceId(StateMachineInstanceEntity entity);
}
