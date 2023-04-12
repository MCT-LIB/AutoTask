package com.mct.autotask.domain.model;

import com.mct.autotask.data.datalocal.base.BaseEntity;
import com.mct.autotask.data.datalocal.entity.ActionEntity;

public class Action extends ActionTemplate {

    private long scenarioId;
    private int order;

    public long getScenarioId() {
        return scenarioId;
    }

    public Action setScenarioId(long scenarioId) {
        this.scenarioId = scenarioId;
        return this;
    }

    public int getOrder() {
        return order;
    }

    public Action setOrder(int order) {
        this.order = order;
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E extends BaseEntity> E toEntity() {
        return (E) new ActionEntity(
                getId(),
                getCreatedAt(),
                getUpdatedAt(),
                getName(),
                getDescription(),
                getIcon(),
                getIconTint(),
                getLoop(),
                getLoopDelay(),
                getStartDelay(),
                getActionDetail(),
                getScenarioId(),
                getOrder()
        );
    }
}
