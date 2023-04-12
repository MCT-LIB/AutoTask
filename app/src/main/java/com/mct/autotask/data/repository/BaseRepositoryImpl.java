package com.mct.autotask.data.repository;

import androidx.annotation.NonNull;

import com.mct.autotask.data.datalocal.base.BaseDao;
import com.mct.autotask.data.datalocal.base.BaseEntity;
import com.mct.autotask.domain.model.BaseModel;
import com.mct.autotask.domain.repository.BaseRepository;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class BaseRepositoryImpl<M extends BaseModel> implements BaseRepository<M> {

    protected abstract BaseDao<? extends BaseEntity> getDao();

    @Override
    public M getById(long id) {
        return toModel(getDao().fetchById(id));
    }

    @Override
    public List<M> getAll() {
        return toModels(getDao().fetchAll());
    }

    public Long add(@NonNull M model) {
        return getDao().insert(toEntity(model));
    }

    public List<Long> add(@NonNull List<M> models) {
        return getDao().insertAll(toEntities(models));
    }

    public boolean edit(@NonNull M model) {
        return getDao().update(toEntity(model));
    }

    public boolean edit(@NonNull List<M> models) {
        return getDao().updateAll(toEntities(models));
    }

    public boolean remove(@NonNull M model) {
        return getDao().delete(toEntity(model));
    }

    public boolean remove(@NonNull List<M> models) {
        return getDao().deleteAll(toEntities(models));
    }

    protected <E extends BaseEntity> E toEntity(@NonNull M model) {
        return model.toEntity();
    }

    protected <E extends BaseEntity> List<E> toEntities(@NonNull List<M> models) {
        return models.stream().map((Function<M, E>) M::toEntity).collect(Collectors.toList());
    }

    protected <E extends BaseEntity> M toModel(@NonNull E entity) {
        return entity.toModel();
    }

    protected <E extends BaseEntity> List<M> toModels(@NonNull List<E> entities) {
        return entities.stream().map((Function<E, M>) E::toModel).collect(Collectors.toList());
    }

}
