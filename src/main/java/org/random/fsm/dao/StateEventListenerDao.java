package org.random.fsm.dao;

import org.random.fsm.entity.StateEventListenerEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StateEventListenerDao {
    /**
     * @param entity
     * @return
     */
    long insert(StateEventListenerEntity entity);

    /**
     * @param id
     * @return
     */
    List<StateEventListenerEntity> selectBySourceId(String id);

    /**
     * @return
     */
    int batchInsert(List<StateEventListenerEntity> entities);
}
