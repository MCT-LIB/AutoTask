package com.mct.autotask.presentation.view.services;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.lang.ref.WeakReference;

public class AutoTaskService extends AccessibilityService {

    public static final String ACTION_PERMISSION_CHANGE = "act_permission_change";
    private static final String TAG = "AutoTaskService";
    private static WeakReference<AutoTaskService> sAutoTaskService;

    public static boolean isServiceActive() {
        return sAutoTaskService != null && sAutoTaskService.get() != null;
    }

    @Override
    protected void onServiceConnected() {
        Log.e(TAG, "onServiceConnected: ");
        sAutoTaskService = new WeakReference<>(this);
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ACTION_PERMISSION_CHANGE));

        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        // pass the typeof events you want your service to listen to
        // other will not be handled by this service
        info.flags = AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS |
                AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS;

        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;

        // In case you want your service to work only with a particular application
        //or when that application is in foreground, you should specify those applications package
        //names here, otherwise the service would listen events from all the applications
        info.packageNames = null;

        // Set the type of feedback your service will provide.
        // info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;

        // the notification timeout is the time interval after which the service would
        // listen from the system. Anything happening between that interval won't be
        // captured by the service
        info.notificationTimeout = 100;

        // finally set the serviceInfo
        this.setServiceInfo(info);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.e(TAG, "onAccessibilityEvent: " + event);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind: ");
        sAutoTaskService.clear();
        sAutoTaskService = null;
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ACTION_PERMISSION_CHANGE));
        return super.onUnbind(intent);
    }

    @Override
    public void onInterrupt() {

    }
}
