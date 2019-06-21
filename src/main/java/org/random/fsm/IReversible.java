package org.random.fsm;

/**
 *
 */
public interface IReversible extends ITransitAction {

    /**
     * @param context
     * @return
     */
    Object doReverse(ActionContext context);
}
