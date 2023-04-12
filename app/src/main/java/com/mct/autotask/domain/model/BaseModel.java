package com.mct.autotask.domain.model;

import com.mct.autotask.data.datalocal.base.BaseEntity;

import java.util.Date;

public abstract class BaseModel {

    private long id;
    private Date createdAt;
    private Date updatedAt;

    public long getId() {
        return id;
    }

    public BaseModel setId(long id) {
        this.id = id;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public BaseModel setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public BaseModel setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public abstract <E extends BaseEntity> E toEntity();
}
