package com.qubuyer.business.mine.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.base.mvp.BaseRefreshView;
import com.qubuyer.bean.mine.RebateOrderEntity;
import com.qubuyer.bean.mine.SaleAmountEntity;
import com.qubuyer.business.mine.adapter.RebateOrderAdapter;
import com.qubuyer.business.mine.adapter.SaleAmountAdapter;
import com.qubuyer.business.mine.presenter.RebateOrderPresenter;
import com.qubuyer.business.mine.presenter.SaleAmountListPresenter;
import com.qubuyer.customview.DecorationLLM;
import com.qubuyer.utils.StringUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Susong
 * @date 创建时间2019/4/3
 * @description 折让订单viewpage
 */
public class RebateOrderViewPage implements BaseRefreshView, IRebateOrderPageView {
    //当前上下文
    private Context mContext;
    private View mView;
    private SmartRefreshLayout srl_refresh;
    private RecyclerView rv_list;
    private RebateOrderPresenter mPresenter;
    private RebateOrderAdapter mAdapter;
    //折让订单状态 1:已结算 2:待结算
    private int mType;

    public RebateOrderViewPage(Context mContext, int type) {
        this.mContext = mContext;
        this.mType = type;
        createP();
    }

    protected void createP() {
        mPresenter = new RebateOrderPresenter();
        mPresenter.attachView(this);
    }

    public View getView() {
        if (mView == null) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.layout_fragment_rebate_order_page, null);
            srl_refresh = mView.findViewById(R.id.srl_refresh);
            rv_list = mView.findViewById(R.id.rv_list);
            initPullToRefresh();
            initRecyclerView();
        }
        return mView;
    }

    private void initRecyclerView() {
        mAdapter = new RebateOrderAdapter(mContext, mType);
        rv_list.addItemDecoration(new DecorationLLM(mContext, DecorationLLM.VERTICAL_LIST, R.drawable.shape_recyclerview_divider, ConvertUtils.dp2px(15)));
        rv_list.setLayoutManager(new LinearLayoutManager(mContext));
        rv_list.setAdapter(mAdapter);
    }

    private void initPullToRefresh() {
        srl_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.resetNoMoreData();
                mPresenter.refresh(mType);
            }
        });
        srl_refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.loadMore(mType);
            }
        });
        srl_refresh.setEnableScrollContentWhenLoaded(true);
    }

    @Override
    public void finishLoadMore(int delay, boolean success, boolean noMoreData) {
        srl_refresh.finishLoadMore(delay, success, noMoreData);
    }

    @Override
    public void finishRefresh(boolean success) {
        srl_refresh.finishRefresh(success);
    }

    @Override
    public void showLoading() {
        ((BaseActivity) mContext).showLoading();
    }

    @Override
    public void hideLoading() {
        ((BaseActivity) mContext).hideLoading();
    }

    @Override
    public void doResponseError(int code, String message) {
        ((BaseActivity) mContext).doResponseError(code, message);
    }

    private SpannableStringBuilder changGoodTotalPriceText(String value) {
        SpannableStringBuilder sp1 = new SpannableStringBuilder();
        sp1.append(value);

        sp1.setSpan(new AbsoluteSizeSpan(ConvertUtils.dp2px(13)), 0, value.indexOf(":") + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp1.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 0, value.indexOf(":") + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        sp1.setSpan(new AbsoluteSizeSpan(ConvertUtils.dp2px(16)), value.indexOf(":") + 1, value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp1.setSpan(new ForegroundColorSpan(Color.parseColor("#FF681D")), value.indexOf(":") + 1, value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp1;
    }

    @Override
    public void onShowRefreshListToView(List<RebateOrderEntity> list) {
        mAdapter.setData(list);
    }

    @Override
    public void onShowLoadMoreListToView(List<RebateOrderEntity> list) {
        mAdapter.addAll(list);
    }

    @Override
    public void destory() {
        if (null != mPresenter) {
            mPresenter.detachView();
            mPresenter.destoryModel();
            mPresenter = null;
        }
    }

    public void loadData() {
        if (null != srl_refresh && null != mAdapter && mAdapter.getData().isEmpty()) {
            srl_refresh.autoRefresh();
        }
    }

    public void autoRefreshData(){
        if (null != srl_refresh && null != mAdapter) {
            srl_refresh.autoRefresh();
        }
    }
}


