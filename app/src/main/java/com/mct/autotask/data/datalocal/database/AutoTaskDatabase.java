package com.mct.autotask.data.datalocal.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.mct.autotask.data.datalocal.converter.DateConverter;
import com.mct.autotask.data.datalocal.dao.ActionDao;
import com.mct.autotask.data.datalocal.dao.ActionTemplateDao;
import com.mct.autotask.data.datalocal.dao.ScenarioDao;
import com.mct.autotask.data.datalocal.entity.ActionEntity;
import com.mct.autotask.data.datalocal.entity.ActionTemplateEntity;
import com.mct.autotask.data.datalocal.entity.ScenarioEntity;

@Database(version = 1,
        entities = {ScenarioEntity.class, ActionTemplateEntity.class, ActionEntity.class},
        exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AutoTaskDatabase extends RoomDatabase {

    private static final String DB_NAME = "AutoTaskDatabase.db";
    private static AutoTaskDatabase instance;

    public static synchronized AutoTaskDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AutoTaskDatabase.class, DB_NAME).build();
        }
        return instance;
    }

    public abstract ActionDao getActionDao();

    public abstract ActionTemplateDao getActionTemplateDao();

    public abstract ScenarioDao getScenarioDao();

}