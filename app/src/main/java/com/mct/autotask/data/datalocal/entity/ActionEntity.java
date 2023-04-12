package com.mct.autotask.data.datalocal.entity;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

import com.mct.autotask.domain.model.Action;
import com.mct.autotask.domain.model.actiondetail.ActionDetail;

import java.util.Date;
import java.util.Objects;

@Entity(tableName = "Action",
        foreignKeys = @ForeignKey(entity = ScenarioEntity.class,
                parentColumns = "id",
                childColumns = "scenarioId",
                onUpdate = CASCADE,
                onDelete = CASCADE),
        indices = {@Index("scenarioId")})
public class ActionEntity extends ActionTemplateEntity {

    /**
     * scenarioId: Link to Scenario
     */
    public long scenarioId;

    /**
     * order: Order action in a Scenario
     */
    public int order;

    public ActionEntity() {
    }

    @Ignore
    public ActionEntity(long id, Date createdAt, Date updatedAt,
                        String name, String description, String icon, String iconTint,
                        int loop, int loopDelay, int startDelay, ActionDetail actionDetail,
                        long scenarioId, int order) {
        super(id, createdAt, updatedAt, name, description, icon, iconTint, loop, loopDelay, startDelay, actionDetail);
        this.scenarioId = scenarioId;
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActionEntity)) return false;
        if (!super.equals(o)) return false;
        ActionEntity that = (ActionEntity) o;
        return scenarioId == that.scenarioId && order == that.order;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), scenarioId, order);
    }

    @Override
    public Action newModel() {
        return new Action();
    }

    @Override
    public Action toModel() {
        return ((Action) super.toModel())
                .setScenarioId(scenarioId)
                .setOrder(order);
    }
}
