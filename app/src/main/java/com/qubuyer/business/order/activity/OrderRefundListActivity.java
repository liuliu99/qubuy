package com.qubuyer.business.order.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.order.OrderRefundEntity;
import com.qubuyer.business.order.adapter.OrderRefundListAdapter;
import com.qubuyer.business.order.presenter.OrderRefundListPresenter;
import com.qubuyer.business.order.view.IOrderRefundListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;


/**
 * @author Susong
 * @date 创建时间2019/4/1
 * @description 退款列表activity
 */
public class OrderRefundListActivity extends BaseActivity<OrderRefundListPresenter> implements IOrderRefundListView {
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srl_refresh;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.ll_no_data)
    LinearLayout ll_no_data;

    private OrderRefundListAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_order_refund_list;
    }

    @Override
    protected OrderRefundListPresenter createP(Context context) {
        return new OrderRefundListPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setTitle("退款/售后");
        initPullToRefresh();
        initRecyclerView();
    }

    @Override
    protected void initData() {
        srl_refresh.autoRefresh();
    }

    private void initPullToRefresh() {
        srl_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.resetNoMoreData();
                mPresenter.refresh();
            }
        });
        srl_refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.loadMore();
            }
        });
        srl_refresh.setEnableScrollContentWhenLoaded(true);
    }

    private void initRecyclerView() {
        mAdapter = new OrderRefundListAdapter(this);
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
    public void onShowRefreshListToView(List<OrderRefundEntity> list) {
        if (null != list && !list.isEmpty()) {
            rv_list.setVisibility(View.VISIBLE);
            ll_no_data.setVisibility(View.GONE);
            mAdapter.setData(list);
        } else {
            rv_list.setVisibility(View.GONE);
            ll_no_data.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onShowLoadMoreListToView(List<OrderRefundEntity> list) {
        mAdapter.addAll(list);
    }

    @Override
    public void onShowRefundDetailToView(OrderRefundEntity entity) {
    }

    @Override
    public void onShowCancelRefundResultToView(boolean result) {

    }

    @Override
    public void destory() {

    }

    @Override
    public void finishLoadMore(int delay, boolean success, boolean noMoreData) {
        srl_refresh.finishLoadMore(delay, success, noMoreData);
    }

    @Override
    public void finishRefresh(boolean success) {
        srl_refresh.finishRefresh(success);
    }
}
