package com.qubuyer.business.mine.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.business.mine.presenter.WithdrawPresenter;

import butterknife.OnClick;


/**
 * @author Susong
 * @date 创建时间:2019/4/13
 * @description 提现activity
 * & @version
 */
public class WithdrawResultActivity extends BaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_withdraw_result;
    }

    @Override
    protected WithdrawPresenter createP(Context context) {
        return new WithdrawPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setTitle("提现成功");
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onNavigationClick(View view) {
        ActivityUtils.finishToActivity(MineWalletActivity.class, false);
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        dissmissLoadingDialog();
    }

    @OnClick({R.id.tv_confirm_withdraw})
    public void onClickWithButterKnife(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm_withdraw:
                ActivityUtils.finishToActivity(MineWalletActivity.class, false);
                break;
        }
    }
}
