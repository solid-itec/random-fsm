package org.random.fsm.dao;

import org.random.fsm.entity.StateInstanceTraceEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 */
@Component
public interface StateInstanceTraceDao {

    /**
     * @param id
     * @return
     */
    List<StateInstanceTraceEntity> selectByInstanceId(String id);

    /**
     * @param entity
     * @return
     */
    long insert(StateInstanceTraceEntity entity);

}
