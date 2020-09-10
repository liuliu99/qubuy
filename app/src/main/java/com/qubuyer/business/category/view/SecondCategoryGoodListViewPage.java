package com.qubuyer.business.category.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.base.mvp.BaseRefreshView;
import com.qubuyer.bean.category.SecondCategoryGoodEntity;
import com.qubuyer.business.category.adapter.SecondCategoryGoodListAdapter;
import com.qubuyer.business.category.presenter.SecondCategoryGoodPresenter;
import com.qubuyer.customview.GoodItemDecoration;
import com.qubuyer.customview.HomeGoodItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Susong
 * @date 创建时间2019/3/20
 * @description 二级分类商品列表页
 * @version
 */
public class SecondCategoryGoodListViewPage implements BaseRefreshView, ISecondCategoryGoodView {
    //当前上下文
    private Context mContext;
    private View mView;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private SecondCategoryGoodPresenter mPresenter;
    private SecondCategoryGoodListAdapter mAdapter;
    //一级分类id
    private String mFirstCategoryId;
    //二级分类id
    private String mSecondCategoryId;

    public SecondCategoryGoodListViewPage(Context mContext,String firstCategoryId, String secondCategoryId) {
        this.mContext = mContext;
        mFirstCategoryId = firstCategoryId;
        mSecondCategoryId = secondCategoryId;
        createP();
    }

    protected void createP() {
        mPresenter = new SecondCategoryGoodPresenter();
        mPresenter.attachView(this);
    }

    public View getView() {
        if (mView == null) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.layout_activity_second_category_good_list_page, null);
            mRefreshLayout = mView.findViewById(R.id.srl_refresh);
            mRecyclerView = mView.findViewById(R.id.rv_list);
            initPullToRefresh();
            initRecyclerView();
        }
        return mView;
    }

    private void initRecyclerView() {
        mAdapter = new SecondCategoryGoodListAdapter(mContext);
        mRecyclerView.addItemDecoration(new GoodItemDecoration());
//        mRecyclerView.addItemDecoration(new DecorationLLM(mContext, DecorationLLM.VERTICAL_LIST, R.drawable.shape_recyclerview_divider, DensityUtil.dip2px(mContext, 10)));
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initPullToRefresh() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.resetNoMoreData();
                mPresenter.refresh(mFirstCategoryId, mSecondCategoryId);
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.loadMore(mFirstCategoryId, mSecondCategoryId);
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
        ((BaseActivity)mContext).doResponseError(code, message);
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

    @Override
    public void onShowRefreshDataToView(SecondCategoryGoodEntity entity) {
        if (null != entity && null != entity.getList() && null != entity.getList().getData()) {
            mAdapter.setData(entity.getList().getData());
        } else {
            mAdapter.setData(new ArrayList<>(0));
        }
    }

    @Override
    public void onShowLoadMoreGoodDataToView(SecondCategoryGoodEntity entity) {
        if (null != entity && null != entity.getList() && null != entity.getList().getData()) {
            mAdapter.addAll(entity.getList().getData());
        } else {
            mAdapter.addAll(new ArrayList<>(0));
        }
    }
}


