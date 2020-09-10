package com.qubuyer.business.order.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.event.DelOrderEvent;
import com.qubuyer.bean.event.SOSelectInvoiceEvent;
import com.qubuyer.bean.mine.MineInvoiceEntitiy;
import com.qubuyer.bean.order.OrderEntity;
import com.qubuyer.bean.order.OrderRefundReasonEntity;
import com.qubuyer.business.good.activity.GoodDetailActivity;
import com.qubuyer.business.good.activity.GoodInvoiceActivity;
import com.qubuyer.business.mine.activity.AddressListActivity;
import com.qubuyer.business.order.adapter.OrderDetailGoodListAdapter;
import com.qubuyer.business.order.presenter.OrderDetailPresenter;
import com.qubuyer.business.order.view.CancelReasonDialog;
import com.qubuyer.business.order.view.IOrderDetailView;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.constant.NetConstant;
import com.qubuyer.customview.AbsToolbar;
import com.qubuyer.customview.BrowserActivity;
import com.qubuyer.customview.DecorationLLM;
import com.qubuyer.customview.OverSrollView;
import com.qubuyer.utils.DialogUtil;
import com.qubuyer.utils.NavigationUtil;
import com.qubuyer.utils.StringUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author Susong
 * @date 创建时间:2019/3/29
 * @description 订单列表activity
 * & @version
 */
public class OrderDetailActivity extends BaseActivity<OrderDetailPresenter> implements IOrderDetailView {
    @BindView(R.id.tv_status)
    TextView tv_status;
    @BindView(R.id.tv_status_desc)
    TextView tv_status_desc;

    @BindView(R.id.rl_logistics)
    RelativeLayout rl_logistics;
    @BindView(R.id.tv_logistics_desc)
    TextView tv_logistics_desc;
    @BindView(R.id.tv_logistics_time)
    TextView tv_logistics_time;

    @BindView(R.id.rl_address)
    RelativeLayout rl_address;
    @BindView(R.id.tv_address_name)
    TextView tv_address_name;
    @BindView(R.id.tv_address_phone)
    TextView tv_address_phone;
    @BindView(R.id.tv_address)
    TextView tv_address;

    @BindView(R.id.rv_good_list)
    RecyclerView rv_good_list;

    @BindView(R.id.ll_service)
    LinearLayout ll_service;

    @BindView(R.id.tv_order_no)
    TextView tv_order_no;
    @BindView(R.id.tv_order_create_time)
    TextView tv_order_create_time;
    @BindView(R.id.tv_order_pay_type)
    TextView tv_order_pay_type;
    @BindView(R.id.rl_send_type)
    RelativeLayout rl_send_type;
    @BindView(R.id.tv_order_send_type_type)
    TextView tv_order_send_type_type;
    @BindView(R.id.tv_order_invoice_type_type)
    TextView tv_order_invoice_type_type;
    @BindView(R.id.tv_order_invoice_show)
    TextView tv_order_invoice_show;
    @BindView(R.id.tv_order_invoice_title)
    TextView tv_order_invoice_title;
    @BindView(R.id.tv_order_invoice_content)
    TextView tv_order_invoice_content;
    @BindView(R.id.tv_order_leave_message)
    TextView tv_order_leave_message;
    @BindView(R.id.tv_order_price)
    TextView tv_order_price;
    @BindView(R.id.tv_order_freight)
    TextView tv_order_freight;
    @BindView(R.id.tv_order_real_price)
    TextView tv_order_real_price;

    @BindView(R.id.tv_one_btn)
    TextView tv_one_btn;
    @BindView(R.id.tv_two_btn)
    TextView tv_two_btn;
    @BindView(R.id.tv_three_btn)
    TextView tv_three_btn;
    @BindView(R.id.tv_four_btn)
    TextView tv_four_btn;

    @BindView(R.id.rl_invoice_type)
    RelativeLayout rl_invoice_type;
    @BindView(R.id.rl_invoice_title)
    RelativeLayout rl_invoice_title;
    @BindView(R.id.rl_invoice_content)
    RelativeLayout rl_invoice_content;

    @BindView(R.id.ll_bottom)
    LinearLayout ll_bottom;

    @BindView(R.id.osv_container)
    OverSrollView osv_container;

    @BindView(R.id.rl_pay_type)
    RelativeLayout rl_pay_type;

    private OrderEntity mOrderEntity;
    private List<OrderRefundReasonEntity> mCancelReasonList;

    private CancelReasonDialog cancelReasonDialog;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_order_detail;
    }

    @Override
    protected OrderDetailPresenter createP(Context context) {
        return new OrderDetailPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        setStatusBarDrawable(R.drawable.shape_order_detail_top_bg);
        setTitle("订单详情");
        if (null != getIntent() && null != getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY)) {
            mOrderEntity = (OrderEntity) getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY);
        }
    }

    @Override
    protected void initData() {
        if (null != mOrderEntity) {
            mPresenter.getOrderDetail(String.valueOf(mOrderEntity.getOrder_id()), 0 != mOrderEntity.getId() ? mOrderEntity.getId() + "" : "");
        }
        mPresenter.getCancelReasonList();
    }

    @Override
    public AbsToolbar toolbar() {
        return null;
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        dissmissLoadingDialog();
    }

    @OnClick({R.id.iv_back, R.id.rl_logistics, R.id.rl_address, R.id.ll_service,R.id.tv_order_invoice_show})
    public void onClickWithButterKnfie(View v) {
        switch (v.getId()) {
            case R.id.iv_back: //返回按钮
                finish();
                break;
            case R.id.rl_logistics: //物流

                break;
            case R.id.rl_address: //地址
                NavigationUtil.overlay(this, AddressListActivity.class);
                break;
            case R.id.ll_service://客服
                Intent intent = new Intent(this, BrowserActivity.class);
                intent.putExtra(BrowserActivity.KEY_URL, NetConstant.ONLINE_SERVICE_URL);
                intent.putExtra(BrowserActivity.KEY_DEFALT_TITLE, "在线客服");
                intent.putExtra(BrowserActivity.KEY_NEED_UPDATE_TITLE, false);
                startActivity(intent);
                break;
            case R.id.tv_order_invoice_show: //查看发票
                if (null == mOrderEntity) return;
                NavigationUtil.overlay(OrderDetailActivity.this, OrderInvoiceDetailActivity.class, mOrderEntity);
                break;
        }
    }

    @Override
    public void onShowOrderDetailToView(OrderEntity entity) {
        if (null == entity) return;
        mOrderEntity = entity;
        tv_status.setText(entity.getState_brief());
        if (!TextUtils.isEmpty(entity.getShipping_name())) {
            rl_logistics.setVisibility(View.GONE);
            tv_logistics_desc.setText(entity.getShipping_name());
            tv_logistics_time.setText(entity.getShipping_time());
        } else {
            rl_logistics.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(entity.getConsignee())) {
            rl_address.setVisibility(View.VISIBLE);
            tv_address_name.setText(entity.getConsignee());
            tv_address_phone.setText(entity.getMobile());
            tv_address.setText(entity.getFull_address());
        } else {
            rl_address.setVisibility(View.GONE);
        }
        if (null != entity.getOrder_goods() && !entity.getOrder_goods().isEmpty()) {
            rv_good_list.setVisibility(View.VISIBLE);
            OrderDetailGoodListAdapter adapter = new OrderDetailGoodListAdapter(this, entity.getOrder_goods(), entity, new OrderDetailGoodListAdapter.OrderDetailGoodListListener() {
                @Override
                public void onRefundListener(OrderEntity.SplitBean.OrderGoodBean good) {
                    HashMap map = new HashMap();
                    map.put("order_entity", entity);
                    map.put("good_entity", good);
                    NavigationUtil.overlay(OrderDetailActivity.this, OrderRefundActivity.class, map);
                }

                @Override
                public void onAddShopCartListener(OrderEntity.SplitBean.OrderGoodBean enetity) {
                    mPresenter.addGoodToCart(enetity.getGoods_id() + "", 1, enetity.getItem_id() + "");
                }
            });
            rv_good_list.setLayoutManager(new LinearLayoutManager(this));
            rv_good_list.addItemDecoration(new DecorationLLM(this, DecorationLLM.VERTICAL_LIST, R.drawable.shape_recyclerview_divider, ConvertUtils.dp2px(15)));
            rv_good_list.setAdapter(adapter);
        } else {
            rv_good_list.setVisibility(View.GONE);
        }
        tv_order_no.setText(entity.getOrder_sn() + "");
        tv_order_create_time.setText(TimeUtils.millis2String(entity.getAdd_time() * 1000, new SimpleDateFormat("yyyy-MM-dd HH:mm")));
        if (!TextUtils.isEmpty(entity.getPay_name())) {
            rl_pay_type.setVisibility(View.VISIBLE);
            tv_order_pay_type.setText(entity.getPay_name());
        } else {
            rl_pay_type.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(entity.getShipping_name())) {
            rl_send_type.setVisibility(View.VISIBLE);
            tv_order_send_type_type.setText(entity.getShipping_name());
        } else {
            rl_send_type.setVisibility(View.GONE);
        }
        if (ObjectUtils.isEmpty(mOrderEntity.getInvoice_info())) {
            rl_invoice_type.setVisibility(View.VISIBLE);
            tv_order_invoice_type_type.setText("不开发票");
            tv_order_invoice_show.setVisibility(View.GONE);
            rl_invoice_title.setVisibility(View.GONE);
            rl_invoice_content.setVisibility(View.GONE);
        } else {
            tv_order_invoice_type_type.setText("电子发票");
            tv_order_invoice_title.setText(mOrderEntity.getInvoice_info().getInvoice_title().equalsIgnoreCase("1") ? "个人" : ObjectUtils.isNotEmpty(mOrderEntity.getInvoice_info().getInvoice_name()) ? mOrderEntity.getInvoice_info().getInvoice_name() : "");
            tv_order_invoice_content.setText(entity.getInvoice_desc());
        }
        tv_order_leave_message.setText(!TextUtils.isEmpty(entity.getUser_note()) ? entity.getUser_note() : "暂无");
        tv_order_price.setText("¥" + StringUtil.formatAmount(entity.getGoods_price(), 2));
        tv_order_freight.setText("+ ¥" + StringUtil.formatAmount("0.00", 2));
        tv_order_real_price.setText(changGoodTotalPriceText("实付款: ¥" + StringUtil.formatAmount(entity.getOrder_amount(), 2)));

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ll_bottom.setVisibility(View.GONE);
        tv_one_btn.setVisibility(View.GONE);
        tv_two_btn.setVisibility(View.GONE);
        tv_three_btn.setVisibility(View.GONE);
        tv_four_btn.setVisibility(View.GONE);
        if (entity.getState_brief().equalsIgnoreCase("待发货")) {
            tv_status_desc.setText("商家会尽快发货喔");
        }
        if (entity.getIs_pay() == 1) {
            tv_status_desc.setText("请尽快支付");
            layoutParams.setMargins(0, 0, 0, ConvertUtils.dp2px(50));
            ll_bottom.setVisibility(View.VISIBLE);
            tv_two_btn.setVisibility(View.VISIBLE);
            tv_two_btn.setText("去支付");
            tv_two_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HashMap map = new HashMap();
                    map.put("order_entity", entity);
                    NavigationUtil.overlay(OrderDetailActivity.this, OrderNowPayActivity.class, map);
                }
            });
        }
        if (entity.getIs_cancel() == 1) {
//            tv_status_desc.setText("订单已取消");
            layoutParams.setMargins(0, 0, 0, ConvertUtils.dp2px(50));
            ll_bottom.setVisibility(View.VISIBLE);
            tv_one_btn.setVisibility(View.VISIBLE);
            tv_one_btn.setText(entity.getState_brief().equalsIgnoreCase("待发货") ? "申请退款" : "取消订单");
            tv_one_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (entity.getIs_pay() == 1) {
                        mPresenter.cancelOrder(entity.getOrder_id() + "", "");
                    } else {
                        showOrderCancelReasonDialog(entity);
                    }
                }
            });
        }
        if (entity.getIs_receiving() == 1) {
            layoutParams.setMargins(0, 0, 0, ConvertUtils.dp2px(50));
            ll_bottom.setVisibility(View.VISIBLE);
            tv_status_desc.setText("请小主耐心等候，快递小哥正火速运送中");
            tv_two_btn.setVisibility(View.VISIBLE);
            tv_two_btn.setText("确认收货");
            tv_two_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.confirmGood(entity.getOrder_id() + "");
                }
            });
            tv_three_btn.setVisibility(View.VISIBLE);
            tv_three_btn.setText("查看物流");
            tv_three_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavigationUtil.overlay(OrderDetailActivity.this, OrderLogisticsActivity.class, entity.getId());
                }
            });
        }
        if (entity.getIs_extend_time() == 1) {
            tv_one_btn.setVisibility(View.VISIBLE);
            tv_one_btn.setText("延长收货");
            tv_one_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.extendReceiving(entity.getId() + "");
                }
            });
        }
        if (entity.getIs_invoice() == 1) {
            ll_bottom.setVisibility(View.VISIBLE);
            tv_four_btn.setVisibility(View.VISIBLE);
            tv_four_btn.setText("申请开票");
            tv_four_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavigationUtil.overlay(OrderDetailActivity.this, GoodInvoiceActivity.class);
                }
            });
        }
        if (entity.getIs_comment() == 1) {
            layoutParams.setMargins(0, 0, 0, ConvertUtils.dp2px(50));
            ll_bottom.setVisibility(View.VISIBLE);
            tv_status_desc.setText("感谢您在趣买买购物，欢迎您再次光临！");
            tv_two_btn.setVisibility(View.VISIBLE);
            tv_two_btn.setText("去评价");
            tv_two_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavigationUtil.overlay(OrderDetailActivity.this, OrderCommentListActivity.class, entity);
                }
            });

            if (entity.getInvoice_type() == 1) {
                tv_one_btn.setVisibility(View.VISIBLE);
                tv_one_btn.setText("查看发票");
                tv_one_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NavigationUtil.overlay(OrderDetailActivity.this, OrderInvoiceDetailActivity.class, entity);
                    }
                });
            }
        }
//        if (entity.getIs_return() == 1) {
//            layoutParams.setMargins(0, 0, 0, ConvertUtils.dp2px(50));
//            ll_bottom.setVisibility(View.VISIBLE);
//            tv_status_desc.setText("感谢您在趣买买购物，欢迎您再次光临！");
//            tv_two_btn.setVisibility(View.VISIBLE);
//            tv_two_btn.setText("申请售后");
//            tv_two_btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    NavigationUtil.overlay(OrderDetailActivity.this, OrderRefundActivity.class);
//                }
//            });
//        }
        if (entity.getIs_complete() == 1) {
            layoutParams.setMargins(0, 0, 0, ConvertUtils.dp2px(50));
            ll_bottom.setVisibility(View.VISIBLE);
            tv_status_desc.setText("感谢您在趣买买购物，欢迎您再次光临！");
            tv_two_btn.setVisibility(View.VISIBLE);
            tv_two_btn.setText("再次购买");
            tv_two_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavigationUtil.overlay(OrderDetailActivity.this, GoodDetailActivity.class, entity.getOrder_goods().get(0).getGoods_id());
                }
            });
        }
        if (entity.getIs_delete() == 1) {
            layoutParams.setMargins(0, 0, 0, ConvertUtils.dp2px(50));
            ll_bottom.setVisibility(View.VISIBLE);
            tv_status_desc.setText("感谢您在趣买买购物，欢迎您再次光临！");
            tv_one_btn.setVisibility(View.VISIBLE);
            tv_one_btn.setText("删除订单");
            tv_one_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.delOrder(String.valueOf(entity.getOrder_id()));
                }
            });
        }
        osv_container.setLayoutParams(layoutParams);
    }

    @Override
    public void onShowAddGoodToCartResultToView(boolean result) {
        if (result) {
            ToastUtils.showShort("添加成功");
        }
    }

    @Override
    public void onShowCancelOrderResultToView(boolean result) {
        if (result) {
            ToastUtils.showShort("取消成功");
            finish();
        }
    }

    @Override
    public void onShowDelOrderResultToView(boolean result) {
        if (result) {
            ToastUtils.showShort("删除订单成功");
            EventBus.getDefault().post(new DelOrderEvent());
            finish();
        }
    }

    @Override
    public void onShowConfirmGoodResultToView(boolean result) {
        if (result) {
            ToastUtils.showShort("确认收货成功");
            finish();
        }
    }

    @Override
    public void onShowCancelReasonListToView(List<OrderRefundReasonEntity> list) {
        mCancelReasonList = list;
    }

    @Override
    public void onShowExtendReceivingResultToView(boolean result) {
        if (result) {
            ToastUtils.showShort("延长收货成功");
            tv_one_btn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onShowApplyInvoiceResultToView(boolean result) {
        if (result) {
            ToastUtils.showShort("申请开票成功");
            tv_four_btn.setVisibility(View.GONE);
        }
    }

    private void showOrderCancelReasonDialog(OrderEntity entity) {
        if (null == mCancelReasonList || mCancelReasonList.isEmpty()) {
            ToastUtils.showShort("取消原因数据异常");
            return;
        }
        if (null == cancelReasonDialog) {
            cancelReasonDialog = new CancelReasonDialog(this, new CancelReasonDialog.SelectRefunReasonListener() {
                @Override
                public void onRefundReasonClickListener(OrderRefundReasonEntity entity1) {
                    mPresenter.cancelOrder(entity.getOrder_id() + "", entity1.getId() + "");
                }
            });
            cancelReasonDialog.setData(mCancelReasonList);
        }
        if (!cancelReasonDialog.isShowing()) {
            cancelReasonDialog.show();
            DialogUtil.setDialogWindowAttr(cancelReasonDialog, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM, R.style.shopping_cart_anim);
        }
    }

    private SpannableStringBuilder changGoodTotalPriceText(String value) {
        SpannableStringBuilder sp1 = new SpannableStringBuilder();
        sp1.append(value);

        sp1.setSpan(new AbsoluteSizeSpan(ConvertUtils.dp2px(13)), 0, value.indexOf(":") + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp1.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 0, value.indexOf(":") + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        sp1.setSpan(new AbsoluteSizeSpan(ConvertUtils.dp2px(15)), value.indexOf(":") + 1, value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp1.setSpan(new ForegroundColorSpan(Color.parseColor("#FE761C")), value.indexOf(":") + 1, value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp1;
    }

    @Override
    public void destory() {

    }

    /**
     * EventBus事件分发方法
     */
    @Subscribe
    public void onEventMainThread(SOSelectInvoiceEvent event) {
        if (null != event) {
            MineInvoiceEntitiy entity = event.getEntitiy();
            if (null == entity || entity.getIsUse() == 0 || null == mOrderEntity) return;
            mPresenter.applyInvoice(mOrderEntity.getOrder_id() + "",
                    0 != mOrderEntity.getId() ? mOrderEntity.getId()+"": null,
                    entity.getCompanyDutyparagraph(),
                    entity.getPhone(),
                    entity.getEmail(),
                    entity.getType() + "",
                    entity.getCompanyName(),
                    entity.getContent(),
                    "1");
        }
    }
}
