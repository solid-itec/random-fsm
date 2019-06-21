package org.random.fsm.impl;

import com.alibaba.fastjson.JSON;
import org.mvel2.MVEL;
import org.random.fsm.*;
import org.random.fsm.entity.StateInstanceTraceEntity;
import org.springframework.util.StringUtils;

/**
 *
 */
public class DefaultStateTransition implements IStateTransition {

    private String id;
    private StateNode nextStateNode;
    private StateNode prevStateNode;
    // 路由规则表达式
    private String rule;
    private boolean priori;

    @Override
    public StateNode getNextStateNode() {
        return this.nextStateNode;
    }

    public void setNextStateNode(StateNode nextStateNode) {
        this.nextStateNode = nextStateNode;
    }

    @Override
    public StateNode getPrevStateNode() {
        return this.prevStateNode;
    }

    public void setPrevStateNode(StateNode prevStateNode) {
        this.prevStateNode = prevStateNode;
    }

    @Override
    public boolean match(ITransitAction callback, ActionContext context) throws StateMachineException {
        // 根据规则表达式计算对象是否匹配流转条件
        // 默认的流转条件为当进入当前流向皆匹配
        if (!context.isForward()) {
            return true;
        }
        if (StringUtils.isEmpty(rule)) {
            return true;
        }
        Object eval;
        if (!priori) {
            eval = MVEL.eval(rule, context.getParameter());
        } else {
            // 先行计算
            Object result = callback.doAction(context);
            eval = MVEL.eval(rule, result);
            //	context.setResult(result);
        }
        if (eval != null && eval instanceof Boolean && (boolean) eval) {
            return true;
        }
        return false;
    }

    @Override
    public void transit(ITransitAction callback, ActionContext context) throws StateMachineException {
        String from = prevStateNode.getId();
        String to = nextStateNode.getId();
        int prevValue = prevStateNode.getValue();
        int nextValue = nextStateNode.getValue();
        context.setPrevStateNodeId(from);
        context.setNextStateNodeId(to);
        context.setPrevStateNodeValue(prevValue);
        context.setNextStateNodeValue(nextValue);
        Object result;
        // 触发节点状态事件
        String json = null;
        if (!context.isForward()) {
            result = ((IReversible) callback).doReverse(context);
        } else {
            if (StringUtils.isEmpty(rule) || !priori) {
                // 规则表达式为空或后执行的情况需要执行
                result = callback.doAction(context);
                json = JSON.toJSONString(context.getParameter());
            } else {
                result = context;
                json = JSON.toJSONString(result);
            }
        }
        context.setResult(result);
        StateInstanceTraceEntity entity = IContextProvider
                .createTraceEntity(context.getStateInstanceId(), context.getPrevStateNodeInstanceId(), from,
                        context.getNextStateNodeInstanceId(), to, json != null ? json : "");
        // 持久化状态流转日志
        context.getProvider().getTracerDao().insert(entity);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }


    public boolean isPriori() {
        return priori;
    }

    public void setPriori(boolean priori) {
        this.priori = priori;
    }
}
