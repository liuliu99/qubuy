package com.qubuyer.business.order.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.order.OrderRefundEntity;
import com.qubuyer.business.order.presenter.OrderRefundListPresenter;
import com.qubuyer.business.order.view.IOrderRefundListView;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.customview.ImageViewAutoLoad;
import com.qubuyer.utils.StringUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author Susong
 * @date 创建时间:2019/4/1
 * @description 退款详情activity
 * & @version
 */
public class OrderRefundDetailActivity extends BaseActivity<OrderRefundListPresenter> implements IOrderRefundListView {
    @BindView(R.id.tv_refund_status)
    TextView tv_refund_status;
    @BindView(R.id.tv_refund_status_desc)
    TextView tv_refund_status_desc;
    @BindView(R.id.tv_refund_status_note)
    TextView tv_refund_status_note;
    @BindView(R.id.tv_cancel_refund)
    TextView tv_cancel_refund;
    @BindView(R.id.iv_good_img)
    ImageViewAutoLoad iv_good_img;
    @BindView(R.id.tv_good_name)
    TextView tv_good_name;
    @BindView(R.id.tv_good_spec)
    TextView tv_good_spec;
    @BindView(R.id.tv_refund_reason)
    TextView tv_refund_reason;
    @BindView(R.id.tv_refund_money)
    TextView tv_refund_money;
    @BindView(R.id.tv_refund_create_time)
    TextView tv_refund_create_time;
    @BindView(R.id.tv_refund_no)
    TextView tv_refund_no;

    private OrderRefundEntity mEntity;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_order_refund_detail;
    }

    @Override
    protected OrderRefundListPresenter createP(Context context) {
        return new OrderRefundListPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setTitle("退款详情");
        if (null != getIntent() && null != getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY)) {
            mEntity = (OrderRefundEntity) getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY);
        }
    }

    @Override
    protected void initData() {
        mPresenter.getRefundDetail(mEntity.getId() + "");
    }

    @OnClick({R.id.tv_cancel_refund})
    public void onClickWithButterKnfie(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel_refund: //撤销申请
                mPresenter.cancelRefund(mEntity.getId() + "");
                break;
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

    @Override
    public void onShowRefreshListToView(List<OrderRefundEntity> list) {

    }

    @Override
    public void onShowLoadMoreListToView(List<OrderRefundEntity> list) {

    }

    @Override
    public void onShowRefundDetailToView(OrderRefundEntity entity) {
        if (null != entity) {
            if (null != entity.getStatus_desc()) {
                tv_refund_status.setText(entity.getStatus_desc().getTitle());
                tv_refund_status_desc.setText(entity.getStatus_desc().getDesc());
                tv_refund_status_note.setText(entity.getStatus_desc().getNote());
            }
            if (entity.getStatus() == 0) {
                tv_cancel_refund.setVisibility(View.VISIBLE);
            } else {
                tv_cancel_refund.setVisibility(View.GONE);
            }

            iv_good_img.setUri(this, entity.getOriginal_img_full());
            tv_good_name.setText(entity.getGoods_name());
            tv_good_spec.setText(entity.getSpec_key_name());

            tv_refund_reason.setText("退款原因：" + entity.getDescribe());
            tv_refund_money.setText("退款金额：" + StringUtil.formatAmount(entity.getRefund_money(), 2));
            tv_refund_create_time.setText("申请时间：" + entity.getAdd_time_format());
            tv_refund_no.setText("订单编号：" + entity.getOrder_sn());
        }
    }

    @Override
    public void onShowCancelRefundResultToView(boolean result) {
        if (result) {
            ToastUtils.showShort("撤销成功");
            finish();
        }
    }

    @Override
    public void destory() {

    }

    @Override
    public void finishLoadMore(int delay, boolean success, boolean noMoreData) {

    }

    @Override
    public void finishRefresh(boolean success) {

    }
}
