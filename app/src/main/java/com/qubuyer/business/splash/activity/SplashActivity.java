package com.qubuyer.business.splash.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.CacheDiskUtils;
import com.blankj.utilcode.util.CacheDoubleUtils;
import com.blankj.utilcode.util.CacheMemoryUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.BuildConfig;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseFragmentActivity;
import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.business.home.view.PrivacyAgreementDialog;
import com.qubuyer.business.splash.fragment.SplashFragment;
import com.qubuyer.business.splash.fragment.WelcomeFragment;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.customview.AbsToolbar;
import com.qubuyer.utils.CrashUtil;
import com.qubuyer.utils.DialogUtil;
import com.qubuyer.utils.FileUtil;

import java.util.List;

import static com.blankj.utilcode.constant.PermissionConstants.CAMERA;
import static com.blankj.utilcode.constant.PermissionConstants.LOCATION;
import static com.blankj.utilcode.constant.PermissionConstants.PHONE;
import static com.blankj.utilcode.constant.PermissionConstants.STORAGE;

public class SplashActivity extends BaseFragmentActivity {
    private static final String SHOW_WEICOM = "showWelcome";
    private static final int RC_PERMISSION = 0x03;
    protected OnActivityDispatchTouchEventListener onActivityDispatchTouchEventListener;

    @Override
    public int containerId() {
        return R.id.rl_container;
    }

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_splash;
    }

    @Override
    protected WrapperPresenter createP(Context context) {
        return null;
    }

    @Override
    public View statusBar() {
        return null;
    }

    @Override
    public AbsToolbar toolbar() {
        return null;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        BarUtils.setStatusBarLightMode(this, true);
        requestPermissions();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    private void requestPermissions() {
        PermissionUtils.permission(CAMERA, STORAGE, LOCATION, PHONE).callback(new PermissionUtils.FullCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onGranted(List<String> permissionsGranted) {
                initLogger();
                CacheDoubleUtils.getInstance(CacheMemoryUtils.getInstance(), CacheDiskUtils.getInstance(FileUtil.getOwnCacheDirectory(SplashActivity.this, getPackageName() + "/cache")));
                CrashUtil.initCrashReport();
                CrashUtils.init();
                if (!showPrivacyAgreement()) return;
                choosePage();
            }

            @Override
            public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                StringBuilder stringBuilder = new StringBuilder("该功能可能需要您的");
                if (null != permissionsDeniedForever && !permissionsDeniedForever.isEmpty()) {
                    for (String temp : permissionsDeniedForever) {
                        switch (temp) {
                            case Manifest.permission.CAMERA:
                                if (stringBuilder.indexOf("相机") == -1) {
                                    stringBuilder.append("相机,");
                                }
                                break;
                            case Manifest.permission.READ_EXTERNAL_STORAGE:
                            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                                if (stringBuilder.indexOf("存储") == -1) {
                                    stringBuilder.append("存储,");
                                }
                                break;
                            case Manifest.permission.ACCESS_COARSE_LOCATION:
                            case Manifest.permission.ACCESS_FINE_LOCATION:
                                if (stringBuilder.indexOf("位置") == -1) {
                                    stringBuilder.append("位置,");
                                }
                                break;
                            case Manifest.permission.READ_PHONE_STATE:
                            case Manifest.permission.CALL_PHONE:
                                if (stringBuilder.indexOf("电话") == -1) {
                                    stringBuilder.append("电话,");
                                }
                                break;
                        }
                    }
                } else {
                    for (String temp : permissionsDenied) {
                        switch (temp) {
                            case Manifest.permission.CAMERA:
                                if (stringBuilder.indexOf("相机") == -1) {
                                    stringBuilder.append("相机,");
                                }
                                break;
                            case Manifest.permission.READ_EXTERNAL_STORAGE:
                            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                                if (stringBuilder.indexOf("存储") == -1) {
                                    stringBuilder.append("存储,");
                                }
                                break;
                            case Manifest.permission.ACCESS_COARSE_LOCATION:
                            case Manifest.permission.ACCESS_FINE_LOCATION:
                                if (stringBuilder.indexOf("位置") == -1) {
                                    stringBuilder.append("位置,");
                                }
                                break;
                            case Manifest.permission.READ_PHONE_STATE:
                            case Manifest.permission.CALL_PHONE:
                                if (stringBuilder.indexOf("电话") == -1) {
                                    stringBuilder.append("电话");
                                }
                                break;
                        }
                    }
                }
                stringBuilder.append("等权限, 请手动开启");
                DialogUtil.getConfirmDialog(SplashActivity.this, "", stringBuilder.toString(), "去设置", "取消", false, (dialog, which) -> {
                    PermissionUtils.launchAppDetailsSettings();
                    ActivityUtils.finishAllActivities();
                }, (dialog, which) -> finish()).show();
            }
        }).request();
    }

    private void choosePage() {
        boolean showWelcome = SPUtils.getInstance().getBoolean(SHOW_WEICOM, true);
        if (showWelcome) {
//            switchFragment(new WelcomeFragment(), R.id.activitySplashContainer, false, true);
            push(WelcomeFragment.class);
            SPUtils.getInstance().put(SHOW_WEICOM, false);
        } else {
//            push(SplashFragment.class);
            push(0, 0, SplashFragment.class, null);
//            switchFragment(new SplashFragment(), R.id.activitySplashContainer, false, true);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (onActivityDispatchTouchEventListener != null) {
            boolean b = onActivityDispatchTouchEventListener.onActivityDispatchTouchEvent(ev);
            if (b) {
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 设置触摸事件分发监听器
     */
    public void setOnActivityDispatchTouchEventListener(OnActivityDispatchTouchEventListener onActivityDispatchTouchEventListener) {
        this.onActivityDispatchTouchEventListener = onActivityDispatchTouchEventListener;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    /**
     * 触摸事件分发接口
     */
    public interface OnActivityDispatchTouchEventListener {
        boolean onActivityDispatchTouchEvent(MotionEvent ev);
    }

    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)      //（可选）是否显示线程信息。 默认值为true
                .methodCount(2)               // （可选）要显示的方法行数。 默认2
                .methodOffset(7)               // （可选）设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息，默认是0
//                .logStrategy(customLog)  //（可选）更改要打印的日志策略。 默认LogCat
                .tag("QTrip")                  //（可选）每个日志的全局标记。 默认PRETTY_LOGGER（如上图）
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

    private boolean showPrivacyAgreement(){
//        if (System.currentTimeMillis() > TimeUtils.string2Millis("2020-01-15 00:00:00")) {
//            ActivityUtils.finishAllActivities();
//            android.os.Process.killProcess(android.os.Process.myPid());
//            System.exit(1);
//            return false;
//        }
        boolean isShowPrivacyAgreement = SPUtils.getInstance().getBoolean(AppConstant.SP_IS_FIRST_SHOW_PRIVACY_AGREEMENT, false);
        if (!isShowPrivacyAgreement) {
            PrivacyAgreementDialog privacyAgreementDialog = new PrivacyAgreementDialog(this, new PrivacyAgreementDialog.PrivacyAgreementListener() {
                @Override
                public void onIsAgree(boolean isAgree) {
                    if (!isAgree) return;
                    SPUtils.getInstance().put(AppConstant.SP_IS_FIRST_SHOW_PRIVACY_AGREEMENT, true);
                    choosePage();
                }
            });
            privacyAgreementDialog.show();
            DialogUtil.setDialogWindowAttr(privacyAgreementDialog, ScreenUtils.getScreenWidth() - ConvertUtils.dp2px(60), WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER, R.style.shopping_cart_anim);
        }
        return isShowPrivacyAgreement;
    }
}
