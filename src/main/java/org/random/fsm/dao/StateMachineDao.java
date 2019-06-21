package org.random.fsm.dao;

import org.random.fsm.entity.StateMachineEntity;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public interface StateMachineDao {

    /**
     * @return
     */
    long insert(StateMachineEntity entity);

    StateMachineEntity loadById(long id);

    StateMachineEntity selectMaximumById(String id);
}
