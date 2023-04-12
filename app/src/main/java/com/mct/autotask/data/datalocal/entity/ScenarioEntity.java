package com.mct.autotask.data.datalocal.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.TypeConverters;

import com.mct.autotask.data.datalocal.base.BaseEntity;
import com.mct.autotask.data.datalocal.converter.SchedulerConverter;
import com.mct.autotask.domain.model.Scenario;
import com.mct.autotask.domain.model.scheduler.SchedulerDate;
import com.mct.autotask.domain.model.scheduler.SchedulerType;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@Entity(tableName = "Scenario")
public class ScenarioEntity extends BaseEntity {

    /**
     * name: Name of Scenario
     */
    public String name;

    /**
     * description: Description of Scenario
     */
    public String description;

    /**
     * icon: Icon of Scenario
     */
    public String icon;

    /**
     * iconTint: IconTint of Scenario
     */
    public String iconTint;

    /**
     * favorite: true if user favorite
     */
    public boolean favorite;

    /**
     * enable: true if user enable this scenario
     */
    public boolean enable;

    /**
     * The type of scheduler
     */
    @TypeConverters(SchedulerConverter.Type.class)
    public SchedulerType schedulerType;

    /**
     * schedulerDate: Save the set of DAY_OF_WEEK<br/>
     * see: {@link SchedulerDate}
     */
    @TypeConverters(SchedulerConverter.Date.class)
    public Set<Integer> schedulerDate;

    /**
     * schedulerTime: Only save the hour & minute to timer this scenario<br/>
     * see: {@link Calendar#HOUR_OF_DAY}, {@link Calendar#MINUTE}
     */
    @TypeConverters(SchedulerConverter.Time.class)
    public Calendar schedulerTime;

    public ScenarioEntity() {
    }

    @Ignore
    public ScenarioEntity(long id, Date createdAt, Date updatedAt,
                          String name, String description, String icon, String iconTint,
                          boolean favorite, boolean enable,
                          SchedulerType schedulerType, Set<Integer> schedulerDate, Calendar schedulerTime) {
        super(id, createdAt, updatedAt);
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.iconTint = iconTint;
        this.favorite = favorite;
        this.enable = enable;
        this.schedulerType = schedulerType;
        this.schedulerDate = schedulerDate;
        this.schedulerTime = schedulerTime;
    }

    @Override
    public Scenario newModel() {
        return new Scenario();
    }

    @Override
    public Scenario toModel() {
        return ((Scenario) super.toModel())
                .setName(name)
                .setDescription(description)
                .setIcon(icon)
                .setIconTint(iconTint)
                .setFavorite(favorite)
                .setEnable(enable)
                .setSchedulerType(schedulerType)
                .setSchedulerDate(schedulerDate)
                .setSchedulerTime(schedulerTime);
    }
}
