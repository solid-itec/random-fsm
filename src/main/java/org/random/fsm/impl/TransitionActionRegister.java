package org.random.fsm.impl;

import org.random.fsm.IActionRegister;
import org.random.fsm.ITransitAction;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class TransitionActionRegister implements BeanPostProcessor, IActionRegister {
    private static final Map<String, ITransitAction> targets = new HashMap<>();
    private static final Map<String, ITransitAction> proxies = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 注册ITransitionAction原生对象
        if (bean instanceof ITransitAction) {
            String key = ((ITransitAction) bean).getId();
            targets.put(key, (ITransitAction) bean);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 注册ITransitionAction代理对象
        if (bean instanceof ITransitAction) {
            String key = ((ITransitAction) bean).getId();
            proxies.put(key, (ITransitAction) bean);
        }
        return bean;
    }

    @Override
    public ITransitAction get(String name) {
        return targets.get(name);
    }
}
