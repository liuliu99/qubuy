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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ConvertUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.base.mvp.BaseRefreshView;
import com.qubuyer.bean.mine.SaleAmountEntity;
import com.qubuyer.business.mine.adapter.SaleAmountAdapter;
import com.qubuyer.business.mine.presenter.SaleAmountListPresenter;
import com.qubuyer.customview.DecorationLLM;
import com.qubuyer.utils.StringUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * @author Susong
 * @date 创建时间2019/4/3
 * @description 销售总额viewpage
 */
public class SaleAmountViewPage implements BaseRefreshView, ISaleAmountPageView {
    //当前上下文
    private Context mContext;
    private View mView;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private TextView tv_total_money;
    private SaleAmountListPresenter mPresenter;
    private SaleAmountAdapter mAdapter;
    //订单状态
    private int mType;

    public SaleAmountViewPage(Context mContext, int type) {
        this.mContext = mContext;
        this.mType = type;
        createP();
    }

    protected void createP() {
        mPresenter = new SaleAmountListPresenter();
        mPresenter.attachView(this);
    }

    public View getView() {
        if (mView == null) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.layout_fragment_sale_amount_list_page, null);
            mRefreshLayout = mView.findViewById(R.id.srl_refresh);
            mRecyclerView = mView.findViewById(R.id.rv_list);
            tv_total_money = mView.findViewById(R.id.tv_total_money);
            initPullToRefresh();
            initRecyclerView();
        }
        return mView;
    }

    private void initRecyclerView() {
        mAdapter = new SaleAmountAdapter(mContext, mType);
        mRecyclerView.addItemDecoration(new DecorationLLM(mContext, DecorationLLM.VERTICAL_LIST, R.drawable.shape_recyclerview_divider, ConvertUtils.dp2px(10)));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initPullToRefresh() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.resetNoMoreData();
                mPresenter.refresh(mType);
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.loadMore(mType);
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

    @Override
    public void onShowRefreshListToView(SaleAmountEntity entity) {
        if (null == entity) return;
        StringBuilder stringBuilder = new StringBuilder();
        if (mType == 1) {
            stringBuilder.append("已结算金额: ");
        } else {
            stringBuilder.append("待结算金额: ");
        }
        stringBuilder.append(StringUtil.formatAmount(entity.getTotal_money(), 2));
        tv_total_money.setText(changGoodTotalPriceText(stringBuilder.toString()));
        mAdapter.setData(entity.getList());
    }

    @Override
    public void onShowLoadMoreListToView(SaleAmountEntity entity) {
        mAdapter.addAll(entity.getList());
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
    public void destory() {
        if (null != mPresenter) {
            mPresenter.detachView();
            mPresenter.destoryModel();
            mPresenter = null;
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
}


