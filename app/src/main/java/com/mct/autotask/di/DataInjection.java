package com.mct.autotask.di;

import com.mct.autotask.App;
import com.mct.autotask.data.repository.RepositoryImpl;
import com.mct.autotask.domain.repository.Repository;

public class DataInjection {

    public static Repository provideRepository() {
        return RepositoryImpl.getInstance(App.getInstance());
    }

}
