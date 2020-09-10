package com.qubuyer.base.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.MenuRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.qubuyer.R;
import com.qubuyer.base.mvp.BaseView;
import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.ToolbarMenuEntity;
import com.qubuyer.business.login.activity.LoginActivity;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.customview.AbsToolbar;
import com.qubuyer.customview.DefaultToolbar;
import com.qubuyer.customview.LoadingDialog;
import com.qubuyer.utils.ActivityStackManager;
import com.qubuyer.utils.DialogUtil;
import com.qubuyer.utils.NavigationUtil;
import com.qubuyer.utils.SessionUtil;
import com.qubuyer.utils.StatusBarUtil;
import com.qubyer.okhttputil.helper.HttpManager;
import com.qubyer.okhttputil.utils.HttpConstant;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoImpl;
import org.devio.takephoto.model.InvokeParam;
import org.devio.takephoto.model.TContextWrap;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.permission.InvokeListener;
import org.devio.takephoto.permission.PermissionManager;
import org.devio.takephoto.permission.TakePhotoInvocationHandler;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Susong
 * @date 创建时间:2018/12/19
 * @description mvp base activity
 * & @version
 */
public abstract class BaseActivity<T extends WrapperPresenter> extends AppCompatActivity implements BaseView, AbsToolbar.OnNavigationClickListener, AbsToolbar.OnTitleOnClickListener, TakePhoto.TakeResultListener, InvokeListener {
    protected T mPresenter;
    private Unbinder mBind;
    protected LinearLayout mContentView;
    protected View mStatusBar;
    protected AbsToolbar mToolbar;
    private AbsToolbar.OnTitleOnClickListener onTitleOnClickListener;
    private Toolbar.OnMenuItemClickListener onMenuItemClickListener;
    protected OnBackPressedListener onBackPressedListener;
    // 用于显示 Loading Dialog
    public LoadingDialog mDialog;

    private TakePhoto mTakePhoto;
    protected InvokeParam mInvokeParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        StatusBarUtil.transparentStatusBar(this);
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
        mPresenter = createP(this);
        if (SessionUtil.getInstance().isLogined()) {
            String token = SessionUtil.getInstance().getToken();
            if (token != null) {
                HttpManager.getInstance().init(token, SessionUtil.getInstance().getTokenOverduedListener());
            }
        }
        initWidget(savedInstanceState);
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        initData();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        BarUtils.setStatusBarLightMode(this, true);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStackManager.getActivityStackManager().popActivity(this);
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter.destoryModel();
        }
        if (mBind != null && mBind != Unbinder.EMPTY) {
            try {
                mBind.unbind();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void initToolBar() {
        mContentView = new LinearLayout(this);
        mContentView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mContentView.setOrientation(LinearLayout.VERTICAL);
        mStatusBar = statusBar();
        if (null != mStatusBar) {
            mContentView.addView(mStatusBar);
        }
        mToolbar = toolbar();
        if (null != mToolbar) {
            ViewGroup.LayoutParams layoutParams = mToolbar.getLayoutParams();
            if (null == layoutParams) {
                layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            mToolbar.setOnNavigationClickListener(this);
            mToolbar.setOnTitleOnClickListener(this);
            mContentView.addView(mToolbar, layoutParams);
        }
        super.setContentView(mContentView);
    }

    public void inflateMenu(List<ToolbarMenuEntity> toolbarMenuEntities) {
        if (null != mToolbar) {
            for (ToolbarMenuEntity toolBarMenuEntity : toolbarMenuEntities) {
                mToolbar.addMenuView(this, toolBarMenuEntity);
            }
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
                    BaseActivity.this.onMenuItemClick(item);
                    return true;
                }
            });
            // Ugly code... But i can't help...
            Menu menu = mToolbar.getMenu();
            if (null != menu) {
                for (int i = 0; i < menu.size(); i++) {
                    MenuItem item = menu.getItem(i);
                    SpannableString text = new SpannableString(item.getTitle() + "");
                    text.setSpan(new ForegroundColorSpan(Color.WHITE), 0, text.length(), 0);
                    text.setSpan(new RelativeSizeSpan(1.1f), 0, text.length(), 0);
                    item.setTitle(text);
                }
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

    protected void initWindow() {
    }

    /**
     * 请求数据,子类选择实现
     */
    protected void initData() {

    }

    /**
     * 初始化组件，设置监听器，子类选择实现
     *
     * @param savedInstanceState
     */
    protected void initWidget(Bundle savedInstanceState) {

    }

    /**
     * 布局初始化，子类必须实现
     *
     * @return resId
     */
    protected abstract int getContentView();

    /**
     * 请求数据,子类选择实现
     */
    protected abstract T createP(Context context);

    public View statusBar() {
        if (null == mStatusBar) {
            mStatusBar = new View(this);
            mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, BarUtils.getStatusBarHeight()));
            mStatusBar.setBackground(ContextCompat.getDrawable(this, R.color.white));
        }
        return mStatusBar;
    }

    public void setStatusBarVisibility(boolean visibility) {
        if (null != mStatusBar) {
            mStatusBar.setVisibility(visibility ? View.VISIBLE : View.GONE);
        }
    }

    public void setStatusBarInVisibility(boolean visibility) {
        if (null != mStatusBar) {
            mStatusBar.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public void setStatusBarDrawable(int drawableId) {
        if (null != mStatusBar) {
            mStatusBar.setBackground(drawableId != 0 ? ContextCompat.getDrawable(this, drawableId) : null);
        }
    }

    public void setStatusBarColor(@ColorInt int colorId) {
        if (null != mStatusBar) {
            mStatusBar.setBackgroundColor(colorId);
        }
    }

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
            mToolbar.setTitleColor(ContextCompat.getColor(this, colorId));
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

    public void setToolbarBackgroundDrawable(int drawableId) {
        if (null != mStatusBar) {
            mToolbar.setBackground(drawableId != 0 ? ContextCompat.getDrawable(this, drawableId) : null);
        }
    }

    public void clearMenu() {
        if (null != mToolbar) {
            mToolbar.getMenu().clear();
            mToolbar.clearMenuView();
        }
    }

    public LinearLayout getBaseContentView() {
        return mContentView;
    }

    protected void onMenuItemClick(MenuItem item) {
    }

    protected void onTitleClick(View view) {
    }

    @Override
    public void onNavigationClick(View view) {
        finish();
    }

    @Override
    public void onTitleOnClick(View view) {
        if (null != onTitleOnClickListener) {
            onTitleOnClickListener.onTitleOnClick(view);
        }
        BaseActivity.this.onTitleClick(view);
    }

    public void showToastCenter(String msg) {
        //提示已发送
        ToastUtils.setBgResource(R.drawable.shape_toast_bg);
        ToastUtils.setMsgColor(getResources().getColor(R.color.white));
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        ToastUtils.showShort(msg);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void setOnTitleOnClickListener(AbsToolbar.OnTitleOnClickListener onTitleOnClickListener) {
        this.onTitleOnClickListener = onTitleOnClickListener;
    }

    public void setOnMenuItemClickListener(final Toolbar.OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    public interface OnBackPressedListener {
        boolean onBackPressed();
    }

    @Override
    public void doResponseError(int code, String message) {
        if (AppConstant.NET_RESPONSE_CODE.get(code) != null) {
//            if (code == AppConstant.CODE_NO_LOGIN) {
//                NavigationUtil.overlay(this, Login1Activity.class);
//            } else if (code == AppConstant.CODE_NO_BIND) {
////                NavigationUtil.overlay(this, BindingActivity.class);
//            } else if (code == AppConstant.CODE_GO_SET_PWD) {
//            } else if (code == AppConstant.CODE_BIND_FAIL) {
////                NavigationUtil.overlay(this, BindingActivity.class);
//            } else if (code == AppConstant.CODE_LOGIN_OVERTIME) {
//                NavigationUtil.overlay(this, Login1Activity.class);
//            } else if (code == AppConstant.CODE_LOGIN) {
////                NavigationUtil.overlay(this, Login1Activity.class);
//            } else if (code == AppConstant.CODE_TOKEN_NO_EXIST) {
//                NavigationUtil.overlay(this, Login1Activity.class);
//            } else if (code == AppConstant.CODE_TOKEN_OVERTIME) {
//                NavigationUtil.overlay(this, Login1Activity.class);
//            } else if (code == AppConstant.CODE_TOKEN_INFO_UNUSUAL) {
////                NavigationUtil.overlay(this, Login1Activity.class);
//            } else if (code == AppConstant.CODE_ACCOUNT_NO_EXIST) {
//                NavigationUtil.overlay(this, Login1Activity.class);
//            }
        } else {
            if (code != HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && onNeedCallSuper()) {
                if (TextUtils.isEmpty(message)) {
                    message = getString(R.string.server_busy_retry_later);
                }
                ToastUtils.showShort(message);
            }
        }
        dissmissLoadingDialog();
    }

    /**
     * 如果不需要使用父类提示,请重写该方法
     */
    protected boolean onNeedCallSuper() {
        return true;
    }

    /**
     * 显示加载页面
     *
     * @return 当前的对话框对象
     */
    public void showLoadingDialog() {
        showLoadingDialog(getString(R.string.loading));
    }

    /**
     * 显示加载页面
     *
     * @param loadingText 加载提示文字
     * @return 当前的对话框对象
     */
    public void showLoadingDialog(String loadingText) {
        if (mDialog == null) {
            mDialog = new LoadingDialog(this);
        }
        mDialog.setTitle(loadingText);
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
        mDialog.show();
    }

    public void dissmissLoadingDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (mTakePhoto == null) {
            mTakePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
//            mTakePhoto.setTakePhotoOptions(new TakePhotoOptions.Builder().setWithOwnGallery(false).create());
        }
        return mTakePhoto;
    }

    @Override
    public void takeSuccess(TResult result) {

    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.mInvokeParam = invokeParam;
        }
        return type;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public boolean checkLogin() {
        if (!SessionUtil.getInstance().isLogined()) {
            DialogUtil.getConfirmDialog(this, "提示", "需要登录后，才能继续以下操作，是否现在登录？", "登录", "取消", false, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    NavigationUtil.overlay(BaseActivity.this, LoginActivity.class);
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
            return false;
        }
        return true;
    }
}
