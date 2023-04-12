package com.mct.autotask.presentation.view.fragments.settings.display;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mct.autotask.R;
import com.mct.autotask.presentation.internalmodel.PrimaryColor;
import com.mct.autotask.presentation.view.adapters.PrimaryColorAdapter;
import com.mct.autotask.presentation.view.base.CustomBaseFragment;
import com.mct.autotask.utils.SettingsUtils;

public class SettingsColorFragment extends CustomBaseFragment implements Toolbar.OnMenuItemClickListener, PrimaryColorAdapter.OnSelectColorListener {

    private int mSpanCount;
    private RecyclerView rcvColors;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_color, container, false);
        initUi(view);
        return view;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        initMenu();
        setLayoutManager();
    }

    @Override
    public boolean onMenuItemClick(@NonNull MenuItem item) {
        SettingsUtils.getPrefs().setSpanCount(item.getItemId(), isLandscape());
        setLayoutManager();
        return false;
    }

    @Override
    public void onSelectedColor(PrimaryColor primaryColor) {
        if (SettingsUtils.setPrimaryColor(primaryColor)) {
            requireActivity().recreate();
        }
    }

    private void initUi(@NonNull View view) {
        initToolbar(view);
        initMenu();
        rcvColors = view.findViewById(R.id.rcv_colors);
        rcvColors.setAdapter(new PrimaryColorAdapter(SettingsUtils.getPrimaryColor(), this));
        setLayoutManager();
    }

    private void initMenu() {
        int[] listItem = isLandscape() ? new int[]{6, 7, 8, 9} : new int[]{2, 3, 4, 5};
        toolbar.getMenu().clear();
        toolbar.setOnMenuItemClickListener(this);
        for (int spanCount : listItem) {
            String title = getString(R.string.settings_color_menu_span_count_n, spanCount);
            toolbar.getMenu().add(Menu.NONE, spanCount, Menu.NONE, title);
        }
    }

    private void setLayoutManager() {
        int spanCount = SettingsUtils.getPrefs().getSpanCount(isLandscape());
        if (mSpanCount != spanCount) {
            mSpanCount = spanCount;
            rcvColors.setLayoutManager(new GridLayoutManager(requireContext(), mSpanCount));
            rcvColors.scrollToPosition(SettingsUtils.getPrefs().getPrimaryColor().ordinal());
        }
    }

}
