package com.mct.autotask.data.datalocal.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.TypeConverters;

import com.mct.autotask.data.datalocal.base.BaseEntity;
import com.mct.autotask.data.datalocal.converter.ActionBaseConverter;
import com.mct.autotask.domain.model.ActionTemplate;
import com.mct.autotask.domain.model.actiondetail.ActionDetail;

import java.util.Date;
import java.util.Objects;

@Entity(tableName = "ActionTemplate")
public class ActionTemplateEntity extends BaseEntity {

    /**
     * name: Name of action
     */
    public String name;

    /**
     * description: Description of action
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
     * loop: Number of times the action was performed
     */
    public int loop;

    /**
     * loopDelay: The delay before the action runs a loop
     */
    public int loopDelay;

    /**
     * startDelay: The delay before run the action
     */
    public int startDelay;

    /**
     * actionDetail: Enum stores the implementation class of the action
     */
    @TypeConverters(ActionBaseConverter.class)
    public ActionDetail actionDetail;

    public ActionTemplateEntity() {
    }

    @Ignore
    public ActionTemplateEntity(long id, Date createdAt, Date updatedAt,
                                String name, String description, String icon, String iconTint,
                                int loop, int loopDelay, int startDelay, ActionDetail actionDetail) {
        super(id, createdAt, updatedAt);
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.iconTint = iconTint;
        this.loop = loop;
        this.loopDelay = loopDelay;
        this.startDelay = startDelay;
        this.actionDetail = actionDetail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActionTemplateEntity)) return false;
        ActionTemplateEntity that = (ActionTemplateEntity) o;
        return loop == that.loop && loopDelay == that.loopDelay && startDelay == that.startDelay &&
                name.equals(that.name) && Objects.equals(description, that.description) &&
                icon.equals(that.icon) && iconTint.equals(that.iconTint) &&
                Objects.equals(actionDetail, that.actionDetail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, icon, iconTint, loop, loopDelay, startDelay, actionDetail);
    }

    @Override
    public ActionTemplate newModel() {
        return new ActionTemplate();
    }

    @Override
    public ActionTemplate toModel() {
        return ((ActionTemplate) super.toModel())
                .setName(name)
                .setDescription(description)
                .setIcon(icon)
                .setIconTint(iconTint)
                .setLoop(loop)
                .setLoopDelay(loopDelay)
                .setStartDelay(startDelay)
                .setActionDetail(actionDetail);
    }
}
