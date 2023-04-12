package com.mct.autotask.presentation.internalmodel;

import android.content.res.Resources;
import android.os.LocaleList;

import androidx.annotation.NonNull;

import java.util.Locale;

public enum Language {
    DEFAULT(null),
    EN("en_US"),
    VI("vi_VN");

    private final String language;

    Language(String language) {
        this.language = language;
    }

    public static Language get(int id) {
        try {
            return values()[id];
        } catch (Throwable throwable) {
            return DEFAULT;
        }
    }

    @NonNull
    public String getDisplayLanguage() {
        Locale locale = getLocale();
        return locale.getDisplayLanguage(Locale.ENGLISH);
    }

    @NonNull
    public String getDisplayLanguageBySelf() {
        Locale locale = getLocale();
        return locale.getDisplayLanguage(locale);
    }

    @NonNull
    public Locale getLocale() {
        try {
            String[] arr = language.split("_");
            return new Locale(arr[0], arr[1]);
        } catch (Throwable ignored) {
        }
        LocaleList localeList = Resources.getSystem().getConfiguration().getLocales();
        return !localeList.isEmpty() && localeList.get(0) != null
                ? localeList.get(0)
                : Locale.getDefault();
    }
}
