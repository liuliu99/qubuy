package com.qubuyer.business.order.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.order.OrderLogisticsEntity;
import com.qubuyer.business.order.adapter.OrderLogisticsListAdapter;
import com.qubuyer.business.order.presenter.OrderLogisticsPresenter;
import com.qubuyer.business.order.view.IOrderLogisticsView;
import com.qubuyer.constant.AppConstant;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;


/**
 * @author Susong
 * @date 创建时间:2019/4/15
 * @description 订单物流信息activity
 * & @version
 */
public class OrderLogisticsActivity extends BaseActivity<OrderLogisticsPresenter> implements IOrderLogisticsView {
    @BindView(R.id.tv_order_no)
    TextView tv_order_no;
    @BindView(R.id.tv_order_status)
    TextView tv_order_status;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    private OrderLogisticsListAdapter mAdapter;

    private int mOrderId;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_order_logistics;
    }

    @Override
    protected OrderLogisticsPresenter createP(Context context) {
        return new OrderLogisticsPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        if (null != getIntent() && null != getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY)) {
            mOrderId = (int) getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY);
        }
        setTitle("订单跟踪");
        initRecyclerView();
    }

    @Override
    protected void initData() {
        mPresenter.getLogisticsList(mOrderId + "");
    }

    private void initRecyclerView() {
        mAdapter = new OrderLogisticsListAdapter(this);
//        mRecyclerView.addItemDecoration(new DecorationLLM(mContext, DecorationLLM.VERTICAL_LIST, R.drawable.shape_recyclerview_divider, DensityUtil.dip2px(mContext, 10)));
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        rv_list.setAdapter(mAdapter);
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
    public void onShowLogisticsListToView(OrderLogisticsEntity entity) {
        if (null != entity) {
            tv_order_no.setText("订单编号：" + entity.getEBusinessID());
            if (null != entity.getTraces() && !entity.getTraces().isEmpty()) {
                tv_order_status.setText("订单状态：" + entity.getTraces().get(0).getRemark());
            }
            mAdapter.setData(entity.getTraces());
        }
    }
}
