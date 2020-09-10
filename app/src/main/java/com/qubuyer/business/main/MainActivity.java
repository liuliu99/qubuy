package com.qubuyer.business.main;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseFragmentActivity;
import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.event.GoToMainEvent;
import com.qubuyer.bean.event.GoToMineEvent;
import com.qubuyer.bean.event.GoToShopCartEvent;
import com.qubuyer.business.category.fragment.CategoryFragment;
import com.qubuyer.business.home.fragment.HomeFragment;
import com.qubuyer.business.mine.fragment.MineFragment;
import com.qubuyer.business.shopcart.fragment.ShopCartFragment;
import com.qubuyer.customview.AbsToolbar;
import com.qubuyer.customview.ShadowDrawable;
import com.qubuyer.utils.EventBusUtil;
import com.qubuyer.utils.HandlerUtil;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseFragmentActivity {
    private boolean mIsExit;
    @BindView(R.id.rbtn_home)
    RadioButton rbtnHome;
    @BindView(R.id.rbtn_category)
    RadioButton rbtn_category;
    @BindView(R.id.rbtn_shopping)
    RadioButton rbtn_shopping;
    @BindView(R.id.rbtn_mine)
    RadioButton rbtnMine;
    @BindView(R.id.rg_home)
    RadioGroup rgHome;

    private int mSelectedCheckId;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_main;
    }

    @Override
    protected WrapperPresenter createP(Context context) {
        return null;
    }

    @Override
    public int containerId() {
        return R.id.rl_container;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        EventBusUtil.register(this);
        BarUtils.setStatusBarLightMode(this, true);
        setRadioButtonNull();
        ShadowDrawable.setShadowDrawable(rgHome, Color.parseColor("#FFFFFF"), ConvertUtils.dp2px(0), Color.parseColor("#0D2E2C27"), ConvertUtils.dp2px(0), 0, 0);
        mSelectedCheckId = R.id.rbtn_home;
        push(HomeFragment.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTabStatus();
    }

    private void setRadioButtonNull() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            rbtnHome.setButtonDrawable(android.R.color.transparent);
            rbtn_category.setButtonDrawable(android.R.color.transparent);
            rbtn_shopping.setButtonDrawable(android.R.color.transparent);
            rbtnMine.setButtonDrawable(android.R.color.transparent);
        }
    }

    @Override
    public AbsToolbar toolbar() {
        return null;
    }

    @OnClick({R.id.rbtn_home, R.id.rbtn_category, R.id.rbtn_shopping, R.id.rbtn_mine})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rbtn_home:
                mSelectedCheckId = view.getId();
                push(0, 0, HomeFragment.class, null);
                break;
            case R.id.rbtn_category:
                mSelectedCheckId = view.getId();
                push(0, 0, CategoryFragment.class, null);
                break;
            case R.id.rbtn_shopping:
                mSelectedCheckId = view.getId();
                push(0, 0, ShopCartFragment.class, null);
                break;
            case R.id.rbtn_mine:
                mSelectedCheckId = view.getId();
                push(0, 0, MineFragment.class, null);
                break;
        }
    }

    private void setTabStatus() {
        switch (mSelectedCheckId) {
            case R.id.rbtn_home:
                rbtnHome.setChecked(true);
                break;
            case R.id.rbtn_category:
                rbtn_category.setChecked(true);
                break;
            case R.id.rbtn_shopping:
                rbtn_shopping.setChecked(true);
                break;
            case R.id.rbtn_mine:
                rbtnMine.setChecked(true);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregister(this);
    }

    /**
     * 双击返回键退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
                ActivityUtils.finishAllActivities();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            } else {
                showToastCenter("再按一次退出");
                mIsExit = true;
                HandlerUtil.getInstance().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsExit = false;
                    }
                }, 2000);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Subscribe
    public void onEventMainThread(GoToMainEvent event) {
        mSelectedCheckId = R.id.rbtn_home;
        setTabStatus();
        push(0, 0, HomeFragment.class, null);
    }

    @Subscribe
    public void onEventMainThread(GoToShopCartEvent event) {
        mSelectedCheckId = R.id.rbtn_shopping;
        setTabStatus();
        push(0, 0, ShopCartFragment.class, null);
    }

    @Subscribe
    public void onEventMainThread(GoToMineEvent event) {
        mSelectedCheckId = R.id.rbtn_mine;
        setTabStatus();
        push(0, 0, MineFragment.class, null);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
