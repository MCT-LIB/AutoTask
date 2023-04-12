package com.mct.autotask.presentation.view.fragments.settings.display;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

import com.mct.autotask.R;
import com.mct.autotask.presentation.view.base.CustomBaseFragment;
import com.mct.autotask.presentation.view.common.SettingItem;
import com.mct.autotask.utils.SettingsUtils;

public class SettingsThemeFragment extends CustomBaseFragment implements SettingItem.OnClickSettingListener {

    private SettingItem siAuto, siDark, siLight;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_theme, container, false);
        initUi(view);
        return view;
    }

    private void initUi(@NonNull View view) {
        initToolbar(view);

        siAuto = view.findViewById(R.id.si_auto);
        siDark = view.findViewById(R.id.si_dark);
        siLight = view.findViewById(R.id.si_light);

        siAuto.setOnClickSettingListener(this);
        siDark.setOnClickSettingListener(this);
        siLight.setOnClickSettingListener(this);

        switch (SettingsUtils.getNightMode()) {
            default:
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
                siAuto.setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                siDark.setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_NO:
                siLight.setChecked(true);
                break;
        }
    }

    @Override
    @SuppressLint("NonConstantResourceId")
    public boolean onClicked(SettingItem settingItem, boolean isChecked) {
        if (!isChecked) {
            int nightMode;
            switch (settingItem.getId()) {
                default:
                case R.id.si_auto:
                    nightMode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
                    break;
                case R.id.si_dark:
                    nightMode = AppCompatDelegate.MODE_NIGHT_YES;
                    break;
                case R.id.si_light:
                    nightMode = AppCompatDelegate.MODE_NIGHT_NO;
                    break;
            }
            if (SettingsUtils.setNightMode(nightMode)) {
                requireActivity().recreate();
            } else {
                siAuto.setChecked(false);
                siDark.setChecked(false);
                siLight.setChecked(false);
            }
        }
        return true;
    }

}
