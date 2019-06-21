package org.random.fsm.dao;

import org.random.fsm.entity.StateTransitionEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 */
@Component
public interface StateTransitionDao {

    /**
     * @param entities
     */
    void batchInsert(List<StateTransitionEntity> entities);

    /**
     * @param id
     * @return
     */
    List<StateTransitionEntity> selectByMachineId(long id);
}
