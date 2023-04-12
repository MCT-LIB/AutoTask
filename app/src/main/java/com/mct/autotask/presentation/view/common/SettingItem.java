package com.mct.autotask.presentation.view.common;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.mct.autotask.R;

public class SettingItem extends LinearLayoutCompat {

    private static final int UNSET = Integer.MIN_VALUE;
    private TextView mTitle;
    private TextView mSummary;
    private FrameLayout mLeftFrame, mRightFrame;
    private OnClickSettingListener mOnClickSettingListener;
    private boolean checked;

    public SettingItem(@NonNull Context context) {
        this(context, null);
    }

    public SettingItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_setting, this, true);
        mTitle = view.findViewById(R.id.tv_title);
        mSummary = view.findViewById(R.id.tv_summary);
        mLeftFrame = view.findViewById(R.id.left_frame);
        mRightFrame = view.findViewById(R.id.right_frame);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SettingItem, defStyleAttr, 0);
        setTitle(a.getString(R.styleable.SettingItem_st_title));
        setSummary(a.getString(R.styleable.SettingItem_st_summary));
        setWidget(a.getInt(R.styleable.SettingItem_st_widget, Widget.Icon.ordinal()), true);
        setWidget(a.getInt(R.styleable.SettingItem_st_widget_end, Widget.None.ordinal()), false);
        setIcon(a.getResourceId(R.styleable.SettingItem_st_icon, 0));
        setIconTint(a.getColor(R.styleable.SettingItem_st_icon_tint, UNSET));
        setIconEnd(a.getResourceId(R.styleable.SettingItem_st_icon_end, 0));
        setIconEndTint(a.getColor(R.styleable.SettingItem_st_icon_tint_end, UNSET));
        setChecked(a.getBoolean(R.styleable.SettingItem_st_checked, false));
        setEnabled(a.getBoolean(R.styleable.SettingItem_st_enabled, true));
        a.recycle();

        setOnClickListener(v -> {
            if (mOnClickSettingListener != null) {
                setChecked(mOnClickSettingListener.onClicked(this, checked));
            }
        });
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        if (getLeftWidget() instanceof Checkable) {
            ((Checkable) getLeftWidget()).setChecked(checked);
        }
        if (getRightWidget() instanceof Checkable) {
            ((Checkable) getRightWidget()).setChecked(checked);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mTitle.setEnabled(enabled);
        mSummary.setEnabled(enabled);
        if (getLeftWidget() != null) {
            getLeftWidget().setEnabled(enabled);
        }
        if (getRightWidget() != null) {
            getRightWidget().setEnabled(enabled);
        }
    }

    public void setIcon(int iconRes) {
        if (getLeftWidget() instanceof ImageView) {
            ((ImageView) getLeftWidget()).setImageResource(iconRes);
        }
    }

    public void setIconTint(int color) {
        if (getLeftWidget() instanceof ImageView && color != UNSET) {
            ((ImageView) getLeftWidget()).setImageTintList(ColorStateList.valueOf(color));
        }
    }

    public void setIconEnd(int iconRes) {
        if (getRightWidget() instanceof ImageView) {
            ((ImageView) getRightWidget()).setImageResource(iconRes);
        }
    }

    public void setIconEndTint(int color) {
        if (getRightWidget() instanceof ImageView && color != UNSET) {
            ((ImageView) getRightWidget()).setImageTintList(ColorStateList.valueOf(color));
        }
    }

    @SuppressWarnings("unused")
    public void setOnClickSettingListener(OnClickSettingListener mOnClickSettingListener) {
        this.mOnClickSettingListener = mOnClickSettingListener;
    }

    @SuppressWarnings("unused")
    public void setTitle(int stringRes) {
        setTitle(getContext().getString(stringRes));
    }

    @SuppressWarnings("unused")
    public void setSummary(int stringRes) {
        setSummary(getContext().getString(stringRes));
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setSummary(String summary) {
        mSummary.setText(summary);
        mSummary.setVisibility(TextUtils.isEmpty(summary) ? GONE : VISIBLE);
    }

    private void setWidget(int widget, boolean isLeft) {
        setWidget(Widget.getWidgetById(widget), isLeft);
    }

    public void setWidget(@NonNull Widget widget, boolean isLeft) {
        FrameLayout frame = isLeft ? mLeftFrame : mRightFrame;
        frame.removeAllViews();
        if (widget == Widget.None) {
            frame.setVisibility(GONE);
            return;
        }
        frame.addView(widget.getWidget(getContext()));
        frame.setVisibility(VISIBLE);
    }

    private View getLeftWidget() {
        return mLeftFrame.getChildAt(0);
    }

    private View getRightWidget() {
        return mRightFrame.getChildAt(0);
    }

    public enum Widget {
        None(0),
        Icon(R.layout.widget_icon),
        CheckBox(R.layout.widget_checkbox),
        RadioBox(R.layout.widget_radiobox),
        Switch(R.layout.widget_switch);

        final int layoutRes;

        Widget(@LayoutRes int layoutRes) {
            this.layoutRes = layoutRes;
        }

        static Widget getWidgetById(int id) {
            try {
                return values()[id];
            } catch (Throwable t) {
                return None;
            }
        }

        @Nullable
        View getWidget(Context context) {
            if (this == Widget.None) {
                return null;
            }
            return LayoutInflater.from(context).inflate(layoutRes, null);
        }

    }

    public interface OnClickSettingListener {
        /**
         * @param view      view
         * @param isChecked item is checked
         * @return bool ->  new check after click
         */
        boolean onClicked(SettingItem view, boolean isChecked);
    }
}
