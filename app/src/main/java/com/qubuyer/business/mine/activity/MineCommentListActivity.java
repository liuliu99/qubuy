package com.qubuyer.business.mine.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.mine.MineCommentEntity;
import com.qubuyer.business.mine.adapter.MineCommentAdapter;
import com.qubuyer.business.mine.presenter.MineCommentPresenter;
import com.qubuyer.business.mine.view.IMineCommentView;
import com.qubuyer.customview.PictureZoomView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * @author Susong
 * @date 创建时间2020/1/9
 * @description 我的评论列表
 */
public class MineCommentListActivity extends BaseActivity<MineCommentPresenter> implements IMineCommentView {
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    private MineCommentAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_mine_comment_list;
    }

    @Override
    protected MineCommentPresenter createP(Context context) {
        return new MineCommentPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setTitle("我的评价");
        initRecyclerView();
    }

    @Override
    protected void initData() {
        mPresenter.getMineCommentList();
    }

    private void initRecyclerView() {
        mAdapter = new MineCommentAdapter(this, new MineCommentAdapter.MineCommentAdapterListener() {
            @Override
            public void onImgClick(int firstPosition, int secondPosition) {
                if (null != mAdapter.getData() && !mAdapter.getData().isEmpty() && null != mAdapter.getData().get(firstPosition).getImg_full() && !mAdapter.getData().get(firstPosition).getImg_full().isEmpty()) {
                    List<String> list = new ArrayList<>();
                    for (String path : mAdapter.getData().get(firstPosition).getImg_full()) {
                        if (path.contains("?")) {
                            list.add(path.substring(0, path.indexOf("?")));
                        } else {
                            list.add(path);
                        }
                    }
                    PictureZoomView.actionStartUrl(MineCommentListActivity.this, list, secondPosition);
//                        PictureZoomView.actionStartUrl(mContext, mAdapter.getData().get(firstPosition).getImg_full(), secondPosition);
                }
            }
        });
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
    public void onShowMineCommentListToView(List<MineCommentEntity> list) {
        mAdapter.setData(list);
    }

    @Override
    public void destory() {

    }
}
