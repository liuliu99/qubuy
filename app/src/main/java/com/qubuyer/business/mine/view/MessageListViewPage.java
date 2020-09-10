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
import com.qubuyer.bean.mine.MessageEntity;
import com.qubuyer.business.mine.adapter.MessageListAdapter;
import com.qubuyer.business.mine.presenter.MessageListPresenter;
import com.qubuyer.customview.DecorationLLM;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @date 创建时间:2019/4/15
 * @author Susong
 * @description 消息列表viewpage
 & @version
 */
public class MessageListViewPage implements BaseRefreshView, IMessageListPageView {
    //当前上下文
    private Context mContext;
    private View mView;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private MessageListPresenter mPresenter;
    private MessageListAdapter mAdapter;
    //订单状态
    private int mType;

    public MessageListViewPage(Context mContext, int type) {
        this.mContext = mContext;
        this.mType = type;
        createP();
    }

    protected void createP() {
        mPresenter = new MessageListPresenter();
        mPresenter.attachView(this);
    }

    public View getView() {
        if (mView == null) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.layout_fragment_message_list_page, null);
            mRefreshLayout = mView.findViewById(R.id.srl_refresh);
            mRecyclerView = mView.findViewById(R.id.rv_list);
            initPullToRefresh();
            initRecyclerView();
        }
        return mView;
    }

    private void initRecyclerView() {
        mAdapter = new MessageListAdapter(mContext, mType);
        mRecyclerView.addItemDecoration(new DecorationLLM(mContext, DecorationLLM.VERTICAL_LIST, R.drawable.shape_recyclerview_divider, ConvertUtils.dp2px(15)));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initPullToRefresh() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NotNull RefreshLayout refreshLayout) {
                refreshLayout.resetNoMoreData();
                mPresenter.refresh(mType);
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NotNull RefreshLayout refreshLayout) {
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
    public void onShowRefreshListToView(List<MessageEntity> list) {
        mAdapter.setData(list);
    }

    @Override
    public void onShowLoadMoreListToView(List<MessageEntity> list) {
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


