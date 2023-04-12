package com.mct.autotask.data.repository;

import android.content.Context;

import com.mct.autotask.data.datalocal.database.AutoTaskDatabase;
import com.mct.autotask.domain.repository.ActionRepository;
import com.mct.autotask.domain.repository.ActionTemplateRepository;
import com.mct.autotask.domain.repository.Repository;
import com.mct.autotask.domain.repository.ScenarioRepository;

public class RepositoryImpl implements Repository {

    private static RepositoryImpl instance;
    private final AutoTaskDatabase db;
    private volatile ActionRepository actionRepository;
    private volatile ActionTemplateRepository actionTemplateRepository;
    private volatile ScenarioRepository scenarioRepository;

    private RepositoryImpl(Context context) {
        this.db = AutoTaskDatabase.getInstance(context);
    }

    public static RepositoryImpl getInstance(Context context) {
        if (instance != null) {
            return instance;
        } else {
            synchronized (RepositoryImpl.class) {
                if (instance == null) {
                    instance = new RepositoryImpl(context);
                }
                return instance;
            }
        }
    }

    @Override
    public ActionRepository actionRepository() {
        if (actionRepository != null) {
            return actionRepository;
        } else {
            synchronized (this) {
                if (actionRepository == null) {
                    actionRepository = new ActionRepositoryImpl(db.getActionDao());
                }
                return actionRepository;
            }
        }
    }

    @Override
    public ActionTemplateRepository actionTemplateRepository() {
        if (actionTemplateRepository != null) {
            return actionTemplateRepository;
        } else {
            synchronized (this) {
                if (actionTemplateRepository == null) {
                    actionTemplateRepository = new ActionTemplateRepositoryImpl(db.getActionTemplateDao());
                }
                return actionTemplateRepository;
            }
        }
    }

    @Override
    public ScenarioRepository scenarioRepository() {
        if (scenarioRepository != null) {
            return scenarioRepository;
        } else {
            synchronized (this) {
                if (scenarioRepository == null) {
                    scenarioRepository = new ScenarioRepositoryImpl(db.getScenarioDao());
                }
                return scenarioRepository;
            }
        }
    }

}
