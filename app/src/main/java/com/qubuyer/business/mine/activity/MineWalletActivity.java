package com.qubuyer.business.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.mine.UserEntity;
import com.qubuyer.bean.mine.WalletInfoEntity;
import com.qubuyer.bean.order.OrderEntity;
import com.qubuyer.business.mine.presenter.WalletPresenter;
import com.qubuyer.business.mine.view.IWalletView;
import com.qubuyer.constant.NetConstant;
import com.qubuyer.customview.BrowserActivity;
import com.qubuyer.utils.NavigationUtil;
import com.qubuyer.utils.SessionUtil;
import com.qubuyer.utils.StringUtil;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author Susong
 * @date 创建时间:2019/4/1
 * @description 我的钱包activity
 * & @version
 */
public class MineWalletActivity extends BaseActivity<WalletPresenter> implements IWalletView {
    @BindView(R.id.tv_total_money)
    TextView tv_total_money;

    @BindView(R.id.tv_dis_order_projected_income)
    TextView tv_dis_order_projected_income;
    @BindView(R.id.tv_sale_total_projected_income)
    TextView tv_sale_total_projected_income;

    private OrderEntity mOrderEntity;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_mine_wallet;
    }

    @Override
    protected WalletPresenter createP(Context context) {
        return new WalletPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setTitle("我的钱包");
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getWalletInfo();
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
    public void onShoWalletInfoToView(WalletInfoEntity entity) {
        if (null != entity) {
            tv_total_money.setText(StringUtil.formatAmount(entity.getMoney(), 2));
            tv_dis_order_projected_income.setText("预计收入：" + StringUtil.formatAmount(entity.getRestore(), 2) + "元");
            tv_sale_total_projected_income.setText("预计收入：" + StringUtil.formatAmount(entity.getDistribut(), 2) + "元");
        }
    }

    @OnClick({R.id.tv_withdraw, R.id.iv_withdraw_more, R.id.rl_line_credit, R.id.rl_dis_order, R.id.rl_sale_total, R.id.rl_income_expenditure})
    public void onClickWithButterKnife(View view) {
        switch (view.getId()) {
            case R.id.tv_withdraw:
            case R.id.iv_withdraw_more: //提现
                NavigationUtil.overlay(this, WithdrawActivity.class);
                break;
            case R.id.rl_line_credit: //授信额度
                break;
            case R.id.rl_dis_order: //折让订单
                NavigationUtil.overlay(this, RebateOrderActivity.class);
                break;
            case R.id.rl_sale_total: //销售总额
//                if (!isAgency()) {
//                    Intent intent = new Intent(this, BrowserActivity.class);
//                    intent.putExtra(BrowserActivity.KEY_URL, NetConstant.BASE_URL + "/home/html/application?token=" + SessionUtil.getInstance().getToken());
//                    startActivity(intent);
//                } else {
//                    NavigationUtil.overlay(this, SaleAmountActivity.class);
//                }
                NavigationUtil.overlay(this, SaleAmountActivity.class);
                break;
            case R.id.rl_income_expenditure: //收支明细
//                if (!isAgency()) {
//                    Intent intent = new Intent(this, BrowserActivity.class);
//                    intent.putExtra(BrowserActivity.KEY_URL, NetConstant.BASE_URL + "/home/html/application?token=" + SessionUtil.getInstance().getToken());
//                    startActivity(intent);
//                } else {
//                    NavigationUtil.overlay(this, IncomeActivity.class);
//                }
                NavigationUtil.overlay(this, IncomeActivity.class);
                break;
        }
    }

    private boolean isAgency() {
        UserEntity userEntity = SessionUtil.getInstance().getUserInfo();
        if (null != userEntity && userEntity.getIs_distribut() == 1) {
            return true;
        }
        return false;
    }
}
