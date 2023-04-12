package com.mct.autotask.data.datalocal.dao;

import androidx.room.Dao;

import com.mct.autotask.data.datalocal.base.BaseDao;
import com.mct.autotask.data.datalocal.entity.ScenarioEntity;

@Dao
public abstract class ScenarioDao extends BaseDao<ScenarioEntity> {

    public ScenarioDao() {
        super(ScenarioEntity.class);
    }
}
