package com.mct.autotask.presentation.view.base;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.mct.autotask.App;
import com.mct.autotask.utils.SettingsUtils;
import com.mct.components.baseui.BaseActivity;

public abstract class CustomBaseActivity extends BaseActivity {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(App.getInstance().getBaseContext());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SettingsUtils.applyPrimaryColor(this);
    }
}
