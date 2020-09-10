package com.qubuyer.base.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.qubuyer.customview.AbsToolbar;
import com.qubuyer.customview.DefaultToolbar;
import com.qubuyer.utils.ActivityStackManager;

import androidx.annotation.ColorRes;
import androidx.annotation.MenuRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Susong
 * @date 创建时间:2018/12/19
 * @description common activity
 * & @version
 */
public abstract class BaseCommonActivity extends AppCompatActivity implements AbsToolbar.OnNavigationClickListener {
    private Unbinder mBind;
    private LinearLayout mContentView;
    protected AbsToolbar mToolbar;
    private Toolbar.OnMenuItemClickListener onMenuItemClickListener;
    protected BaseActivity.OnBackPressedListener onBackPressedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar();
        View view = null;
        if (0 != getContentView()) {
            view = getLayoutInflater().inflate(getContentView(), null);
        }
        if (null != view) {
            setContentView(view, view.getLayoutParams());
        }
        initWindow();
        ActivityStackManager.getActivityStackManager().pushActivity(this);
        mBind = ButterKnife.bind(this);
        initWidget(savedInstanceState);
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStackManager.getActivityStackManager().popActivity(this);
        if (mBind != null && mBind != Unbinder.EMPTY) {
            try {
                mBind.unbind();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (null == params) {
            params = generateDefaultLayoutParams();
        }
        if (mContentView.indexOfChild(view) == -1) {
            mContentView.addView(view, params);
        }
    }

    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    protected void initToolBar() {
        mContentView = new LinearLayout(this);
        mContentView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mContentView.setOrientation(LinearLayout.VERTICAL);
        mToolbar = toolbar();
        if (null != mToolbar) {
            ViewGroup.LayoutParams layoutParams = mToolbar.getLayoutParams();
            if (null == layoutParams) {
                layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            mToolbar.setOnNavigationClickListener(this);
            mContentView.addView(mToolbar, layoutParams);
        }
        super.setContentView(mContentView);
    }

    protected void initWindow() {
    }

    /**
     * 请求数据,子类选择实现
     */
    protected void initData() {

    }

    /**
     * 初始化组件，设置监听器，子类选择实现
     */
    protected void initWidget(Bundle savedInstanceState) {

    }

    /**
     * 布局初始化，子类必须实现
     *
     * @return
     */
    protected abstract int getContentView();

    /**
     * 获取Toolbar实例,该方法可以被重写
     *
     * @return
     */
    public AbsToolbar toolbar() {
        if (null == mToolbar) {
            mToolbar = new DefaultToolbar(this);
        }
        return mToolbar;
    }

    @Override
    public void setTitle(CharSequence title) {
        if (null != mToolbar) {
            mToolbar.setTitle(title);
        }
    }

    public void setTitleColor(int colorId) {
        if (null != mToolbar) {
            mToolbar.setTitleColor(colorId);
        }
    }

    public void setNavigationDrawable(int drawableId) {
        if (null != mToolbar) {
            mToolbar.setNavigationDrawable(drawableId);
        }
    }

    public void setToolbarBackgroundColor(@ColorRes int colorId) {
        if (null != mToolbar) {
            mToolbar.setBackgroundColor(getResources().getColor(colorId));
        }
    }

    public void inflateMenu(@MenuRes int menuId) {
        clearMenu();
        if (null != mToolbar) {
            mToolbar.inflateMenu(menuId);
            mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (null != onMenuItemClickListener) {
                        onMenuItemClickListener.onMenuItemClick(item);
                    }
                    BaseCommonActivity.this.onMenuItemClick(item);
                    return true;
                }
            });
            // Ugly code... But i can't help...
            Menu menu = mToolbar.getMenu();
            if (null != menu) {
                for (int i = 0; i < menu.size(); i++) {
                    MenuItem item = menu.getItem(i);
                    SpannableString text = new SpannableString(item.getTitle() + "");
                    text.setSpan(new ForegroundColorSpan(Color.BLACK), 0, text.length(), 0);
                    item.setTitle(text);
                }
            }
        }
    }

    public void clearMenu() {
        if (null != mToolbar) {
            mToolbar.getMenu().clear();
        }
    }

    protected void onMenuItemClick(MenuItem item) {
    }

    @Override
    public void onNavigationClick(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void setOnMenuItemClickListener(final Toolbar.OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    public void setOnBackPressedListener(BaseActivity.OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    public interface OnBackPressedListener {
        boolean onBackPressed();
    }

}
