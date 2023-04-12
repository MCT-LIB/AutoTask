package com.mct.autotask.presentation.view.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mct.autotask.R;
import com.mct.autotask.presentation.internalmodel.Language;
import com.mct.autotask.presentation.view.common.SettingItem;

public class LanguageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final OnSelectLanguageListener mListener;
    private Language mCurrent;

    public LanguageAdapter(Language current, OnSelectLanguageListener mListener) {
        this.mCurrent = current;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SettingItem item = new SettingItem(parent.getContext());
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        item.setLayoutParams(layoutParams);
        return new RecyclerView.ViewHolder(item) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Language language = Language.get(position);
        SettingItem item = (SettingItem) holder.itemView;
        item.setWidget(SettingItem.Widget.RadioBox, true);

        if (language == Language.DEFAULT) {
            item.setTitle(item.getContext().getString(R.string.settings_language_title_system_lang));
            item.setSummary(null);
        } else {
            item.setTitle(language.getDisplayLanguage());
            item.setSummary(language.getDisplayLanguageBySelf());
        }
        if (mCurrent == language) {
            item.setChecked(true);
        } else {
            item.setChecked(false);
            item.setOnClickSettingListener((view, isChecked) -> {
                int lastPos = mCurrent.ordinal();
                int newPos = language.ordinal();
                mCurrent = language;
                notifyItemChanged(lastPos);
                notifyItemChanged(newPos);
                if (mListener != null) mListener.onSelectedLanguage(language);
                return true;
            });
        }

    }

    @Override
    public int getItemCount() {
        return Language.values().length;
    }

    public interface OnSelectLanguageListener {
        void onSelectedLanguage(Language language);
    }
}
