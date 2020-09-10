package com.qubuyer.business.mine.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.event.MineGetLetteryEvent;
import com.qubuyer.bean.mine.MineLotteryRecordEntity;
import com.qubuyer.business.mine.adapter.MineLotteryRecordAdapter;
import com.qubuyer.business.mine.presenter.MineLotteryRecordPresenter;
import com.qubuyer.business.mine.view.IMineLotteryRecordView;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.customview.BrowserActivity;
import com.qubuyer.customview.DecorationLLM;
import com.qubuyer.utils.EventBusUtil;
import com.qubuyer.utils.NavigationUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;


/**
 * @author Susong
 * @date 创建时间2020/1/10
 * @description 我的抽奖记录
 */
public class MineLotteryRecordListActivity extends BaseActivity<MineLotteryRecordPresenter> implements IMineLotteryRecordView {
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    private MineLotteryRecordAdapter mAdapter;
    private MineLotteryRecordEntity mSelectedLotteryRecordEntity;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_mine_comment_list;
    }

    @Override
    protected MineLotteryRecordPresenter createP(Context context) {
        return new MineLotteryRecordPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setTitle("抽奖记录");
        initRecyclerView();
        EventBusUtil.register(this);
    }

    @Override
    protected void initData() {
        if (ObjectUtils.isNotEmpty(getIntent())) {
            String lettery_id = getIntent().getStringExtra(AppConstant.INTENT_EXTRA_KEY);
            if (ObjectUtils.isNotEmpty(lettery_id)) {
                mSelectedLotteryRecordEntity = new MineLotteryRecordEntity();
                mSelectedLotteryRecordEntity.setId(lettery_id);
                NavigationUtil.overlay(MineLotteryRecordListActivity.this, MineLotteryAddressEditActivity.class);
            }
        }
        mPresenter.getMineLotteryRecordList();
    }

    private void initRecyclerView() {
        mAdapter = new MineLotteryRecordAdapter(this, new MineLotteryRecordAdapter.MineLotteryRecordAdapterListener() {
            @Override
            public void onGetLottery(MineLotteryRecordEntity entity) {
                mSelectedLotteryRecordEntity = entity;
                NavigationUtil.overlay(MineLotteryRecordListActivity.this, MineLotteryAddressEditActivity.class);
            }
        });
        rv_list.addItemDecoration(new DecorationLLM(this, DecorationLLM.VERTICAL_LIST, R.drawable.shape_recyclerview_divider, ConvertUtils.dp2px(10)));
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
    public void onShowMineLotteryRecordListToView(List<MineLotteryRecordEntity> list) {
        mAdapter.setData(list);
    }

    @Override
    public void onShowMineGetLotteryResultToView(boolean success) {
        if (success) {
            ToastUtils.showShort("领取成功");
            mPresenter.getMineLotteryRecordList();
        }
    }

    @Override
    public void destory() {

    }

    @Subscribe
    public void onEventMainThread(MineGetLetteryEvent event) {
        if (ObjectUtils.isEmpty(event) || ObjectUtils.isEmpty(mSelectedLotteryRecordEntity)) return;
        mPresenter.getLottery(mSelectedLotteryRecordEntity.getId(), event.getConsignee(), event.getMobile(), event.getProvince(), event.getCity(), event.getDistrict(), event.getAddress());
    }

    @Override
    protected void onDestroy() {
        EventBusUtil.unregister(this);
        super.onDestroy();
    }
}
