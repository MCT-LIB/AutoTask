package com.mct.autotask.data.datalocal.base;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.annotation.NonNull;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseDao<M extends BaseEntity> {

    private final Class<M> clazz;

    protected BaseDao(Class<M> clazz) {
        this.clazz = clazz;
    }

    @RawQuery
    protected abstract M _fetchById(SupportSQLiteQuery query);

    @RawQuery
    protected abstract List<M> _fetchAll(SupportSQLiteQuery query);

    @Insert(onConflict = REPLACE)
    protected abstract Long _insert(M entity);

    @Insert(onConflict = REPLACE)
    protected abstract List<Long> _insertAll(List<M> entities);

    @Update(onConflict = REPLACE)
    protected abstract int _update(M entity);

    @Update(onConflict = REPLACE)
    protected abstract int _updateAll(List<M> entities);

    @Delete
    protected abstract int _delete(M entity);

    @Delete
    protected abstract int _deleteAll(List<M> entities);

    public M fetchById(long id) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE id = :id";
        return _fetchById(new SimpleSQLiteQuery(sql, new Object[]{id}));
    }

    public List<M> fetchAll() {
        String sql = "SELECT * FROM " + getTableName() + " ORDER BY id";
        return _fetchAll(new SimpleSQLiteQuery(sql));
    }

    public long insert(@NonNull M entity) {
        return _insert(createdAt(entity));
    }

    public List<Long> insertAll(@NonNull List<M> entities) {
        return _insertAll(entities.stream().map(this::createdAt).collect(Collectors.toList()));
    }

    public boolean update(@NonNull M entity) {
        return _update(updatedAt(entity)) > 0;
    }

    public boolean updateAll(@NonNull List<M> entities) {
        return _updateAll(entities.stream().map(this::updatedAt).collect(Collectors.toList())) > 0;
    }

    public boolean delete(M entity) {
        return _delete(entity) > 0;
    }

    public boolean deleteAll(List<M> entities) {
        return _deleteAll(entities) > 0;
    }

    public String getTableName() {
        Entity annotation = clazz.getAnnotation(Entity.class);
        if (annotation == null) {
            throw new RuntimeException("The entity was has 'Entity' annotation");
        }
        return annotation.tableName();
    }

    @NonNull
    private M createdAt(@NonNull M m) {
        m.createdAt = new Date();
        return m;
    }

    @NonNull
    private M updatedAt(@NonNull M m) {
        m.updatedAt = new Date();
        return m;
    }
}
