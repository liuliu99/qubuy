package com.qubuyer.business.mine.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ConvertUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.mine.MineFansEntity;
import com.qubuyer.business.mine.adapter.MineFansAdapter;
import com.qubuyer.business.mine.presenter.MineFansListPresenter;
import com.qubuyer.business.mine.view.IMineFansListView;
import com.qubuyer.customview.DecorationLLM;

import java.util.List;

import butterknife.BindView;


/**
 * @author Susong
 * @date 创建时间:2019/4/1
 * @description 我的粉丝activity
 * & @version
 */
public class MineFansActivity extends BaseActivity<MineFansListPresenter> implements IMineFansListView {
    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    private MineFansAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_mine_fans;
    }

    @Override
    protected MineFansListPresenter createP(Context context) {
        return new MineFansListPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setTitle("我的粉丝");
        initRecyclerView();
    }

    @Override
    protected void initData() {
        mPresenter.loadFansListData();
    }

    private void initRecyclerView() {
        mAdapter = new MineFansAdapter(this);
        rv_list.addItemDecoration(new DecorationLLM(this, DecorationLLM.VERTICAL_LIST, R.drawable.shape_recyclerview_divider, ConvertUtils.dp2px(15)));
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
    public void onShowFansListToView(List<MineFansEntity> list) {
        mAdapter.setData(list);
    }
}
