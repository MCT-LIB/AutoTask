package com.mct.autotask.data.datalocal.base;

import androidx.annotation.CallSuper;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.mct.autotask.domain.model.BaseModel;

import java.util.Date;

public abstract class BaseEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public Date createdAt;

    public Date updatedAt;

    public BaseEntity() {
    }

    @Ignore
    public BaseEntity(long id, Date createdAt, Date updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public abstract BaseModel newModel();

    @CallSuper
    @SuppressWarnings("unchecked")
    public <M extends BaseModel> M toModel() {
        return (M) newModel()
                .setId(id)
                .setCreatedAt(createdAt)
                .setUpdatedAt(updatedAt);
    }

}
