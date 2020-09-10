package com.qubuyer.business.mine.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.ToolbarMenuEntity;
import com.qubuyer.bean.mine.RebateOrderEntity;
import com.qubuyer.business.mine.adapter.RebateOrderDetailGoodListAdapter;
import com.qubuyer.business.mine.presenter.RebateOrderDetailPresenter;
import com.qubuyer.business.mine.view.IRebateOrderDetailView;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.constant.NetConstant;
import com.qubuyer.customview.AbsToolbar;
import com.qubuyer.customview.BrowserActivity;
import com.qubuyer.customview.DecorationLLM;
import com.qubuyer.customview.RebateOrderDetailHorizontalProgressBar;
import com.qubuyer.utils.DialogUtil;
import com.qubuyer.utils.NavigationUtil;
import com.qubuyer.utils.StringUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author Susong
 * @date 创建时间2019/4/8
 * @description 折让订单详情
 */
public class RebateOrderDetailActivity extends BaseActivity<RebateOrderDetailPresenter> implements IRebateOrderDetailView {
    @BindView(R.id.tv_current_reabte_money)
    TextView tv_current_reabte_money;
    @BindView(R.id.horizontal_progress_view)
    RebateOrderDetailHorizontalProgressBar horizontal_progress_view;
    @BindView(R.id.tv_max_rebate_money)
    TextView tv_max_rebate_money;
    @BindView(R.id.tv_order_no)
    TextView tv_order_no;
    @BindView(R.id.rv_good_list)
    RecyclerView rv_good_list;
    @BindView(R.id.tv_order_create_time)
    TextView tv_order_create_time;
    @BindView(R.id.tv_order_money)
    TextView tv_order_money;

    private int mOrderId;

    private RebateOrderEntity mEntity;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_rebate_order_detail;
    }

    protected RebateOrderDetailPresenter createP(Context context) {
        return new RebateOrderDetailPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        if (null != getIntent() && null != getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY)) {
            mOrderId = (int) getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY);
        }
        setStatusBarDrawable(R.drawable.shape_rebate_order_status_bg);
        setNavigationDrawable(R.drawable.icon_good_detail_return_normal);
        setTitle("订单详情");
        setTitleColor(R.color.white);
        setToolbarBackgroundDrawable(R.drawable.shape_rebate_order_status_bg);
        initMenu();
    }

    private void initMenu() {
        List<ToolbarMenuEntity> toolbarMenuEntityList = new ArrayList<>();
        ToolbarMenuEntity toolbarMenuEntity = new ToolbarMenuEntity();
        toolbarMenuEntity.setDpWidth(24);
        toolbarMenuEntity.setDpHeight(24);
//        toolbarMenuEntity.setMenuContent("添加常用旅客");
        toolbarMenuEntity.setMenuDrawaleId(R.drawable.icon_rebate_order_detail_rule);
        toolbarMenuEntity.setOnMenuOnClickListener(new AbsToolbar.OnMenuOnClickListener() {
            @Override
            public void onMenuOnClick(View view) {
                Intent intent = new Intent(RebateOrderDetailActivity.this, BrowserActivity.class);
                intent.putExtra(BrowserActivity.KEY_URL, NetConstant.BASE_URL + "/home/html/discountRule");
                startActivity(intent);
            }
        });
        toolbarMenuEntityList.add(toolbarMenuEntity);
        inflateMenu(toolbarMenuEntityList);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initData() {
        mPresenter.getReabteOrderDetail(mOrderId + "");
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        dissmissLoadingDialog();
    }

    @OnClick({R.id.tv_withdraw})
    public void onClickWithButterKnife(View view) {
        switch (view.getId()) {
            case R.id.tv_withdraw:
            case R.id.iv_withdraw_more: //提现
                DialogUtil.getConfirmDialog(this, "", "确定要提现到余额吗?", "确定", "取消", true, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.rebateClose(null != mEntity ? mEntity.getRec_id() + "" : "");
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                break;
            case R.id.rl_line_credit: //授信额度
                break;
            case R.id.rl_dis_order: //折让订单
                NavigationUtil.overlay(this, RebateOrderActivity.class);
                break;
            case R.id.rl_sale_total: //销售总额
                NavigationUtil.overlay(this, SaleAmountActivity.class);
                break;
            case R.id.rl_income_expenditure: //收支明细
                break;
        }
    }

    @Override
    public void onShowRebateOrderDetailToView(RebateOrderEntity entity) {
        mEntity = entity;
        if (null != entity) {
            tv_current_reabte_money.setText(changeCurrentRebateMoneyContent("当前折让金额" + StringUtil.formatAmount(entity.getCurrent_price(), 2) + "元"));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            float currentMonth = (float) calendar.get(Calendar.MONTH) / 12 * 100;
//            horizontal_progress_view.setCurrentProgress(currentMonth);
            horizontal_progress_view.setProgressWithAnimation(entity.getCurrent_month_radio()).setCurrentProgressText(entity.getCurrent_radio() + "");
            tv_max_rebate_money.setText("最高折让: " + StringUtil.formatAmount(entity.getExpect_price(), 2) + "元");
            tv_order_no.setText("订单号: " + entity.getOrder_sn());
            List<RebateOrderEntity> list = new ArrayList<>();
            list.add(entity);
            RebateOrderDetailGoodListAdapter adapter = new RebateOrderDetailGoodListAdapter(this, list);
            rv_good_list.setLayoutManager(new LinearLayoutManager(this));
            rv_good_list.addItemDecoration(new DecorationLLM(this, DecorationLLM.VERTICAL_LIST, R.drawable.shape_recyclerview_divider, ConvertUtils.dp2px(15)));
            rv_good_list.setAdapter(adapter);
            tv_order_create_time.setText("下单时间: " + entity.getAdd_time());
            tv_order_money.setText(changeOrderMoneyContent("订单总额: " + StringUtil.formatAmount(entity.getTotal_price(), 2)));
        }
    }

    @Override
    public void onShowRebateCloseResultToView(boolean result) {
        if (result) {
            ToastUtils.showShort("提现成功");
            finish();
        }
    }

    private SpannableStringBuilder changeCurrentRebateMoneyContent(String value) {
        SpannableStringBuilder sp1 = new SpannableStringBuilder();
        sp1.append(value);

        sp1.setSpan(new ForegroundColorSpan(Color.parseColor("#F5F5F5")), 0, value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp1.setSpan(new AbsoluteSizeSpan(ConvertUtils.dp2px(12)), 0, value.indexOf("额") + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp1.setSpan(new AbsoluteSizeSpan(ConvertUtils.dp2px(56)), value.indexOf("额") + 1, value.indexOf("元"), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp1.setSpan(new AbsoluteSizeSpan(ConvertUtils.dp2px(14)), value.indexOf("元"), value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp1;
    }

    private SpannableStringBuilder changeOrderMoneyContent(String value) {
        SpannableStringBuilder sp1 = new SpannableStringBuilder();
        sp1.append(value);

        sp1.setSpan(new AbsoluteSizeSpan(ConvertUtils.dp2px(13)), 0, value.indexOf(":") + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp1.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 0, value.indexOf(":") + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        sp1.setSpan(new AbsoluteSizeSpan(ConvertUtils.dp2px(15)), value.indexOf(":") + 1, value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp1.setSpan(new ForegroundColorSpan(Color.parseColor("#FF9333")), value.indexOf(":") + 1, value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp1;
    }
}
