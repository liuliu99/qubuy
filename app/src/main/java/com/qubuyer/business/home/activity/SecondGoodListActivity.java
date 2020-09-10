package com.qubuyer.business.home.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ConvertUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.business.category.adapter.SecondCategoryGoodListAdapter;
import com.qubuyer.business.home.presenter.HomeSecondGoodListPresenter;
import com.qubuyer.business.home.view.IHomeSecondGoodListPageView;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.customview.GoodItemDecoration;
import com.qubuyer.customview.ImageViewAutoLoad;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @date 创建时间:2019/6/5
 * @author Susong
 * @description 二级商品列表activity
 & @version
 */
public class SecondGoodListActivity extends BaseActivity<HomeSecondGoodListPresenter> implements IHomeSecondGoodListPageView{
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srl_refresh;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.iv_scroll_top)
    ImageViewAutoLoad iv_scroll_top;

    private SecondCategoryGoodListAdapter mAdapter;

    private int mOverallXScroll = 0;
    private int mHeight;

    //1:会员精选 2:每日限量 3:热门推荐 4:新品首发 5:超级返
    private int mType;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_second_good_list;
    }

    @Override
    protected HomeSecondGoodListPresenter createP(Context context) {
        return new HomeSecondGoodListPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        this.mHeight = ConvertUtils.dp2px(145);
        if (null != getIntent() && null != getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY)) {
            mType = (int) getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY);
        }
        switch (mType) {
            case 1:
                setTitle("会员精选");
                break;
            case 2:
                setTitle("每日限量");
                break;
            case 3:
                setTitle("热门推荐");
                break;
            case 4:
                setTitle("新品首发");
                break;
            case 5:
                setTitle("超级返");
                break;
            case 6:
                setTitle("每日折扣");
                break;
        }
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

    private void initRecyclerView() {
        mAdapter = new SecondCategoryGoodListAdapter(this);
        rv_list.addItemDecoration(new GoodItemDecoration());
//        mRecyclerView.addItemDecoration(new DecorationLLM(mContext, DecorationLLM.VERTICAL_LIST, R.drawable.shape_recyclerview_divider, DensityUtil.dip2px(mContext, 10)));
        rv_list.setLayoutManager(new GridLayoutManager(this, 2));
        rv_list.setAdapter(mAdapter);
        rv_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mOverallXScroll += dy;
                if (mOverallXScroll <= 0) {
                    iv_scroll_top.setVisibility(View.GONE);
                } else if (mOverallXScroll > mHeight) {
                    iv_scroll_top.setVisibility(View.VISIBLE);
                }
            }
        });
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
    public void onShowRefreshListToView(List<HomeGoodEntity> list) {
        mAdapter.setData(list);
    }

    @Override
    public void onShowLoadMoreListToView(List<HomeGoodEntity> list) {
        mAdapter.addAll(list);
    }

    @Override
    public void finishLoadMore(int delay, boolean success, boolean noMoreData) {
        srl_refresh.finishLoadMore(delay, success, noMoreData);
    }

    @Override
    public void finishRefresh(boolean success) {
        srl_refresh.finishRefresh(success);
    }

    @OnClick({R.id.iv_scroll_top})
    public void onClickWithButterKnfie(View view) {
        switch (view.getId()) {
            case R.id.iv_scroll_top:
                mOverallXScroll = 0;
                rv_list.scrollToPosition(0);
                break;
        }
    }
}
