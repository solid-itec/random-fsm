package org.random.fsm.entity;

import java.util.Date;

/**
 *
 */
public class StateTransitionEntity {
    private long id;
    private long machineId;
    private long prevStateNodeId;
    private long nextStateNodeId;
    private String actionId;
    private String actionRule;
    private int actionPriori;
    private Date createTime;
    private Date updateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPrevStateNodeId() {
        return prevStateNodeId;
    }

    public void setPrevStateNodeId(long prevStateNodeId) {
        this.prevStateNodeId = prevStateNodeId;
    }

    public long getNextStateNodeId() {
        return nextStateNodeId;
    }

    public void setNextStateNodeId(long nextStateNodeId) {
        this.nextStateNodeId = nextStateNodeId;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public long getMachineId() {
        return machineId;
    }

    public void setMachineId(long machineId) {
        this.machineId = machineId;
    }

    public String getActionRule() {
        return actionRule;
    }

    public void setActionRule(String actionRule) {
        this.actionRule = actionRule;
    }

    public int getActionPriori() {
        return actionPriori;
    }

    public void setActionPriori(int actionPriori) {
        this.actionPriori = actionPriori;
    }
}
