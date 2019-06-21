package org.random.fsm;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.security.MessageDigest;
import java.util.Map;

/**
 *
 */
public class StateMachineHelper implements ApplicationContextAware {

    private static IStateMachineManager stateMachineManager;

    /**
     * @param encrypt
     * @return
     */
    public static String encrypt32(String encrypt) {
        MessageDigest md5;
        String encrypted;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(encrypt.getBytes());
            StringBuffer hex = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                int val = ((int) bytes[i]) & 0xff;
                if (val < 16)
                    hex.append("0");
                hex.append(Integer.toHexString(val));
            }
            encrypted = hex.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return encrypted;
    }

    /**
     * @param encrypt
     * @return
     */
    public static String encrypt16(String encrypt) {
        return encrypt32(encrypt).substring(8, 24);
    }

    /**
     * @return
     * @throws StateMachineException
     */
    public static StateMachineInstance createInstance(String machineId) throws StateMachineException {
        return stateMachineManager.createMachineInstance(machineId);
    }

    /**
     * @return
     * @throws StateMachineException
     */
    public static StateMachineInstance createInstance(String machineId, String referenceId)
            throws StateMachineException {
        return stateMachineManager.createMachineInstance(machineId, null, referenceId);
    }

    /**
     * @param instanceId
     * @return
     * @throws StateMachineException
     */
    public static StateMachineInstance retrieveInstance(String instanceId) throws StateMachineException {
        return stateMachineManager.retrieveMachineInstance(instanceId);
    }

    /**
     * @param stateInstanceId
     * @param action
     * @param parameter
     * @return
     * @throws StateMachineException
     */
    public static Object doAction(String stateInstanceId, ITransitAction action, Map parameter)
            throws StateMachineException {
        return doAction(stateInstanceId, null, action, parameter);
    }

    /**
     * @param stateInstanceId
     * @param referenceId
     * @param action
     * @param parameter
     * @return
     * @throws StateMachineException
     */
    public static Object doAction(String stateInstanceId, String referenceId, ITransitAction action, Map parameter)
            throws StateMachineException {
        ActionContext ctx = new ActionContext();
        ctx.setStateInstanceId(stateInstanceId);
        ctx.setReferenceId(referenceId);
        ctx.setParameter(parameter);
        return action.doAction(ctx);
    }

    //	public void registerEventListener(StateMachineInstance machineInstance, List<IStateMachineListener> listeners) {
    //		stateMachineManager.registerEventListener(machineInstance, listeners);
    //	}
    //
    //	/**
    //	 * @param machineInstance
    //	 * @param listener
    //	 */
    //	public void registerEventListener(StateMachineInstance machineInstance, IStateMachineListener listener) {
    //		List<IStateMachineListener> listeners = new LinkedList<>();
    //		listeners.add(listener);
    //		stateMachineManager.registerEventListener(machineInstance, listeners);
    //	}
    //

    public static Object doAction(String instanceId, Map parameter) throws StateMachineException {
        StateMachineInstance machineInstance = retrieveInstance(instanceId);
        StateNodeInstance nodeInstance = machineInstance.getStateNodeInstance();
        ActionContext ctx = new ActionContext();
        ctx.setStateInstanceId(machineInstance.getInstanceId());
        ctx.setParameter(parameter);
        nodeInstance.forward(ctx);
        return ctx.getResult();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 为方便调用方使用强制注入应用上下文
        // 虽然这样的写法不太优雅
        stateMachineManager = applicationContext.getBean(IStateMachineManager.class);
    }
}
