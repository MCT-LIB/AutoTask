package com.mct.autotask.presentation.view.activities;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.ConnectivityManager.NetworkCallback;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.mct.autotask.R;
import com.mct.autotask.presentation.view.base.CustomBaseActivity;
import com.mct.autotask.presentation.view.fragments.root.RootFragment;
import com.mct.autotask.utils.GoogleAdsHelper;

public class RootActivity extends CustomBaseActivity {

    private static boolean isRecreate;
    private FrameLayout adViewContainer;
    private final NetworkCallback mNetworkCallback = new NetworkCallback() {
        @Override
        public void onAvailable(@NonNull Network network) {
            super.onAvailable(network);
            runOnUiThread(RootActivity.this::initAd);
        }
    };

    @Override
    public void recreate() {
        super.recreate();
        isRecreate = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        init();
        initData();
        initWindow();
        registerNetworkStatusChangedListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GoogleAdsHelper.getInstance().resumeBanner();
    }

    @Override
    protected void onPause() {
        super.onPause();
        GoogleAdsHelper.getInstance().pauseBanner();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isRecreate) {
            GoogleAdsHelper.getInstance().destroyBanner();
        }
        unRegisterNetworkStatusChangedListener();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        initWindow();
    }

    @Override
    protected int getContainerId() {
        return R.id.home_container;
    }

    @Override
    protected void showToastOnBackPressed() {
        Toast.makeText(this, R.string.toast_back_press, Toast.LENGTH_SHORT).show();
    }

    private void init() {
        adViewContainer = findViewById(R.id.ads_container);
        initAd();
    }

    private void initAd() {
        GoogleAdsHelper adsHelper = GoogleAdsHelper.getInstance();
        adsHelper.init(getApplicationContext());
        adsHelper.loadBanner(adViewContainer);
        adsHelper.loadFullAds(getApplicationContext());
    }

    private void initData() {
        if (isRecreate) {
            isRecreate = false;
        } else {
            replaceFragment(new RootFragment());
        }
    }

    private void initWindow() {
        setStatusBarsVisible(Resources.getSystem().getConfiguration().orientation == 1);
    }

    /**
     * Register the status of network changed listener.
     */
    private void registerNetworkStatusChangedListener() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return;
        cm.registerNetworkCallback(new NetworkRequest.Builder().build(), mNetworkCallback);
    }

    private void unRegisterNetworkStatusChangedListener() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return;
        cm.unregisterNetworkCallback(mNetworkCallback);
    }

    private void setStatusBarsVisible(boolean visible) {
        Window window = getWindow();
        WindowInsetsControllerCompat windowInsetsController = WindowCompat.getInsetsController(window, window.getDecorView());
        windowInsetsController.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        if (visible) {
            // Show the status bars.
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            windowInsetsController.show(WindowInsetsCompat.Type.statusBars());
        } else {
            // Hide the status bars.
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            windowInsetsController.hide(WindowInsetsCompat.Type.statusBars());
        }
    }

    public void showInterstitialAd() {
        GoogleAdsHelper.getInstance().showInterstitialAd(this);
    }

    public void showBanner(boolean show) {
        adViewContainer.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}