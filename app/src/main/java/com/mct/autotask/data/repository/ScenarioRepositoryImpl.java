package com.mct.autotask.data.repository;

import com.mct.autotask.data.datalocal.dao.ScenarioDao;
import com.mct.autotask.domain.model.Scenario;
import com.mct.autotask.domain.repository.ScenarioRepository;

public class ScenarioRepositoryImpl extends BaseRepositoryImpl<Scenario> implements ScenarioRepository {

    private final ScenarioDao dao;

    public ScenarioRepositoryImpl(ScenarioDao dao) {
        this.dao = dao;
    }

    @Override
    protected ScenarioDao getDao() {
        return dao;
    }
}
