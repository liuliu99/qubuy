package com.qubuyer.business.order.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.order.InvoiceInfo;
import com.qubuyer.bean.order.OrderEntity;
import com.qubuyer.business.order.presenter.OrderDetailPresenter;
import com.qubuyer.constant.AppConstant;

import java.text.SimpleDateFormat;

import butterknife.BindView;

/**
 * @author Susong
 * @date 创建时间2019/3/31
 * @description 发票详情
 */
public class OrderInvoiceDetailActivity extends BaseActivity {
    @BindView(R.id.tv_order_no)
    TextView tv_order_no;
    @BindView(R.id.tv_order_create_time)
    TextView tv_order_create_time;
    @BindView(R.id.tv_order_ivoice_title)
    TextView tv_order_ivoice_title;
    @BindView(R.id.tv_order_ivoice_taxpayer)
    TextView tv_order_ivoice_taxpayer;
    @BindView(R.id.tv_order_ivoice_type)
    TextView tv_order_ivoice_type;
    @BindView(R.id.tv_order_ivoice_content)
    TextView tv_order_ivoice_content;
    @BindView(R.id.tv_ivoice_status)
    TextView tv_ivoice_status;

    private OrderEntity mOrderEntity;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_order_invoice_detail;
    }

    @Override
    protected OrderDetailPresenter createP(Context context) {
        return new OrderDetailPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setTitle("发票详情");
        if (null != getIntent() && null != getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY)) {
            mOrderEntity = (OrderEntity) getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY);
        }
        if (null != mOrderEntity) {
            InvoiceInfo invoiceInfo = mOrderEntity.getInvoice_info();
            if (ObjectUtils.isNotEmpty(invoiceInfo)) {
                switch (invoiceInfo.getStatus()) {
                    case "0": //待审核
                        tv_ivoice_status.setText("待审核");
                        break;
                    case "1": //审核通过
                        tv_ivoice_status.setText("审核通过");
                        break;
                    case "-1": //审核不通过
                        tv_ivoice_status.setText("审核不通过");
                        break;
                }

                tv_order_ivoice_title.setText("发票抬头: " + (invoiceInfo.getInvoice_title().equalsIgnoreCase("1") ? "个人" : ObjectUtils.isNotEmpty(invoiceInfo.getInvoice_name()) ? invoiceInfo.getInvoice_name() : ""));
                if (!TextUtils.isEmpty(invoiceInfo.getInvoice_taxpayer())) {
                    tv_order_ivoice_taxpayer.setVisibility(View.VISIBLE);
                    tv_order_ivoice_taxpayer.setText("发票税号: " + invoiceInfo.getInvoice_taxpayer());
                } else {
                    tv_order_ivoice_taxpayer.setVisibility(View.GONE);
                }
                tv_order_ivoice_content.setText("发票内容: " + invoiceInfo.getInvoice_desc());
            } else {
                tv_order_ivoice_title.setText("发票抬头: " + (mOrderEntity.getInvoice_title() == 1 ? "个人" : "单位"));
                if (!TextUtils.isEmpty(mOrderEntity.getInvoice_taxpayer())) {
                    tv_order_ivoice_taxpayer.setVisibility(View.VISIBLE);
                    tv_order_ivoice_taxpayer.setText("发票税号: " + mOrderEntity.getInvoice_taxpayer());
                } else {
                    tv_order_ivoice_taxpayer.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(mOrderEntity.getInvoice_desc())) {
                    tv_order_ivoice_content.setVisibility(View.VISIBLE);
                    tv_order_ivoice_content.setText("发票内容: " + mOrderEntity.getInvoice_desc());
                } else {
                    tv_order_ivoice_content.setVisibility(View.GONE);
                }
            }
            tv_order_no.setText("订单编号: " + mOrderEntity.getOrder_sn());
            tv_order_create_time.setText("下单时间: " + TimeUtils.millis2String(mOrderEntity.getAdd_time() * 1000, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));

            tv_order_ivoice_type.setText("发票类型: 电子发票");
        }
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
