package com.mct.autotask.presentation.view.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mct.autotask.App;
import com.mct.autotask.R;
import com.mct.autotask.presentation.internalmodel.PrimaryColor;
import com.mct.components.utils.ScreenUtils;

public class PrimaryColorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final OnSelectColorListener mListener;
    private final PrimaryColor mCurrent;
    private final Resources.Theme theme;

    public PrimaryColorAdapter(PrimaryColor current, OnSelectColorListener listener) {
        this.mCurrent = current;
        this.mListener = listener;
        this.theme = App.getInstance().getResources().newTheme();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(new PrimaryColorFrame(parent.getContext())) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PrimaryColor primaryColor = PrimaryColor.get(position);
        PrimaryColorFrame view = (PrimaryColorFrame) holder.itemView;
        view.setBackgroundTintList(ColorStateList.valueOf(primaryColor.getDisplayColor(theme)));
        if (view.getChildCount() > 0) {
            view.removeAllViews();
        }
        if (mCurrent == primaryColor) {
            int size = ScreenUtils.dp2px(24);
            ImageView check = new ImageView(view.getContext());
            check.setImageResource(R.drawable.ic_checked_circle);
            view.addView(check, new FrameLayout.LayoutParams(size, size, Gravity.CENTER));
            view.setOnClickListener(null);
        } else {
            view.setOnClickListener(v -> {
                if (mListener != null) mListener.onSelectedColor(primaryColor);
            });
        }
    }

    @Override
    public int getItemCount() {
        return PrimaryColor.values().length;
    }

    public interface OnSelectColorListener {
        void onSelectedColor(PrimaryColor primaryColor);
    }

    public static class PrimaryColorFrame extends FrameLayout {

        public PrimaryColorFrame(Context context) {
            super(context);
            ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(2, 2, 2, 2);
            setLayoutParams(layoutParams);
            setBackgroundResource(R.drawable.ripple_transparent_rectangle);
        }

        @Override
        protected void onMeasure(int size, int heightMeasureSpec) {
            super.onMeasure(size, size);
        }
    }
}
