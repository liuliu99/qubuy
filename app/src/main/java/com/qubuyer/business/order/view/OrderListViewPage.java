package com.qubuyer.business.order.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.base.mvp.BaseRefreshView;
import com.qubuyer.bean.event.DelOrderEvent;
import com.qubuyer.bean.event.SOSelectInvoiceEvent;
import com.qubuyer.bean.mine.MineInvoiceEntitiy;
import com.qubuyer.bean.order.OrderEntity;
import com.qubuyer.bean.order.OrderRefundReasonEntity;
import com.qubuyer.business.good.activity.GoodInvoiceActivity;
import com.qubuyer.business.order.adapter.OrderListAdapter;
import com.qubuyer.business.order.presenter.OrderListPresenter;
import com.qubuyer.customview.ImageViewAutoLoad;
import com.qubuyer.utils.DialogUtil;
import com.qubuyer.utils.NavigationUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * @author Susong
 * @date 创建时间:2019/2/21
 * @description 订单列表ViewPage
 * & @version
 */
public class OrderListViewPage implements BaseRefreshView, IOrderListPageView {
    //当前上下文
    private Context mContext;
    private View mView;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private ImageViewAutoLoad mScrollTop;
    private LinearLayout rlNoData;
    private OrderListPresenter mPresenter;
    private OrderListAdapter mAdapter;
    private int mOverallXScroll = 0;
    private int mHeight;
    //订单状态
    private String mOrderStatus;
    private CancelReasonDialog cancelReasonDialog;
    private OrderEntity mOperationOrderEntity;

    public OrderListViewPage(Context mContext, String orderStatus) {
        this.mContext = mContext;
        this.mOrderStatus = orderStatus;
        this.mHeight = ConvertUtils.dp2px(145);
        createP();
    }

    protected void createP() {
        mPresenter = new OrderListPresenter();
        mPresenter.attachView(this);
    }

    public View getView() {
        if (mView == null) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.layout_fragment_order_list_page, null);
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
            mRefreshLayout = mView.findViewById(R.id.srl_refresh);
            mRecyclerView = mView.findViewById(R.id.rv_list);
            rlNoData = mView.findViewById(R.id.ll_no_data);
            mScrollTop = mView.findViewById(R.id.iv_scroll_top);
            mScrollTop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOverallXScroll = 0;
                    mRecyclerView.scrollToPosition(0);
                }
            });
            initPullToRefresh();
            initRecyclerView();
        }
        return mView;
    }

    private void initRecyclerView() {
        mAdapter = new OrderListAdapter(mContext, mOrderStatus, new OrderListAdapter.OrderListOperationListener() {
            @Override
            public void onCancelOrderClick(OrderEntity entity) {
                mOperationOrderEntity = entity;
                if (entity.getIs_pay() == 1) {
                    mPresenter.cancelOrder(entity.getOrder_id() + "", "");
                } else {
                    mPresenter.getCancelReasonList();
                }
            }

            @Override
            public void onConfirmGoodClick(String orderId) {
                mPresenter.confirmGood(orderId);
            }

            @Override
            public void extendReceiving(String orderId) {
                mPresenter.extendReceiving(orderId);
            }

            @Override
            public void applyInvoice(OrderEntity entity) {
                mOperationOrderEntity = entity;
                NavigationUtil.overlay(mContext, GoodInvoiceActivity.class);
            }

            @Override
            public void onDelOrderClick(OrderEntity entity) {
                mPresenter.delOrder(String.valueOf(entity.getOrder_id()));
            }
        });
//        mRecyclerView.addItemDecoration(new DecorationLLM(mContext, DecorationLLM.VERTICAL_LIST, R.drawable.shape_recyclerview_divider, DensityUtil.dip2px(mContext, 10)));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mOverallXScroll += dy;
                if (mOverallXScroll <= 0) {
                    mScrollTop.setVisibility(View.GONE);
                } else if (mOverallXScroll > mHeight) {
                    mScrollTop.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initPullToRefresh() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.resetNoMoreData();
                mPresenter.refresh(mOrderStatus);
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.loadMore(mOrderStatus);
            }
        });
        mRefreshLayout.setEnableScrollContentWhenLoaded(true);
    }

    @Override
    public void finishLoadMore(int delay, boolean success, boolean noMoreData) {
        mRefreshLayout.finishLoadMore(delay, success, noMoreData);
    }

    @Override
    public void finishRefresh(boolean success) {
        mRefreshLayout.finishRefresh(success);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void doResponseError(int code, String message) {
        ((BaseActivity) mContext).doResponseError(code, message);
    }

    @Override
    public void onShowRefreshListToView(List<OrderEntity> list) {
//        list = null;
        List<OrderEntity> orderEntityList = new ArrayList<>();
        for (OrderEntity entity : list) {
            if (null != entity.getOrder_goods() && !entity.getOrder_goods().isEmpty()) {
                orderEntityList.add(entity);
            } else if (null != entity.getSplit() && !entity.getSplit().isEmpty()) {
                OrderEntity orderEntity;
                for (OrderEntity.SplitBean splitBean : entity.getSplit()) {
                    orderEntity = new OrderEntity();
                    orderEntity.setOrder_id(entity.getOrder_id());
                    orderEntity.setInvoice_type(entity.getInvoice_type());
                    orderEntity.setInvoice_title(entity.getInvoice_title());
                    orderEntity.setInvoice_name(entity.getInvoice_name());
                    orderEntity.setInvoice_mobile(entity.getInvoice_mobile());
                    orderEntity.setInvoice_email(entity.getInvoice_email());
                    orderEntity.setInvoice_taxpayer(entity.getInvoice_taxpayer());
                    orderEntity.setInvoice_desc(entity.getInvoice_desc());

                    orderEntity.setId(splitBean.getId());
                    orderEntity.setOrder_sn(splitBean.getOrder_sn());
                    orderEntity.setGoods_price(splitBean.getGoods_price());
                    orderEntity.setShipping_price(splitBean.getShipping_price());
                    orderEntity.setOrder_amount(splitBean.getOrder_amount());
                    orderEntity.setTotal_amount(splitBean.getTotal_amount());
                    orderEntity.setAdd_time(splitBean.getAdd_time());
                    orderEntity.setShipping_time(splitBean.getShipping_time());
                    orderEntity.setConfirm_time(splitBean.getConfirm_time());
                    orderEntity.setCancel_reason(splitBean.getCancel_reason());
                    orderEntity.setIs_pay(splitBean.getIs_pay());
                    orderEntity.setIs_receiving(splitBean.getIs_receiving());
                    orderEntity.setIs_comment(splitBean.getIs_comment());
                    orderEntity.setIs_return(splitBean.getIs_return());
                    orderEntity.setIs_complete(splitBean.getIs_complete());
                    orderEntity.setState_brief(splitBean.getState_brief());
                    orderEntity.setIs_delete(splitBean.getIs_delete());
                    orderEntity.setIs_extend_time(splitBean.getIs_extend_time());
                    orderEntity.setIs_invoice(splitBean.getIs_invoice());
                    orderEntity.setOrder_goods(splitBean.getOrder_goods());
                    orderEntityList.add(orderEntity);
                }
            }
        }
        mAdapter.setData(orderEntityList);
        if (orderEntityList.isEmpty()) {
            rlNoData.setVisibility(View.VISIBLE);
            mScrollTop.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            rlNoData.setVisibility(View.GONE);
            mScrollTop.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onShowLoadMoreListToView(List<OrderEntity> list) {
        List<OrderEntity> orderEntityList = new ArrayList<>();
        for (OrderEntity entity : list) {
            if (null != entity.getOrder_goods() && !entity.getOrder_goods().isEmpty()) {
                orderEntityList.add(entity);
            } else if (null != entity.getSplit() && !entity.getSplit().isEmpty()) {
                OrderEntity orderEntity;
                for (OrderEntity.SplitBean splitBean : entity.getSplit()) {
                    orderEntity = new OrderEntity();
                    orderEntity.setOrder_id(entity.getOrder_id());
                    orderEntity.setInvoice_type(entity.getInvoice_type());
                    orderEntity.setInvoice_title(entity.getInvoice_title());
                    orderEntity.setInvoice_name(entity.getInvoice_name());
                    orderEntity.setInvoice_mobile(entity.getInvoice_mobile());
                    orderEntity.setInvoice_email(entity.getInvoice_email());
                    orderEntity.setInvoice_taxpayer(entity.getInvoice_taxpayer());
                    orderEntity.setInvoice_desc(entity.getInvoice_desc());

                    orderEntity.setId(splitBean.getId());
                    orderEntity.setOrder_sn(splitBean.getOrder_sn());
                    orderEntity.setGoods_price(splitBean.getGoods_price());
                    orderEntity.setShipping_price(splitBean.getShipping_price());
                    orderEntity.setOrder_amount(splitBean.getOrder_amount());
                    orderEntity.setTotal_amount(splitBean.getTotal_amount());
                    orderEntity.setAdd_time(splitBean.getAdd_time());
                    orderEntity.setShipping_time(splitBean.getShipping_time());
                    orderEntity.setConfirm_time(splitBean.getConfirm_time());
                    orderEntity.setCancel_reason(splitBean.getCancel_reason());
                    orderEntity.setIs_pay(splitBean.getIs_pay());
                    orderEntity.setIs_receiving(splitBean.getIs_receiving());
                    orderEntity.setIs_comment(splitBean.getIs_comment());
                    orderEntity.setIs_return(splitBean.getIs_return());
                    orderEntity.setIs_complete(splitBean.getIs_complete());
                    orderEntity.setState_brief(splitBean.getState_brief());
                    orderEntity.setIs_delete(splitBean.getIs_delete());
                    orderEntity.setIs_extend_time(splitBean.getIs_extend_time());
                    orderEntity.setIs_invoice(splitBean.getIs_invoice());
                    orderEntity.setOrder_goods(splitBean.getOrder_goods());
                    orderEntityList.add(orderEntity);
                }
            }
        }
        mAdapter.addAll(orderEntityList);
    }

    @Override
    public void onShowCancelOrderResultToView(boolean result) {
        if (result) {
            ToastUtils.showShort("取消订单成功");
            if (null != mRefreshLayout) {
                mRefreshLayout.autoRefresh();
            }
        }
    }

    @Override
    public void onShowDelOrderResultToView(boolean result) {
        if (result) {
            ToastUtils.showShort("删除订单成功");
            if (null != mRefreshLayout) {
                mRefreshLayout.autoRefresh();
            }
        }
    }

    @Override
    public void onShowCancelReasonListToView(List<OrderRefundReasonEntity> list) {
        if (null == list || list.isEmpty()) {
            ToastUtils.showShort("取消原因数据异常");
            return;
        }
        if (null == cancelReasonDialog) {
            cancelReasonDialog = new CancelReasonDialog(mContext, new CancelReasonDialog.SelectRefunReasonListener() {
                @Override
                public void onRefundReasonClickListener(OrderRefundReasonEntity entity1) {
                    mPresenter.cancelOrder(mOperationOrderEntity.getOrder_id() + "", entity1.getId() + "");
                }
            });
            cancelReasonDialog.setData(list);
        }
        if (!cancelReasonDialog.isShowing()) {
            cancelReasonDialog.show();
            DialogUtil.setDialogWindowAttr(cancelReasonDialog, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM, R.style.shopping_cart_anim);
        }
    }

    @Override
    public void onShowConfirmGoodResultToView(boolean result) {
        if (result) {
            ToastUtils.showShort("确认收货成功");
            if (null != mRefreshLayout) {
                mRefreshLayout.autoRefresh();
            }
        }
    }

    @Override
    public void onShowExtendReceivingResultToView(boolean result) {
        if (result) {
            ToastUtils.showShort("延长收货成功");
            if (null != mRefreshLayout) {
                mRefreshLayout.autoRefresh();
            }
        }
    }

    @Override
    public void onShowApplyInvoiceResultToView(boolean result) {
        if (result) {
            ToastUtils.showShort("申请开票成功");
            if (null != mRefreshLayout) {
                mRefreshLayout.autoRefresh();
            }
        }
    }

    @Override
    public void destory() {
        if (null != mPresenter) {
            mPresenter.detachView();
            mPresenter.destoryModel();
            mPresenter = null;
        }
        if (null != mAdapter) {
            mAdapter.cancelAllTimers();
        }
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void loadData() {
        if (null != mRefreshLayout && null != mAdapter && mAdapter.getData().isEmpty()) {
            mRefreshLayout.autoRefresh();
        }
    }

    public void refreshData() {
        if (null != mRefreshLayout) {
            mRefreshLayout.autoRefresh();
        }
    }

    /**
     * EventBus事件分发方法
     */
    @Subscribe
    public void onEventMainThread(SOSelectInvoiceEvent event) {
        if (null != event) {
            MineInvoiceEntitiy entity = event.getEntitiy();
            if (null == entity || entity.getIsUse() == 0 || null == mOperationOrderEntity) return;
            mPresenter.applyInvoice(mOperationOrderEntity.getOrder_id() + "",
                    0 != mOperationOrderEntity.getId() ? mOperationOrderEntity.getId() + "" : "",
                    entity.getCompanyDutyparagraph(),
                    entity.getPhone(),
                    entity.getEmail(),
                    entity.getType() + "",
                    entity.getCompanyName(),
                    entity.getContent(),
                    "1");
        }
    }

    /**
     * EventBus事件分发方法
     */
    @Subscribe
    public void onEventMainThread(DelOrderEvent event) {
        refreshData();
    }
}


