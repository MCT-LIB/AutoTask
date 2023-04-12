package com.mct.autotask.presentation.view.fragments.settings;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mct.autotask.R;
import com.mct.autotask.presentation.view.activities.RootActivity;
import com.mct.autotask.presentation.view.base.CustomBaseFragment;
import com.mct.autotask.presentation.view.fragments.settings.display.SettingsColorFragment;
import com.mct.autotask.presentation.view.fragments.settings.display.SettingsLanguageFragment;
import com.mct.autotask.presentation.view.fragments.settings.display.SettingsThemeFragment;
import com.mct.autotask.presentation.view.fragments.settings.necessary.SettingsPermissionFragment;
import com.mct.components.baseui.BaseActivity;

public class SettingsFragment extends CustomBaseFragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        initUi(view);
        return view;
    }

    @Override
    @SuppressLint("NonConstantResourceId")
    public void onClick(@NonNull View v) {
        Fragment fragment;
        switch (v.getId()) {
            default:
            case R.id.si_premium:
                if (getActivity() instanceof RootActivity) {
                    ((RootActivity) getActivity()).showInterstitialAd();
                }
                return;
            case R.id.si_permission:
                fragment = new SettingsPermissionFragment();
                break;
            case R.id.si_theme:
                fragment = new SettingsThemeFragment();
                break;
            case R.id.si_color:
                fragment = new SettingsColorFragment();
                break;
            case R.id.si_language:
                fragment = new SettingsLanguageFragment();
                break;
        }
        replaceFragment(fragment, true, BaseActivity.Anim.FADE);
    }

    private void initUi(@NonNull View view) {
        view.findViewById(R.id.si_premium).setOnClickListener(this);
        view.findViewById(R.id.si_permission).setOnClickListener(this);
        view.findViewById(R.id.si_theme).setOnClickListener(this);
        view.findViewById(R.id.si_color).setOnClickListener(this);
        view.findViewById(R.id.si_language).setOnClickListener(this);
    }

}
