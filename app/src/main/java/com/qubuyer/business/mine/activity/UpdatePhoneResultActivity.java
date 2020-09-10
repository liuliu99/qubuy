package com.qubuyer.business.mine.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.base.mvp.WrapperPresenter;


/**
 * @author Susong
 * @date 创建时间2019/4/12
 * @description 修改手机号结果页activity
 */
public class UpdatePhoneResultActivity extends BaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_update_phone_result;
    }

    @Override
    protected WrapperPresenter createP(Context context) {
        return null;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setTitle("更换成功");
    }

    @Override
    public void onNavigationClick(View view) {
        ActivityUtils.finishToActivity(SettingActivity.class, false);
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
}
