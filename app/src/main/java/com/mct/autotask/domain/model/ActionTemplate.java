package com.mct.autotask.domain.model;

import com.mct.autotask.data.datalocal.base.BaseEntity;
import com.mct.autotask.data.datalocal.entity.ActionTemplateEntity;
import com.mct.autotask.domain.model.actiondetail.ActionDetail;

public class ActionTemplate extends BaseModel {

    private String name;
    private String description;
    private String icon;
    private String iconTint;
    private int loop;
    private int loopDelay;
    private int startDelay;
    private ActionDetail actionDetail;

    public String getName() {
        return name;
    }

    public ActionTemplate setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ActionTemplate setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public ActionTemplate setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public String getIconTint() {
        return iconTint;
    }

    public ActionTemplate setIconTint(String iconTint) {
        this.iconTint = iconTint;
        return this;
    }

    public int getLoop() {
        return loop;
    }

    public ActionTemplate setLoop(int loop) {
        this.loop = loop;
        return this;
    }

    public int getLoopDelay() {
        return loopDelay;
    }

    public ActionTemplate setLoopDelay(int loopDelay) {
        this.loopDelay = loopDelay;
        return this;
    }

    public int getStartDelay() {
        return startDelay;
    }

    public ActionTemplate setStartDelay(int startDelay) {
        this.startDelay = startDelay;
        return this;
    }

    public ActionDetail getActionDetail() {
        return actionDetail;
    }

    public ActionTemplate setActionDetail(ActionDetail actionDetail) {
        this.actionDetail = actionDetail;
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E extends BaseEntity> E toEntity() {
        return (E) new ActionTemplateEntity(
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
                getActionDetail()
        );
    }
}
