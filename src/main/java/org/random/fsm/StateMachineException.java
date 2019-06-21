package org.random.fsm;

/**
 *
 */
public class StateMachineException extends RuntimeException {
    public StateMachineException() {
    }

    public StateMachineException(String message) {
        super(message);
    }

    public StateMachineException(String message, Throwable cause) {
        super(message, cause);
    }

    public StateMachineException(Throwable cause) {
        super(cause);
    }

    public StateMachineException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
