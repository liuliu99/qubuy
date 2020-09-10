package com.qubuyer.business.payment.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.event.GoToMainEvent;
import com.qubuyer.bean.event.GoToMainOrderListPageEvent;
import com.qubuyer.bean.payment.PayResultEntity;
import com.qubuyer.business.good.activity.SOActivity;
import com.qubuyer.business.main.MainActivity;
import com.qubuyer.business.order.activity.OrderListActivity;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.utils.EventBusUtil;
import com.qubuyer.utils.NavigationUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Susong
 * @date 创建时间:2019/3/19
 * @description 支付结果activity
 * & @version
 */
public class PaymentResultActivity extends BaseActivity {
    @BindView(R.id.tv_payprice)
    TextView tv_payprice;
    @BindView(R.id.tv_operation_one)
    TextView tv_operation_one;
    @BindView(R.id.tv_operation_two)
    TextView tv_operation_two;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_pay_result;
    }

    @Override
    protected WrapperPresenter createP(Context context) {
        return null;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        boolean isSuccess = false;
        String resultString = null;
        PayResultEntity entity = null;
        if (null != getIntent() && null != getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY)) {
            HashMap map = (HashMap) getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY);
            isSuccess = (boolean) map.get("is_success");
            resultString = (String) map.get("result_string");
            entity = (PayResultEntity) map.get("result_entity");
        }
//        if (isSuccess) {
//            setTitle("支付成功");
//            iv_result.setDrawableId(this, R.drawable.icon_pay_result_success);
//            tv_pay_result.setText("订单支付成功");
//            tv_reason.setText("实付金额¥" + (null != entity ? StringUtil.formatAmount(entity.getPay_money(), 2) : "0.00") + ",详细信息请进入我的订单查看");
//        } else {
//            setTitle("支付失败");
//            iv_result.setDrawableId(this, R.drawable.icon_pay_result_fail);
//            tv_pay_result.setText("订单支付失败");
//            tv_reason.setText(resultString);
//        }

    }

    @OnClick({R.id.tv_operation_one, R.id.tv_operation_two})
    public void onClickWithButterKnfie(View v) {
        switch (v.getId()) {
            case R.id.tv_operation_one: //返回首页
                EventBusUtil.sendEvent(new GoToMainEvent());
                ActivityUtils.finishOtherActivities(MainActivity.class);
                break;
            case R.id.tv_operation_two: //查看订单
                ActivityUtils.finishOtherActivities(MainActivity.class);
                NavigationUtil.overlay(this, OrderListActivity.class);
                break;
        }
    }

    @Override
    public void onNavigationClick(View view) {
        ActivityUtils.finishActivity(SOActivity.class);
        finish();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
