package org.random.fsm;

/**
 *
 */
public interface IActionRegister {

    /**
     * @param name
     * @return
     */
    ITransitAction get(String name);
}
