package org.random.fsm.entity;

import java.util.Date;

/**
 *
 */
public class StateMachineEntity {
    private long id;
    private String machineId;
    private int version;
    //	private long initialNodeId;
    private Date createTime;
    private Date updateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    //	public long getInitialNodeId() {
    //		return initialNodeId;
    //	}
    //
    //	public void setInitialNodeId(long initialNodeId) {
    //		this.initialNodeId = initialNodeId;
    //	}
    //
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
