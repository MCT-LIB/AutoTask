package com.mct.autotask.data.repository;

import com.mct.autotask.data.datalocal.dao.ActionDao;
import com.mct.autotask.domain.model.Action;
import com.mct.autotask.domain.repository.ActionRepository;

public class ActionRepositoryImpl extends BaseRepositoryImpl<Action> implements ActionRepository {

    private final ActionDao dao;

    public ActionRepositoryImpl(ActionDao dao) {
        this.dao = dao;
    }

    @Override
    protected ActionDao getDao() {
        return dao;
    }
}
