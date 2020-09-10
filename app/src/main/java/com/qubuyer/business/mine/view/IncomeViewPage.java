package com.qubuyer.business.mine.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ConvertUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.base.mvp.BaseRefreshView;
import com.qubuyer.bean.mine.IncomeEntity;
import com.qubuyer.business.mine.adapter.IncomeAdapter;
import com.qubuyer.business.mine.presenter.IncomeListPresenter;
import com.qubuyer.customview.DecorationLLM;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

/**
 * @author Susong
 * @date 创建时间2019/6/23
 * @description 收支明细viewpage
 * @version
 */
public class IncomeViewPage implements BaseRefreshView, IIncomePageView {
    //当前上下文
    private Context mContext;
    private View mView;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private IncomeListPresenter mPresenter;
    private IncomeAdapter mAdapter;
    //类型 	income收入wa提现
    private String mType;

    public IncomeViewPage(Context mContext, String type) {
        this.mContext = mContext;
        this.mType = type;
        createP();
    }

    protected void createP() {
        mPresenter = new IncomeListPresenter();
        mPresenter.attachView(this);
    }

    public View getView() {
        if (mView == null) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.layout_fragment_in_come_list_page, null);
            mRefreshLayout = mView.findViewById(R.id.srl_refresh);
            mRecyclerView = mView.findViewById(R.id.rv_list);
            initPullToRefresh();
            initRecyclerView();
        }
        return mView;
    }

    private void initRecyclerView() {
        mAdapter = new IncomeAdapter(mContext);
        mRecyclerView.addItemDecoration(new DecorationLLM(mContext, DecorationLLM.VERTICAL_LIST, R.drawable.shape_recyclerview_divider, ConvertUtils.dp2px(15)));
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
    public void onShowRefreshListToView(List<IncomeEntity> list) {
        mAdapter.setData(list);
    }

    @Override
    public void onShowLoadMoreListToView(List<IncomeEntity> list) {
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
        if (null != mRefreshLayout && null != mAdapter && mAdapter.getData().isEmpty()) {
            mRefreshLayout.autoRefresh();
        }
    }
}


