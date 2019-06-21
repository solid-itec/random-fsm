package org.random.fsm.dao;

import org.random.fsm.entity.StateNodeEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 */
@Component
public interface StateNodeDao {

    /**
     * @param entities
     */
    int batchInsert(List<StateNodeEntity> entities);

    /**
     * @param id
     * @return
     */
    List<StateNodeEntity> selectByMachineId(long id);
}
