package com.qubuyer.business.order.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.TimeMode;
import com.qubuyer.bean.good.GoodSOResultEntity;
import com.qubuyer.bean.order.OrderEntity;
import com.qubuyer.bean.payment.PayListEntity;
import com.qubuyer.business.payment.view.SOPayListView;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.utils.StringUtil;
import com.qubuyer.utils.TimeUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Susong
 * @date 创建时间:2019/2/26
 * @description 订单立即付款页
 * & @version
 */
public class OrderNowPayActivity extends BaseActivity {

    @BindView(R.id.v_pay_list)
    SOPayListView v_pay_list;

    @BindView(R.id.tv_pay_price)
    TextView tv_pay_price;
    @BindView(R.id.tv_over_time)
    TextView tv_over_time;
    @BindView(R.id.tv_now_pay)
    TextView tv_now_pay;

    private OrderEntity mOrderEntity;
    private GoodSOResultEntity mSOEntity;

    private CountDownTimer countDownTimer;

    //订单号
    private String mOrderPayId;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_order_now_pay;
    }

    @Override
    protected WrapperPresenter createP(Context context) {
        return null;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        if (null != getIntent() && null != getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY)) {
            HashMap map = (HashMap) getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY);
            mOrderEntity = (OrderEntity) map.get("order_entity");
            mSOEntity = (GoodSOResultEntity) map.get("order_so_entity");
        }
        if (null != mOrderEntity) {
            mOrderPayId = mOrderEntity.getOrder_id() + "";
        }
        if (null != mSOEntity) {
            mOrderPayId = mSOEntity.getOrder_id() + "";
        }
        setTitle("请选择支付方式");
        if (null != mOrderEntity) {
            tv_pay_price.setText("实付款：¥" + StringUtil.formatAmount(mOrderEntity.getOrder_amount(), 2));
            countDownTimer = new CountDownTimer(mOrderEntity.getTime_out() * 1000 - System.currentTimeMillis(), 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    TimeMode timeMode = TimeUtil.parseRemainTime(millisUntilFinished);
                    if (timeMode.getDay() > 0) {
                        timeMode.setHour(timeMode.getHour() + timeMode.getDay() * 24);
                    }
                    tv_over_time.setText("距订单关闭还有" + timeMode.getHour() + "小时" + timeMode.getMinute() + "分钟");
                }

                @Override
                public void onFinish() {
                    tv_over_time.setText("订单已关闭");
                }
            }.start();
        } else if (null != mSOEntity) {
            tv_pay_price.setText("实付款：¥" + StringUtil.formatAmount(mSOEntity.getOrder_amount(), 2));
        }
        initPayListView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(mOrderPayId)) {
            v_pay_list.setOrderPayId(mOrderPayId);
            v_pay_list.loadResult();
        }
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

    @OnClick({R.id.tv_now_pay})
    public void onClickByButterKnfie(View v) {
        switch (v.getId()) {
            case R.id.tv_now_pay:
                if (null == mOrderEntity && null == mSOEntity) {
                    ToastUtils.showShort("订单数据异常");
                    return;
                }
                v_pay_list.setOrderPayId(mOrderPayId);
                v_pay_list.pay();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != countDownTimer) {
            countDownTimer.cancel();
        }
    }

    private void initPayListView() {
        v_pay_list.setListener(new SOPayListView.OnSOPayListListener() {
            @Override
            public void onSOPayListClickListener(PayListEntity entity) {

            }
        });
    }
}
