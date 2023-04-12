package com.mct.autotask.presentation.view.fragments.settings.necessary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.mct.autotask.R;
import com.mct.autotask.presentation.view.base.CustomBaseFragment;
import com.mct.autotask.presentation.view.common.SettingItem;
import com.mct.autotask.presentation.view.services.AutoTaskService;
import com.mct.autotask.utils.PermissionUtils;

public class SettingsPermissionFragment extends CustomBaseFragment implements SettingItem.OnClickSettingListener {

    private static final String EXTRA_FRAGMENT_ARG_KEY = ":settings:fragment_args_key";
    private static final String EXTRA_SHOW_FRAGMENT_ARGUMENTS = ":settings:show_fragment_args";
    private static final int DETECT_OVERLAY_CHANGE_INTERVAL = 250;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private SettingItem siOverlay, siAccessibility;
    private boolean mIsOverlayValid, mIsAccessibilityValid, mIsDetectingAccessibility;
    private final BroadcastReceiver onAccessibilityChange = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mIsAccessibilityValid != PermissionUtils.isAccessibilityPermissionValid()) {
                if (mIsDetectingAccessibility) {
                    moveTaskToFront();
                    return;
                }
                initPermissionState();
            }
        }
    };
    private final Runnable mCheckOverlayRunnable = new Runnable() {
        @Override
        public void run() {
            if (mIsOverlayValid != PermissionUtils.isOverlayPermissionValid()) {
                moveTaskToFront();
                return;
            }
            mHandler.postDelayed(mCheckOverlayRunnable, DETECT_OVERLAY_CHANGE_INTERVAL);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerAccessibilityChange();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_permission, container, false);
        initUi(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mIsDetectingAccessibility = false;
        stopDetectOverlayGranted();
        initPermissionState();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopDetectOverlayGranted();
        unregisterAccessibilityChange();
    }

    @Override
    public boolean onClicked(SettingItem view, boolean isChecked) {
        if (view == siOverlay) {
            startDetectOverlayGranted();
            Uri uri = Uri.parse("package:" + requireContext().getPackageName());
            Intent intent = getIntent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION).setData(uri);
            startActivity(intent);
        }
        if (view == siAccessibility) {
            mIsDetectingAccessibility = true;
            Bundle bundle = new Bundle();
            String showArgs = requireContext().getPackageName() + "/" + AutoTaskService.class.getName();
            bundle.putString(EXTRA_FRAGMENT_ARG_KEY, showArgs);
            Intent intent = getIntent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            intent.putExtra(EXTRA_FRAGMENT_ARG_KEY, showArgs);
            intent.putExtra(EXTRA_SHOW_FRAGMENT_ARGUMENTS, bundle);

            startActivity(intent);
        }
        return isChecked;
    }

    private void initUi(View view) {
        initToolbar(view);
        siOverlay = view.findViewById(R.id.si_overlay);
        siAccessibility = view.findViewById(R.id.si_accessibility);
        siOverlay.setOnClickSettingListener(this);
        siAccessibility.setOnClickSettingListener(this);
    }

    private void initPermissionState() {
        mIsOverlayValid = PermissionUtils.isOverlayPermissionValid();
        mIsAccessibilityValid = PermissionUtils.isAccessibilityPermissionValid();
        siOverlay.setChecked(mIsOverlayValid);
        siAccessibility.setChecked(mIsAccessibilityValid);
    }

    private Intent getIntent(String action) {
        return new Intent(action).addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_NO_HISTORY
        );
    }

    private void moveTaskToFront() {
        if (getActivity() == null) {
            return;
        }
        int flag = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;
        startActivity(new Intent(getActivity(), getActivity().getClass()).addFlags(flag));
    }

    private void registerAccessibilityChange() {
        IntentFilter intentFilter = new IntentFilter(AutoTaskService.ACTION_PERMISSION_CHANGE);
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(onAccessibilityChange, intentFilter);
    }

    private void unregisterAccessibilityChange() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(onAccessibilityChange);
    }

    private void startDetectOverlayGranted() {
        stopDetectOverlayGranted();
        mHandler.post(mCheckOverlayRunnable);
    }

    private void stopDetectOverlayGranted() {
        mHandler.removeCallbacks(mCheckOverlayRunnable);
    }


}
