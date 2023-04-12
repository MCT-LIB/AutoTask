package com.mct.autotask.utils;

import android.provider.Settings;

import com.mct.autotask.App;
import com.mct.autotask.presentation.view.services.AutoTaskService;

public class PermissionUtils {

    public static boolean isOverlayPermissionValid() {
        return Settings.canDrawOverlays(App.getInstance());
    }

    public static boolean isAccessibilityPermissionValid() {
        return AutoTaskService.isServiceActive();
    }

}
