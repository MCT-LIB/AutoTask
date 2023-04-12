package com.mct.autotask.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.admanager.AdManagerAdView;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAdLoadCallback;

import java.util.Collections;

public class GoogleAdsHelper {

    private static final String TAG = "AdsUtils";
    private static final String BANNER_ID = "/6499/example/banner";
    private static final String INTERSTITIAL_ID = "/6499/example/interstitial";
    private static GoogleAdsHelper instance;
    private boolean isInit;
    private boolean isBannerLoading, isBannerLoaded;
    private AdManagerAdView adView;
    private boolean isFullAdLoading;
    private AdManagerInterstitialAd interstitialAd;

    private GoogleAdsHelper() {
    }

    public static GoogleAdsHelper getInstance() {
        if (instance == null) {
            instance = new GoogleAdsHelper();
        }
        return instance;
    }

    public void init(Context context) {
        if (isInit) {
            return;
        }
        isInit = true;
        MobileAds.initialize(context, status -> {
        });
    }

    public void loadBanner(@NonNull FrameLayout container) {
        if (!isInit) {
            return;
        }
        Log.i(TAG, "banner: isBannerLoading -> " + isBannerLoading);
        Log.i(TAG, "banner: isBannerLoaded -> " + isBannerLoaded);
        MobileAds.setRequestConfiguration(new RequestConfiguration.Builder()
                .setTestDeviceIds(Collections.singletonList("ABCDEF012345"))
                .build());
        if (adView == null) {
            adView = new AdManagerAdView(container.getContext());
            adView.setAdSizes(AdSize.FULL_BANNER, AdSize.BANNER);
            adView.setAdUnitId(BANNER_ID);
            adView.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    isBannerLoading = isBannerLoaded = false;
                    Log.d(TAG, "banner: OnAdFailedToLoad -> " + loadAdError.getMessage());
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    isBannerLoading = false;
                    isBannerLoaded = true;
                    Log.i(TAG, "banner: OnBannerLoaded");
                }
            });
        }
        if (!isBannerLoading && !isBannerLoaded) {
            Log.d(TAG, "banner: AdView is loading!");
            isBannerLoading = true;
            adView.loadAd(new AdManagerAdRequest.Builder().build());
        }
        if (adView.getParent() instanceof ViewGroup) {
            ((ViewGroup) adView.getParent()).removeView(adView);
        }
        container.addView(adView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    public void resumeBanner() {
        if (isInit && isBannerLoaded) {
            adView.resume();
        }
    }

    public void pauseBanner() {
        if (isInit && isBannerLoaded) {
            adView.pause();
        }
    }

    public void destroyBanner() {
        if (isInit && isBannerLoaded) {
            adView.destroy();
            adView = null;
            isBannerLoaded = false;
        }
    }

    public void loadFullAds(Context context) {
        Log.i(TAG, "interstitial: loadFullAds isFullAdLoading -> " + isFullAdLoading);
        if (isFullAdLoading) {
            Log.i(TAG, "interstitial: Ad is loading!");
            return;
        }
        if (interstitialAd != null) {
            Log.i(TAG, "interstitial: Ad is loaded!");
            return;
        }
        isFullAdLoading = true;
        AdManagerAdRequest adRequest = new AdManagerAdRequest.Builder().build();
        AdManagerInterstitialAd.load(context, INTERSTITIAL_ID, adRequest,
                new AdManagerInterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull AdManagerInterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        instance.interstitialAd = interstitialAd;
                        isFullAdLoading = false;
                        Log.i(TAG, "interstitial: onAdLoaded");
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        Log.d(TAG, "interstitial: The ad was dismissed.");
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        instance.interstitialAd = null;
                                        loadFullAds(context);
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        Log.d(TAG, "interstitial: The ad failed to show.");
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        instance.interstitialAd = null;
                                        loadFullAds(context);
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.
                                        Log.d(TAG, "interstitial: The ad was shown.");
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, "interstitial: onAdFailedToLoad -> " + loadAdError.getMessage());
                        interstitialAd = null;
                        isFullAdLoading = false;
                    }
                });
    }

    public void showInterstitialAd(Activity activity) {
        if (interstitialAd != null) {
            interstitialAd.show(activity);
        } else {
            loadFullAds(activity.getApplicationContext());
        }
    }

    public boolean isBannerLoaded() {
        return isBannerLoaded && adView != null;
    }

    public boolean isFullAdLoaded() {
        return !isFullAdLoading && interstitialAd != null;
    }

}
