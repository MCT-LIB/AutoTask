package com.mct.autotask.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.AppCompatDelegate.NightMode;

import com.mct.autotask.BuildConfig;
import com.mct.autotask.presentation.internalmodel.Language;
import com.mct.autotask.presentation.internalmodel.PrimaryColor;

import java.util.Locale;

public class SettingsUtils {

    ///////////////////////////////////////////////////////////////////////////
    // Theme
    ///////////////////////////////////////////////////////////////////////////

    public static void restoreNightMode() {
        AppCompatDelegate.setDefaultNightMode(getNightMode());
    }

    public static boolean setNightMode(@NightMode int nightMode) {
        getPrefs().setNightMode(nightMode);
        if (nightMode == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) {
            nightMode = getSystemNightMode();
        }
        if (AppCompatDelegate.getDefaultNightMode() != nightMode) {
            AppCompatDelegate.setDefaultNightMode(nightMode);
            return true;
        }
        return false;
    }

    public static int getNightMode() {
        return getPrefs().getNightMode();
    }

    public static boolean isNightMode() {
        int nightMode = getNightMode();
        if (nightMode == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) {
            nightMode = getSystemNightMode();
        }
        return nightMode == AppCompatDelegate.MODE_NIGHT_YES;
    }

    public static int getSystemNightMode() {
        Configuration cf = Resources.getSystem().getConfiguration();
        return (cf.uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
                ? AppCompatDelegate.MODE_NIGHT_YES
                : AppCompatDelegate.MODE_NIGHT_NO;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Primary color
    ///////////////////////////////////////////////////////////////////////////

    public static void applyPrimaryColor(@NonNull Activity activity) {
        activity.setTheme(getPrimaryColor().getStyle());
    }

    public static boolean setPrimaryColor(PrimaryColor primaryColor) {
        getPrefs().setPrimaryColor(primaryColor);
        return true;
    }

    public static PrimaryColor getPrimaryColor() {
        return getPrefs().getPrimaryColor();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Localization language
    ///////////////////////////////////////////////////////////////////////////

    public static Context restoreLocale(Context context) {
        Locale local = getSelectedLanguage().getLocale();
        Configuration configuration = updateResources(context, local);
        return context.createConfigurationContext(configuration);
    }

    public static boolean setLocale(@NonNull Context context, @NonNull Language language) {
        Locale mLocale = getSelectedLanguage().getLocale();
        getPrefs().setSelectedLanguage(language);
        if (mLocale.equals(language.getLocale())) {
            return false;
        }
        Resources resources = context.getApplicationContext().getResources();
        Configuration configuration = updateResources(context.getApplicationContext(), language.getLocale());
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return true;
    }

    public static Language getSelectedLanguage() {
        return getPrefs().getSelectedLanguage();
    }

    @NonNull
    private static Configuration updateResources(@NonNull Context context, Locale locale) {
        Locale.setDefault(locale);
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);
        return configuration;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Preferences
    ///////////////////////////////////////////////////////////////////////////

    public static void init(Context context) {
        SettingPreferences.init(context);
    }

    public static SettingPreferences getPrefs() {
        return SettingPreferences.getInstance();
    }

    public static class SettingPreferences {

        private static final String PREF_NAME = BuildConfig.APPLICATION_ID + ".SettingPreference";

        // Theme
        private static final String KEY_MODE_NIGHT = "k_mode_night";
        private static final int DEFAULT_MODE_NIGHT = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
        // Primary Color
        private static final String KEY_PRIMARY_COLOR_LIGHT = "k_primary_color_light";
        private static final String KEY_PRIMARY_COLOR_DARK = "k_primary_color_dark";
        private static final int DEFAULT_PRIMARY_COLOR_LIGHT = PrimaryColor.DEFAULT_LIGHT.ordinal();
        private static final int DEFAULT_PRIMARY_COLOR_DARK = PrimaryColor.DEFAULT_DARK.ordinal();
        private static final String KEY_SPAN_COUNT_PORTRAIT = "k_span_count_portrait";
        private static final int DEFAULT_SPAN_COUNT_PORTRAIT = 4;
        private static final String KEY_SPAN_COUNT_LANDSCAPE = "k_span_count_landscape";
        private static final int DEFAULT_SPAN_COUNT_LANDSCAPE = 6;
        // Language
        private static final String KEY_SELECTED_LANGUAGE = "k_select_language";
        private static final int DEFAULT_SELECTED_LANGUAGE = Language.DEFAULT.ordinal();

        static SettingPreferences instance;
        private final SharedPreferences preferences;

        private SettingPreferences(@NonNull Context context) {
            preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }

        static void init(Context context) {
            if (instance == null) {
                instance = new SettingPreferences(context);
            }
        }

        static SettingPreferences getInstance() {
            return instance;
        }

        private SharedPreferences.Editor editor() {
            return preferences.edit();
        }

        /* ------------------------------------ Theme ------------------------------------------- */

        @NightMode
        public int getNightMode() {
            return preferences.getInt(KEY_MODE_NIGHT, DEFAULT_MODE_NIGHT);
        }

        public void setNightMode(@NightMode int mode) {
            editor().putInt(KEY_MODE_NIGHT, mode).apply();
        }

        /* -------------------------------- Primary color --------------------------------------- */

        public PrimaryColor getPrimaryColor() {
            String key = isNightMode() ? KEY_PRIMARY_COLOR_DARK : KEY_PRIMARY_COLOR_LIGHT;
            int def = isNightMode() ? DEFAULT_PRIMARY_COLOR_DARK : DEFAULT_PRIMARY_COLOR_LIGHT;
            return PrimaryColor.get(preferences.getInt(key, def));
        }

        public void setPrimaryColor(@NonNull PrimaryColor primaryColor) {
            String key = isNightMode() ? KEY_PRIMARY_COLOR_DARK : KEY_PRIMARY_COLOR_LIGHT;
            editor().putInt(key, primaryColor.ordinal()).apply();
        }

        public void setSpanCount(int spanCount, boolean isLandscape) {
            editor().putInt(isLandscape ? KEY_SPAN_COUNT_LANDSCAPE : KEY_SPAN_COUNT_PORTRAIT, spanCount).apply();
        }

        public int getSpanCount(boolean isLandscape) {
            if (isLandscape) {
                return preferences.getInt(KEY_SPAN_COUNT_LANDSCAPE, DEFAULT_SPAN_COUNT_LANDSCAPE);
            } else {
                return preferences.getInt(KEY_SPAN_COUNT_PORTRAIT, DEFAULT_SPAN_COUNT_PORTRAIT);
            }
        }

        /* ------------------------------------ Local ------------------------------------------- */

        public Language getSelectedLanguage() {
            return Language.get(preferences.getInt(KEY_SELECTED_LANGUAGE, DEFAULT_SELECTED_LANGUAGE));
        }

        public void setSelectedLanguage(@NonNull Language language) {
            editor().putInt(KEY_SELECTED_LANGUAGE, language.ordinal()).apply();
        }

    }

}
