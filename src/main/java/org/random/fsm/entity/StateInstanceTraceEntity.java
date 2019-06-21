package org.random.fsm.entity;

import java.util.Date;

/**
 *
 */
public class StateInstanceTraceEntity {

    private long id;
    private String machineInstanceId;
    private String prevNodeInstanceId;
    private String prevNodeId;
    private String nextNodeInstanceId;
    private String nextNodeId;
    private String parameter;
    private Date createTime;
    private Date updateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMachineInstanceId() {
        return machineInstanceId;
    }

    public void setMachineInstanceId(String machineInstanceId) {
        this.machineInstanceId = machineInstanceId;
    }

    public String getPrevNodeInstanceId() {
        return prevNodeInstanceId;
    }

    public void setPrevNodeInstanceId(String prevNodeInstanceId) {
        this.prevNodeInstanceId = prevNodeInstanceId;
    }

    public String getPrevNodeId() {
        return prevNodeId;
    }

    public void setPrevNodeId(String prevNodeId) {
        this.prevNodeId = prevNodeId;
    }

    public String getNextNodeInstanceId() {
        return nextNodeInstanceId;
    }

    public void setNextNodeInstanceId(String nextNodeInstanceId) {
        this.nextNodeInstanceId = nextNodeInstanceId;
    }

    public String getNextNodeId() {
        return nextNodeId;
    }

    public void setNextNodeId(String nextNodeId) {
        this.nextNodeId = nextNodeId;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
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
}
