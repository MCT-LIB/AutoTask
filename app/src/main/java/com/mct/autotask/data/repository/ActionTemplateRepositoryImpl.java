package com.mct.autotask.data.repository;

import com.mct.autotask.data.datalocal.dao.ActionTemplateDao;
import com.mct.autotask.domain.model.ActionTemplate;
import com.mct.autotask.domain.repository.ActionTemplateRepository;

public class ActionTemplateRepositoryImpl extends BaseRepositoryImpl<ActionTemplate> implements ActionTemplateRepository {

    private final ActionTemplateDao dao;

    public ActionTemplateRepositoryImpl(ActionTemplateDao dao) {
        this.dao = dao;
    }

    @Override
    protected ActionTemplateDao getDao() {
        return dao;
    }
}
