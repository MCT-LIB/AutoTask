package com.mct.autotask.presentation.view.fragments.settings.display;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mct.autotask.R;
import com.mct.autotask.presentation.internalmodel.Language;
import com.mct.autotask.presentation.view.adapters.LanguageAdapter;
import com.mct.autotask.presentation.view.base.CustomBaseFragment;
import com.mct.autotask.utils.SettingsUtils;

public class SettingsLanguageFragment extends CustomBaseFragment implements LanguageAdapter.OnSelectLanguageListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_language, container, false);
        initUi(view);
        return view;
    }

    @Override
    public void onSelectedLanguage(@NonNull Language language) {
        if (SettingsUtils.setLocale(requireContext(), language)) {
            requireActivity().recreate();
        }
    }

    private void initUi(@NonNull View view) {
        initToolbar(view);
        RecyclerView rcvLanguage = view.findViewById(R.id.rcv_language);
        rcvLanguage.setLayoutManager(new LinearLayoutManager(requireContext()));
        rcvLanguage.setAdapter(new LanguageAdapter(SettingsUtils.getSelectedLanguage(), this));
    }

}
