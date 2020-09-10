package com.qubuyer.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.qubuyer.R;
import com.qubuyer.bean.ToolbarMenuEntity;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

/**
 * @date 创建时间:2018/12/19
 * @author Susong
 * @description 顶部Toolbar
 & @version
 */
public class DefaultToolbar extends AbsToolbar {
    private OnNavigationClickListener onNavigationClickListener;
    private OnTitleOnClickListener onTitleOnClickListener;
    private TextView mTitleView;
    private TextView mNavigationView;
    private LinearLayout mMenuContainer;

    public DefaultToolbar(Context context) {
        super(context);
        initView(context);
    }

    public DefaultToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public DefaultToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        setPadding(ConvertUtils.dp2px(5), ConvertUtils.dp2px(5), ConvertUtils.dp2px(5), ConvertUtils.dp2px(5));
        setContentInsetsAbsolute(0, 0);
//        setBackgroundColor(getResources().getColor(R.color.toolbar_background_color));
        setBackground(ContextCompat.getDrawable(context, R.color.white));
        setTitleTextAppearance(getContext(), R.style.ToolbarTitleStyle);
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.actionBarSize, typedValue, true);
        int[] actionBarSizeArr = new int[]{android.R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = context.obtainStyledAttributes(typedValue.data, actionBarSizeArr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, actionBarSize));

        mNavigationView = new TextView(context);
        LayoutParams lp = new LayoutParams(ConvertUtils.dp2px(60.0f), ViewGroup.LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.START | Gravity.CENTER_VERTICAL;
        mNavigationView.setLayoutParams(lp);
        mNavigationView.setClickable(true);
        mNavigationView.setPadding(ConvertUtils.dp2px(10), 0, 0, 0);
        mNavigationView.setMinWidth(ConvertUtils.dp2px(56));
        mNavigationView.setMaxWidth(ConvertUtils.dp2px(100));
        Drawable returnImg = getContext().getResources().getDrawable(R.drawable.icon_return_black);
        if (null != returnImg) {
            returnImg.setBounds(0, 0, returnImg.getMinimumWidth(), returnImg.getMinimumHeight());
        }
        mNavigationView.setCompoundDrawables(returnImg, null, null, null);
        mNavigationView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onNavigationClickListener) {
                    onNavigationClickListener.onNavigationClick(v);
                }
            }
        });
        addView(mNavigationView);

        mTitleView = new TextView(context);
        mTitleView.setSingleLine(true);
        LayoutParams lp1 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp1.gravity = Gravity.CENTER;
        mTitleView.setLayoutParams(lp1);
        mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.0f);
        mTitleView.setTextColor(getResources().getColor(R.color.toolbar_title_color));
        mTitleView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTitleOnClickListener != null) {
                    onTitleOnClickListener.onTitleOnClick(v);
                }
            }
        });
        addView(mTitleView);

        mMenuContainer = new LinearLayout(context);
        lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
        mMenuContainer.setLayoutParams(lp);
        addView(mMenuContainer);
    }

    private View createMenuView(Context context, ToolbarMenuEntity toolbarMenuEntity) {
        TextView mMenuView = new TextView(context);
        LayoutParams lp = new LayoutParams(toolbarMenuEntity.getDpWidth() == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : ConvertUtils.dp2px(toolbarMenuEntity.getDpWidth()), toolbarMenuEntity.getDpHeight() == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : ConvertUtils.dp2px(toolbarMenuEntity.getDpHeight()));
        lp.gravity = Gravity.CENTER;
        lp.rightMargin = ConvertUtils.dp2px(15);
        mMenuView.setLayoutParams(lp);
        mMenuView.setClickable(true);
        mMenuView.setPadding(0, 0, 0, ConvertUtils.dp2px(0));
        if (!TextUtils.isEmpty(toolbarMenuEntity.getMenuContent())) {
            mMenuView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15.0f);
            mMenuView.setTextColor(getResources().getColor(R.color.common_text_color1));
            mMenuView.setText(toolbarMenuEntity.getMenuContent());
            mMenuView.setId(toolbarMenuEntity.getMenuId());
        } else if (toolbarMenuEntity.getMenuDrawaleId() != 0) {
            Drawable returnImg = ContextCompat.getDrawable(context, toolbarMenuEntity.getMenuDrawaleId());
            if (null != returnImg) {
                returnImg.setBounds(0, 0, ConvertUtils.dp2px(toolbarMenuEntity.getDpWidth()), ConvertUtils.dp2px(toolbarMenuEntity.getDpHeight()));
//                returnImg.setBounds(0, 0, 80, 80);
            }
            mMenuView.setCompoundDrawables(returnImg, null, null, null);
//            mMenuView.setCompoundDrawablesWithIntrinsicBounds(returnImg, null, null, null);
        }
        mMenuView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != toolbarMenuEntity.getOnMenuOnClickListener()) {
                    toolbarMenuEntity.getOnMenuOnClickListener().onMenuOnClick(v);
                }
            }
        });
        return mMenuView;
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitleView.setText(title);
    }

    @Override
    public void setTitle(@StringRes int resId) {
        mTitleView.setText(getResources().getString(resId));
    }

    @Override
    public void setTitleColor(@ColorInt int colorId) {
        mTitleView.setTextColor(colorId);
    }

    @Override
    public void setNavigationDrawable(@DrawableRes int drawableId) {
        Drawable returnImg = getContext().getResources().getDrawable(drawableId);
        if (null != returnImg) {
            returnImg.setBounds(0, 0, returnImg.getMinimumWidth(), returnImg.getMinimumHeight());
        }
        mNavigationView.setCompoundDrawables(returnImg, null, null, null );
    }

    @Override
    public void clearMenuView() {
        mMenuContainer.removeAllViews();
    }

    @Override
    public void addMenuView(Context context, ToolbarMenuEntity toolbarMenuEntity) {
        mMenuContainer.removeAllViews();
        mMenuContainer.addView(createMenuView(context, toolbarMenuEntity));
    }

    @Override
    public void setOnNavigationClickListener(OnNavigationClickListener onNavigationClickListener) {
        this.onNavigationClickListener = onNavigationClickListener;
    }

    @Override
    public void setOnTitleOnClickListener(OnTitleOnClickListener onTitleOnClickListener) {
        this.onTitleOnClickListener = onTitleOnClickListener;
    }
}
