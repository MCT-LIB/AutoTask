package com.mct.autotask.presentation.internalmodel;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.mct.autotask.R;

public enum PrimaryColor {

    Colors_01(R.style.AppTheme_Colors_01),
    Colors_02(R.style.AppTheme_Colors_02),
    Colors_03(R.style.AppTheme_Colors_03),
    Colors_04(R.style.AppTheme_Colors_04),
    Colors_05(R.style.AppTheme_Colors_05),
    Colors_06(R.style.AppTheme_Colors_06),
    Colors_07(R.style.AppTheme_Colors_07),
    Colors_08(R.style.AppTheme_Colors_08),
    Colors_09(R.style.AppTheme_Colors_09),
    Colors_10(R.style.AppTheme_Colors_10),
    Colors_11(R.style.AppTheme_Colors_11),
    Colors_12(R.style.AppTheme_Colors_12),
    Colors_13(R.style.AppTheme_Colors_13),
    Colors_14(R.style.AppTheme_Colors_14),
    Colors_15(R.style.AppTheme_Colors_15),
    Colors_16(R.style.AppTheme_Colors_16),
    Colors_17(R.style.AppTheme_Colors_17),
    Colors_18(R.style.AppTheme_Colors_18);

    public static final PrimaryColor DEFAULT_LIGHT = Colors_06;
    public static final PrimaryColor DEFAULT_DARK = Colors_18;

    private final int style;

    PrimaryColor(@StyleRes int style) {
        this.style = style;
    }

    public static PrimaryColor get(int id) {
        try {
            return values()[id];
        } catch (Throwable throwable) {
            return DEFAULT_LIGHT;
        }
    }

    @StyleRes
    public int getStyle() {
        return style;
    }

    public int getDisplayColor(@NonNull Resources.Theme theme) {
        int mColor;
        theme.applyStyle(style, true);
        TypedArray ta = theme.obtainStyledAttributes(new int[]{android.R.attr.colorPrimary});
        mColor = ta.getColor(0, Color.BLACK);
        ta.recycle();
        return mColor;
    }

}
