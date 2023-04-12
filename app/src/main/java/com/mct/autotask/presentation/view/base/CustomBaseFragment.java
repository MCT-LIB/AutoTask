package com.mct.autotask.presentation.view.base;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.mct.autotask.R;

public abstract class CustomBaseFragment extends com.mct.components.baseui.BaseFragment {

    protected Toolbar toolbar;

    protected void initToolbar(@NonNull View view) {
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> popLastFragment());
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Resources resources = requireContext().getApplicationContext().getResources();
        resources.updateConfiguration(newConfig, resources.getDisplayMetrics());
        updateToolbar();
    }

    private void updateToolbar() {
        if (toolbar == null) {
            return;
        }
        Context context = toolbar.getContext();
        TypedArray a = context.obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        int actionBarSize = a.getDimensionPixelSize(0, 0);
        a.recycle();
        // set height for tool bar
        if (actionBarSize != 0) {
            ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.height = actionBarSize;
            }
            toolbar.setMinimumHeight(actionBarSize);
        }
        // set text style for
        toolbar.setTitleTextAppearance(context, androidx.appcompat.R.style.TextAppearance_Widget_AppCompat_Toolbar_Title);
        toolbar.setSubtitleTextAppearance(context, androidx.appcompat.R.style.TextAppearance_Widget_AppCompat_Toolbar_Subtitle);
        int textColor = getResources().getColor(R.color.toolbar_color, null);
        toolbar.setTitleTextColor(textColor);
        toolbar.setSubtitleTextColor(textColor);
        toolbar.requestLayout();
    }

    protected final boolean isLandscape() {
        return Resources.getSystem().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
}
