package com.mct.autotask.data.datalocal.dao;

import androidx.room.Dao;

import com.mct.autotask.data.datalocal.base.BaseDao;
import com.mct.autotask.data.datalocal.entity.ActionEntity;

@Dao
public abstract class ActionDao extends BaseDao<ActionEntity> {

    public ActionDao() {
        super(ActionEntity.class);
    }
}
