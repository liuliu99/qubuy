package com.qubuyer.business.good.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.good.GoodSOResultEntity;
import com.qubuyer.bean.good.SOGoodInfoEntity;
import com.qubuyer.bean.payment.CalcOrderPriceResultEntity;
import com.qubuyer.business.good.adapter.SOGoodListAdapter;
import com.qubuyer.business.good.presenter.SOPresenter;
import com.qubuyer.business.good.view.ISOView;
import com.qubuyer.business.good.view.SOAddressView;
import com.qubuyer.business.good.view.SOInvoiceView;
import com.qubuyer.business.good.view.SOMoneyView;
import com.qubuyer.business.login.activity.LoginActivity;
import com.qubuyer.business.order.activity.OrderNowPayActivity;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.customview.DecorationLLM;
import com.qubuyer.utils.DialogUtil;
import com.qubuyer.utils.NavigationUtil;
import com.qubuyer.utils.SessionUtil;
import com.qubuyer.utils.StringUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * @author Susong
 * @date 创建时间:2019/1/24
 * @description 提交订单Activity
 * & @version
 */
public class SOActivity extends BaseActivity<SOPresenter> implements ISOView {
    @BindView(R.id.v_address)
    SOAddressView v_address;
    @BindView(R.id.rv_good_list)
    RecyclerView rv_good_list;
    @BindView(R.id.et_leave_message)
    EditText et_leave_message;
    @BindView(R.id.v_invoice)
    SOInvoiceView v_invoice;
    @BindView(R.id.tv_good_price)
    TextView tv_good_price;
    @BindView(R.id.tv_good_freight)
    TextView tv_good_freight;
    @BindView(R.id.tv_good_discounts)
    TextView tv_good_discounts;
    //    @BindView(R.id.v_pay_list)
//    SOPayListView v_pay_list;
    @BindView(R.id.v_money)
    SOMoneyView v_money;

    //订单号
    private String mOrderPayId;

    private String mGoodId;
    private String mItemId;
    private int mSelectedGoodNum;
    //购买类型 1:商品详情购买 2:购物车购买
    private int mBuyType;

    private SOGoodListAdapter mAdapter;

    @Override
    public void onResume() {
        super.onResume();
//        if (!TextUtils.isEmpty(mOrderPayId)) {
//            v_pay_list.setOrderPayId(mOrderPayId);
//            v_pay_list.loadResult();
//        }
    }

    @Override
    protected void onDestroy() {
//        v_pay_list.destory();
        super.onDestroy();
    }

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_so;
    }

    @Override
    protected SOPresenter createP(Context context) {
        return new SOPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        if (null != getIntent() && null != getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY)) {
            Map map = (Map) getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY);
            mBuyType = (int) map.get("buy_type");
            if (null != map.get("good_id")) {
                mGoodId = (String) map.get("good_id");
            }
            if (null != map.get("item_id")) {
                mItemId = (String) map.get("item_id");
            }
            if (null != map.get("good_num")) {
                mSelectedGoodNum = (int) map.get("good_num");
            }
        }
        setTitle("确认订单");
        setNavigationDrawable(R.drawable.icon_return_black);
        initAddress();
        initGoodListView();
        initInvoiceView();
        initPayListView();
        initMoneyView();
    }

    @Override
    protected void initData() {
        mPresenter.getSOInfo(mBuyType, mGoodId, mSelectedGoodNum, mItemId);
        calcOrderPrice();
    }

    private void calcOrderPrice() {
        if (null != v_address.getSelectedAddress()) {
            mPresenter.calcOrderPrice(mBuyType,
                    null != v_address.getSelectedAddress() ? v_address.getSelectedAddress().getAddress_id() : "",
                    mGoodId + "",
                    mSelectedGoodNum + "",
                    mItemId + "");
        }
    }

    private void initAddress() {
        v_address.setListener(new SOAddressView.OnSOAddressListener() {
            @Override
            public void onAddressClickListener() {
                calcOrderPrice();
            }
        });
    }

    private void initGoodListView() {
        mAdapter = new SOGoodListAdapter(this);
        rv_good_list.addItemDecoration(new DecorationLLM(this, DecorationLLM.VERTICAL_LIST, R.drawable.shape_recyclerview_divider, ConvertUtils.dp2px(15)));
        rv_good_list.setLayoutManager(new LinearLayoutManager(this));
        rv_good_list.setAdapter(mAdapter);
    }

    private void initInvoiceView() {

    }

    private void initPayListView() {
//        v_pay_list.setListener(new SOPayListView.OnSOPayListListener() {
//            @Override
//            public void onSOPayListClickListener(PayListEntity entity) {
//            }
//        });
    }

    private void initMoneyView() {
        v_money.setListener(new SOMoneyView.OnSOMoneyListener() {
            @Override
            public void onOrderDetailClickListener() {
            }

            @Override
            public void onSubmitOrderClickListener() {
                if (!SessionUtil.getInstance().isLogined()) {
                    DialogUtil.getConfirmDialog(SOActivity.this, "提示", "需要登录后，才能继续以下操作，是否现在登录？", "登录", "取消", false, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            NavigationUtil.overlay(SOActivity.this, LoginActivity.class);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                    return;
                }
                if (null == v_address.getSelectedAddress()) {
                    ToastUtils.showShort("请选择收货地址");
                    return;
                }
//                if (null == v_invoice.getSelectedInvoice()) {
//                    ToastUtils.showShort("请填写发票信息");
//                    return;
//                }
                mPresenter.submitOrder(mBuyType,
                        null != v_address.getSelectedAddress() ? v_address.getSelectedAddress().getAddress_id() : "",
                        null == v_invoice.getSelectedInvoice() || v_invoice.getSelectedInvoice().getIsUse() == 0 ? "0" : "1",
                        null != v_invoice.getSelectedInvoice() ? v_invoice.getSelectedInvoice().getType() + "" : "",
                        null != v_invoice.getSelectedInvoice() ? v_invoice.getSelectedInvoice().getCompanyDutyparagraph() : "",
                        null != v_invoice.getSelectedInvoice() ? v_invoice.getSelectedInvoice().getCompanyName() : "",
                        null != v_invoice.getSelectedInvoice() ? v_invoice.getSelectedInvoice().getContent() : "",
                        null != v_invoice.getSelectedInvoice() ? v_invoice.getSelectedInvoice().getEmail() : "",
                        et_leave_message.getText().toString(),
                        mGoodId,
                        mSelectedGoodNum + "",
                        mItemId + "",
                        v_address.getSelectedAddress().getMobile());
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
    public void onShowSOGoodInfoToView(SOGoodInfoEntity entity) {
        if (null != entity) {
            if (null != entity.getCartList()) {
                mAdapter.setData(entity.getCartList());
            }
            if (null != entity.getCartPriceInfo()) {
                tv_good_price.setText("¥" + StringUtil.formatAmount(entity.getCartPriceInfo().getTotal_fee(), 2));
                v_money.setPayPrice(entity.getCartPriceInfo().getTotal_fee() + "");
                tv_good_freight.setText("+¥0.00");
                tv_good_discounts.setText("-¥0.00");
            }
        }
    }

//    @Override
//    public void onShowSubmitOrderResultToView(GoodSOResultEntity entity) {
//        if (null != entity) {
//            v_pay_list.setOrderPayId(entity.getOrder_id());
//            v_pay_list.pay();
//        }
//    }

    @Override
    public void destory() {

    }

    @Override
    public void onShowSubmitOrderResultToView(GoodSOResultEntity entity) {
        if (null != entity) {
            mOrderPayId = entity.getOrder_id();
//            v_pay_list.setOrderPayId(mOrderPayId);
//            v_pay_list.pay();
            HashMap map = new HashMap();
            map.put("order_so_entity", entity);
            NavigationUtil.forward(this, OrderNowPayActivity.class, map);
        }
    }

    @Override
    public void onShowCalcOrderPriceResultToView(CalcOrderPriceResultEntity entity) {
        if (null == entity) return;
        tv_good_price.setText("¥" + StringUtil.formatAmount(entity.getGoods_price(), 2));
        tv_good_freight.setText("+¥" + StringUtil.formatAmount(entity.getShipping_price(), 2));
        tv_good_discounts.setText("-¥" + StringUtil.formatAmount(entity.getOrder_cut_price(), 2));
        v_money.setPayPrice(StringUtil.formatAmount(entity.getOrder_amount(), 2));
        v_money.setMsg1(entity.getMsg1());
        v_money.setMsg2(entity.getMsg2());
//        mSelectedGoodNum = entity.getTotal_num();
    }
}
