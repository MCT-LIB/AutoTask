package com.mct.autotask.domain.repository;

import androidx.annotation.NonNull;

import com.mct.autotask.domain.model.BaseModel;

import java.util.List;

public interface BaseRepository<M extends BaseModel> {

    M getById(long id);

    List<M> getAll();

    Long add(@NonNull M model);

    List<Long> add(@NonNull List<M> models);

    boolean edit(@NonNull M model);

    boolean edit(@NonNull List<M> models);

    boolean remove(@NonNull M model);

    boolean remove(@NonNull List<M> models);

}
