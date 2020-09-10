package com.qubuyer.business.home.fragment;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.base.fragment.BaseFragment;
import com.qubuyer.base.listener.PauseOnScrollListener;
import com.qubuyer.bean.event.GoToMineEvent;
import com.qubuyer.bean.home.HomeBannerEntity;
import com.qubuyer.bean.home.HomeCategoryEntity;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.bean.home.HomeSaleTopEntity;
import com.qubuyer.bean.mine.UserEntity;
import com.qubuyer.bean.mine.UserMessageCountEntity;
import com.qubuyer.business.good.activity.SearchGoodActivity;
import com.qubuyer.business.home.activity.ScanActivity;
import com.qubuyer.business.home.adapter.HomeAdapter;
import com.qubuyer.business.home.holder.HomeBannerVH;
import com.qubuyer.business.home.presenter.HomePresenter;
import com.qubuyer.business.home.view.IHomeView;
import com.qubuyer.customview.HomeGoodItemDecoration;
import com.qubuyer.customview.ImageViewAutoLoad;
import com.qubuyer.utils.EventBusUtil;
import com.qubuyer.utils.NavigationUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment<HomePresenter> implements IHomeView {
    @BindView(R.id.rl_toolbar)
    RelativeLayout rl_toolbar;
    @BindView(R.id.iv_scan)
    ImageViewAutoLoad iv_scan;
    @BindView(R.id.fl_message)
    FrameLayout fl_message;
    @BindView(R.id.iv_message)
    ImageViewAutoLoad iv_message;
    @BindView(R.id.tv_message_count)
    TextView tv_message_count;
    @BindView(R.id.srl_content)
    SmartRefreshLayout srl_content;
    @BindView(R.id.rv_content)
    RecyclerView rv_content;

    @BindView(R.id.iv_scroll_top)
    ImageViewAutoLoad iv_scroll_top;

    private HomeAdapter mAdapter;

    private int mOverallXScroll = 0;
    private int mHeight;

    private GridLayoutManager mLayoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_fragment_home;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (rv_content != null) {
                RecyclerView.ViewHolder viewHolder = rv_content.findViewHolderForAdapterPosition(0);
                if (viewHolder instanceof HomeBannerVH) {
                    HomeBannerVH holder = (HomeBannerVH) viewHolder;
                    holder.banner.start();

                }
            }
        } else {
            if (rv_content != null) {
                RecyclerView.ViewHolder viewHolder = rv_content.findViewHolderForAdapterPosition(0);
                if (viewHolder instanceof HomeBannerVH) {
                    HomeBannerVH holder = (HomeBannerVH) viewHolder;
                    holder.banner.releaseBanner();
                }
            }
        }
    }

    @Override
    protected HomePresenter createP(Fragment context) {
        return new HomePresenter();
    }

    @Override
    protected void initWidget(View root) {
//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this);
//        }
//        mHeight = ConvertUtils.dp2px(204) - BarUtils.getStatusBarHeight();
        mHeight = BarUtils.getStatusBarHeight();
//        mHeight = ConvertUtils.dp2px(10);
        isUpdateStatusBar = false;
        initPullToRefresh();
        initRecyclerView();
        srl_content.autoRefresh();
        tv_message_count.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        dissmissLoadingDialog();
    }

    private void initToolbar(boolean isTop) {
        if (isTop) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, BarUtils.getStatusBarHeight(), 0, 0);
            rl_toolbar.setLayoutParams(layoutParams);
        } else {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 0);
            rl_toolbar.setLayoutParams(layoutParams);
        }
    }

    private void initPullToRefresh() {
        srl_content.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false);
                mPresenter.refresh(3);
                mOverallXScroll = 0;
            }
        });
        srl_content.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
//                mPresenter.loadMore();
                mPresenter.loadGoodList(3);
//                srl_content.finishLoadMore(0, false, true);
            }
        });
        srl_content.setEnableScrollContentWhenLoaded(true);
//        srl_content.setEnableLoadMore(false);
    }

    private void initRecyclerView() {
        mAdapter = new HomeAdapter(mContext, new HomeAdapter.OnGoodTopClickListener() {
            @Override
            public void onGoodTopClick(int goodType) {
//                mPresenter.loadGoodList(goodType);
            }
        });
        mLayoutManager = new GridLayoutManager(mContext, 2);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (mAdapter.getItemViewType(position) == HomeAdapter.ITEM_INFO) {
                    return 1;
                }
                if (mAdapter.getItemViewType(position) == HomeAdapter.ITEM_FOOT) {
                    return mLayoutManager.getSpanCount();
                }
                return mLayoutManager.getSpanCount();
            }
        });
//        ((SimpleItemAnimator)rv_content.getItemAnimator()).setSupportsChangeAnimations(false);
//        rv_content.getItemAnimator().setChangeDuration(0);
        rv_content.setItemAnimator(null);//设置动画为null来解决闪烁问题
        rv_content.setLayoutManager(mLayoutManager);
        rv_content.setAdapter(mAdapter);
        rv_content.addOnScrollListener(new PauseOnScrollListener(mContext));
        rv_content.addItemDecoration(new HomeGoodItemDecoration());
        rv_content.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mOverallXScroll = mOverallXScroll + dy;// 累加y值 解决滑动一半y值为0
//                ToastUtils.showShort(mOverallXScroll+"");
                setToolbarBackgroundResource();
            }
        });
    }

    private void setToolbarBackgroundResource() {
        if (mOverallXScroll <= 1) {
            ((BaseActivity) getActivity()).setStatusBarVisibility(false);
//            ((BaseActivity) getActivity()).setStatusBarInVisibility(false);
            initToolbar(true);
            rl_toolbar.setBackground(ContextCompat.getDrawable(mContext, R.color.transparent));
            iv_scan.setDrawableId(mContext, R.drawable.icon_home_scan_normal);
            iv_message.setDrawableId(mContext, R.drawable.icon_default_headimg);
            iv_scroll_top.setVisibility(View.GONE);
        } else if (mOverallXScroll >= mHeight) {
            ((BaseActivity) getActivity()).setStatusBarVisibility(true);
//            ((BaseActivity) getActivity()).setStatusBarInVisibility(true);
            ((BaseActivity) getActivity()).setStatusBarColor(ContextCompat.getColor(mContext, R.color.white));
            initToolbar(false);
            rl_toolbar.setBackground(ContextCompat.getDrawable(mContext, R.color.white));
            iv_scan.setDrawableId(mContext, R.drawable.icon_home_scan_scroll);
            iv_message.setDrawableId(mContext, R.drawable.icon_default_headimg);
            iv_scroll_top.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            setToolbarBackgroundResource();
//            mPresenter.getUserMessageCount();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setToolbarBackgroundResource();
//        mPresenter.getUserMessageCount();
    }

    @OnClick({R.id.ll_search, R.id.fl_message, R.id.iv_scroll_top,R.id.iv_scan})
    public void onClickByButterKnife(View v) {
        switch (v.getId()) {
            case R.id.iv_scan: //扫描
                NavigationUtil.overlay(mContext, ScanActivity.class);
                break;
            case R.id.ll_search: //搜索
                NavigationUtil.overlay(mContext, SearchGoodActivity.class);
                break;
            case R.id.fl_message: //消息
                if (((BaseActivity)mContext).checkLogin()){
//                    NavigationUtil.overlay(mContext, MessageListActivity.class);
                    EventBusUtil.sendEvent(new GoToMineEvent());
                }
                break;
            case R.id.iv_scroll_top: //返回顶部
                mOverallXScroll = 0;
                rv_content.scrollToPosition(0);
                break;
        }
    }

    @Override
    public void onShowBannerToView(List<HomeBannerEntity> homeBannerEntityList) {
        mAdapter.setBannerData(homeBannerEntityList);
    }

    @Override
    public void onShowCategoryToView(List<HomeCategoryEntity> homeCategoryEntityList) {
        mAdapter.setCategoryData(homeCategoryEntityList);
    }

    @Override
    public void onShowSaleTopToView(HomeSaleTopEntity entity) {
        mAdapter.setSaleTop(entity);
    }

    @Override
    public void onShowUserInfoToView(UserEntity entity) {
        mAdapter.setUserEntity(entity);
    }

    @Override
    public void onShowChoicenessGoodListToView(List<HomeGoodEntity> list) {
        mAdapter.setChoiceness(list);
    }

    @Override
    public void onShowLimitGoodListToView(List<HomeGoodEntity> list) {
        mAdapter.setLimit(list);
    }

    @Override
    public void onShowSpecialGoodListToView(List<HomeGoodEntity> list) {
        mAdapter.setSpecial(list);
    }

    @Override
    public void onShowFirstpublishGoodListToView(List<HomeGoodEntity> list) {
        mAdapter.setFirstpublish(list);
    }

    @Override
    public void onShowSuperReturnGoodListToView(List<HomeGoodEntity> list) {
        mAdapter.setSuperReturn(list);
    }

    @Override
    public void onShowDailyDiscountGoodListToView(List<HomeGoodEntity> list) {
        mAdapter.setDailyDiscount(list);
    }

    @Override
    public void onShowRefreshDataToView(List<HomeGoodEntity> list) {
//        list.add(new HomeGoodEntity());
        mAdapter.setData(list);
//        mAdapter.setClickData(homeGoodEntityList);
    }

    @Override
    public void onShowClickDataToView(List<HomeGoodEntity> list) {
        list.add(new HomeGoodEntity());
        mAdapter.setClickData(list);
    }

    @Override
    public void onShowLoadMoreGoodDataToView(List<HomeGoodEntity> homeGoodEntityList) {
        mAdapter.addAll(homeGoodEntityList);
    }

    @Override
    public void onShowUserMessageCountDataToView(UserMessageCountEntity entity) {
        if (null != entity && entity.getCount() > 0) {
            tv_message_count.setVisibility(View.VISIBLE);
            tv_message_count.setText(entity.getCount() + "");
        } else {
            tv_message_count.setVisibility(View.GONE);
            tv_message_count.setText("0");
        }
    }

    @Override
    public void finishLoadMore(int delay, boolean success, boolean noMoreData) {
        srl_content.finishLoadMore(delay, success, noMoreData);
    }

    @Override
    public void finishRefresh(boolean success) {
        srl_content.finishRefresh(success);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        if (EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().unregister(this);
//        }
    }
}
