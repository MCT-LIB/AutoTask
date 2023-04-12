package com.mct.autotask.data.datalocal.dao;

import androidx.room.Dao;

import com.mct.autotask.data.datalocal.base.BaseDao;
import com.mct.autotask.data.datalocal.entity.ActionTemplateEntity;

@Dao
public abstract class ActionTemplateDao extends BaseDao<ActionTemplateEntity> {

    public ActionTemplateDao() {
        super(ActionTemplateEntity.class);
    }
}
