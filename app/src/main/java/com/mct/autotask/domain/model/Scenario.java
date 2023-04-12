package com.mct.autotask.domain.model;

import com.mct.autotask.data.datalocal.base.BaseEntity;
import com.mct.autotask.data.datalocal.entity.ScenarioEntity;
import com.mct.autotask.domain.model.scheduler.SchedulerType;

import java.util.Calendar;
import java.util.Set;

public class Scenario extends BaseModel {

    private String name;
    private String description;
    private String icon;
    private String iconTint;
    private boolean favorite;
    private boolean enable;
    private SchedulerType schedulerType;
    private Set<Integer> schedulerDate;
    private Calendar schedulerTime;

    public String getName() {
        return name;
    }

    public Scenario setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Scenario setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public Scenario setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public String getIconTint() {
        return iconTint;
    }

    public Scenario setIconTint(String iconTint) {
        this.iconTint = iconTint;
        return this;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public Scenario setFavorite(boolean favorite) {
        this.favorite = favorite;
        return this;
    }

    public boolean isEnable() {
        return enable;
    }

    public Scenario setEnable(boolean enable) {
        this.enable = enable;
        return this;
    }

    public SchedulerType getSchedulerType() {
        return schedulerType;
    }

    public Scenario setSchedulerType(SchedulerType schedulerType) {
        this.schedulerType = schedulerType;
        return this;
    }

    public Set<Integer> getSchedulerDate() {
        return schedulerDate;
    }

    public Scenario setSchedulerDate(Set<Integer> schedulerDate) {
        this.schedulerDate = schedulerDate;
        return this;
    }

    public Calendar getSchedulerTime() {
        return schedulerTime;
    }

    public Scenario setSchedulerTime(Calendar schedulerTime) {
        this.schedulerTime = schedulerTime;
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E extends BaseEntity> E toEntity() {
        return (E) new ScenarioEntity(
                getId(),
                getCreatedAt(),
                getUpdatedAt(),
                getName(),
                getDescription(),
                getIcon(),
                getIconTint(),
                isFavorite(),
                isEnable(),
                getSchedulerType(),
                getSchedulerDate(),
                getSchedulerTime()
        );
    }
}
