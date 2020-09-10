package com.qubuyer.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.qubuyer.bean.ToolbarMenuEntity;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.Toolbar;

/**
 * @date 创建时间:2018/12/19
 * @author Susong
 * @description Toolbar抽象类
 & @version
 */
public abstract class AbsToolbar extends Toolbar {
    public interface OnNavigationClickListener {
        void onNavigationClick(View view);
    }

    public interface OnTitleOnClickListener{
        void onTitleOnClick(View view);
    }

    public interface OnMenuOnClickListener{
        void onMenuOnClick(View view);
    }

    public AbsToolbar(Context context) {
        super(context);
    }

    public AbsToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public abstract void setOnNavigationClickListener(OnNavigationClickListener onNavigationClickListener);

    public abstract void setOnTitleOnClickListener(OnTitleOnClickListener onTitleOnClickListener);

    public abstract void setTitle(CharSequence title);

    public abstract void setTitle(@StringRes int resId);

    public abstract void setTitleColor(@ColorInt int colorId);

    public abstract void setNavigationDrawable(@DrawableRes int drawableId);

    public abstract void clearMenuView();

    public abstract void addMenuView(Context context, ToolbarMenuEntity toolbarMenuEntity);
}
