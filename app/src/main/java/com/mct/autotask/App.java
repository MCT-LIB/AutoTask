package com.mct.autotask;

import android.app.Application;
import android.content.Context;

import com.mct.autotask.utils.SettingsUtils;

public class App extends Application {

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        SettingsUtils.init(base);
        SettingsUtils.restoreNightMode();
        super.attachBaseContext(SettingsUtils.restoreLocale(base));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

}
