package com.mct.autotask.data.datalocal.converter;

import androidx.annotation.NonNull;
import androidx.room.TypeConverter;

import com.mct.autotask.domain.model.actiondetail.ActionDetail;

public class ActionBaseConverter {

    @TypeConverter
    public ActionDetail fromJson(String json) {
        return ActionDetail.fromJson(json);
    }

    @TypeConverter
    public String toJson(@NonNull ActionDetail actionDetail) {
        return ActionDetail.toJson(actionDetail);
    }

}
