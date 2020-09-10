package com.qubuyer.business.home.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;

import com.blankj.utilcode.util.ToastUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.business.home.presenter.LimitBuyPresenter;
import com.qubuyer.business.register.activity.RegisterActivity;
import com.qubuyer.utils.NavigationUtil;

import butterknife.BindView;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;


/**
 * @author Susong
 * @date 创建时间2019/4/10
 * @description 扫一扫activity
 */
public class ScanActivity extends BaseActivity implements QRCodeView.Delegate {
    @BindView(R.id.zbarview)
    ZBarView zbarview;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_scan;
    }

    @Override
    protected LimitBuyPresenter createP(Context context) {
        return new LimitBuyPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setTitle("扫一扫");
        zbarview.setDelegate(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        zbarview.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
//        mZBarView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT); // 打开前置摄像头开始预览，但是并未开始识别
        zbarview.startSpotAndShowRect(); // 显示扫描框，并开始识别
    }

    @Override
    protected void onStop() {
        zbarview.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        zbarview.onDestroy(); // 销毁二维码扫描控件
        super.onDestroy();
    }

    @Override
    protected void initData() {
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        dissmissLoadingDialog();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
        if (!TextUtils.isEmpty(result)) {
            NavigationUtil.forward(this, RegisterActivity.class, result);
        }
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
        // 这里是通过修改提示文案来展示环境是否过暗的状态，接入方也可以根据 isDark 的值来实现其他交互效果
        String tipText = zbarview.getScanBoxView().getTipText();
        String ambientBrightnessTip = "\n环境过暗，请打开闪光灯";
        if (isDark) {
            if (!tipText.contains(ambientBrightnessTip)) {
                zbarview.getScanBoxView().setTipText(tipText + ambientBrightnessTip);
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip));
                zbarview.getScanBoxView().setTipText(tipText);
            }
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        ToastUtils.showShort("打开相机出错");
    }
}
