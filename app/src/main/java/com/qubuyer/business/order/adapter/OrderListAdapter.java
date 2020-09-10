package com.qubuyer.business.order.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ConvertUtils;
import com.qubuyer.R;
import com.qubuyer.bean.TimeMode;
import com.qubuyer.bean.order.OrderEntity;
import com.qubuyer.business.good.activity.GoodDetailActivity;
import com.qubuyer.business.order.activity.OrderCommentListActivity;
import com.qubuyer.business.order.activity.OrderDetailActivity;
import com.qubuyer.business.order.activity.OrderInvoiceDetailActivity;
import com.qubuyer.business.order.activity.OrderLogisticsActivity;
import com.qubuyer.business.order.activity.OrderNowPayActivity;
import com.qubuyer.business.order.holder.OrderListVH;
import com.qubuyer.utils.NavigationUtil;
import com.qubuyer.utils.StringUtil;
import com.qubuyer.utils.TimeUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * @author Susong
 * @date 创建时间:2019/2/22
 * @description 订单列表Adapter
 * & @version
 */
public class OrderListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<OrderEntity> mData;
    private String mOrderStatus;
    //用于退出activity,避免countdown，造成资源浪费。
    private SparseArray<CountDownTimer> mCountDownMap;
    private OrderListOperationListener mListener;

    public OrderListAdapter(Context context, String orderStatus, OrderListOperationListener listener) {
        mContext = context;
        mOrderStatus = orderStatus;
        mData = new ArrayList<>();
        mCountDownMap = new SparseArray<>();
        mListener = listener;
    }

    public void setData(List<OrderEntity> data) {
        if (data != null) {
            int previousSize = mData.size();
            mData.clear();
            notifyItemRangeRemoved(0, previousSize);
            mData.addAll(data);
            notifyItemRangeInserted(0, mData.size());
        }
    }

    public List<OrderEntity> getData() {
        return mData;
    }

    public void addAll(List<OrderEntity> contents) {
        if (contents != null) {
            int size = mData.size();
            size += 0;
            this.mData.addAll(contents);
            notifyItemRangeInserted(size, contents.size());
            notifyItemRangeChanged(size, contents.size());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_order_list, parent, false);
        return new OrderListVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OrderListVH) {
            setItemTicket((OrderListVH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void setItemTicket(OrderListVH holder, int position) {
        OrderEntity entity = mData.get(position);
        holder.tv_order_no.setText("订单编号:" + entity.getOrder_sn());
        StringBuilder payPrice = new StringBuilder();
        //立即付款
        View.OnClickListener nowPayListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap map = new HashMap();
                map.put("order_entity", entity);
                NavigationUtil.overlay(mContext, OrderNowPayActivity.class, map);
            }
        };
        //再次购买
        View.OnClickListener againBuyListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtil.overlay(mContext, GoodDetailActivity.class, entity.getOrder_goods().get(0).getGoods_id());
            }
        };
        //订单详情
        OrderGoodAdapter.OnGoodListGoodListListener goodListListener = new OrderGoodAdapter.OnGoodListGoodListListener() {
            @Override
            public void onGoodListGoodListClick() {
                NavigationUtil.overlay(mContext, OrderDetailActivity.class, entity);
            }
        };
        //取消订单
        View.OnClickListener cancelOrder = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onCancelOrderClick(entity);
                }
            }
        };
        //查看发票
        View.OnClickListener seeInvoice = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtil.overlay(mContext, OrderInvoiceDetailActivity.class, entity);
            }
        };
        //确认收货
        View.OnClickListener comfirmGood = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onConfirmGoodClick(entity.getId() + "");
                }
            }
        };
        //去评价
        View.OnClickListener commentGood = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtil.overlay(mContext, OrderCommentListActivity.class, entity);
            }
        };
        //查看物流
        View.OnClickListener seeLogistics = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtil.overlay(mContext, OrderLogisticsActivity.class, entity.getId());
            }
        };
        //延长收货
        View.OnClickListener extendReceiving = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.extendReceiving(entity.getId() + "");
                }
            }
        };
        //申请开票
        View.OnClickListener applyInvoice = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.applyInvoice(entity);
                }
            }
        };
        //删除订单
        View.OnClickListener delOrder = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onDelOrderClick(entity);
                }
            }
        };
        switch (mOrderStatus) {
            case OrderEntity.ORDER_GROUP_STATUS_ALL: //全部订单
            case OrderEntity.ORDER_GROUP_STATUS_OBLIGATION: //待付款
            case OrderEntity.ORDER_GROUP_STATUS_WAIT_SEND: //待发货
            case OrderEntity.ORDER_GROUP_STATUS_WAIT_RECEIVING: //待收货
            case OrderEntity.ORDER_GROUP_STATUS_TO_BE_COMMENT: //待评价
            case OrderEntity.ORDER_GROUP_STATUS_FINISH: //已完成
                holder.tv_order_status.setText(entity.getState_brief());
                if (null != entity.getOrder_goods() && entity.getOrder_goods().size() > 1) {
                    holder.rl_one_good.setVisibility(View.GONE);
                    holder.rl_more_good.setVisibility(View.VISIBLE);

                    holder.rv_good_list.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
                    OrderGoodAdapter adapter = new OrderGoodAdapter(mContext, entity.getOrder_goods(), goodListListener);
                    holder.rv_good_list.setAdapter(adapter);
                } else {
                    holder.rl_one_good.setVisibility(View.VISIBLE);
                    holder.rl_more_good.setVisibility(View.GONE);

                    OrderEntity.SplitBean.OrderGoodBean orderGoodsBean = entity.getOrder_goods().get(0);
                    if (null != orderGoodsBean.getGoods()) {
                        holder.iv_good_img.setUri(mContext, orderGoodsBean.getGoods().getOriginal_img());
                    } else {
                        holder.iv_good_img.setDrawableId(mContext, R.mipmap.ic_logo);
                    }
                    holder.tv_good_name.setText(orderGoodsBean.getGoods_name());
                    holder.tv_good_price.setText(StringUtil.formatAmount(orderGoodsBean.getGoods_price(), 2));
                    holder.tv_good_num.setText("x" + orderGoodsBean.getGoods_num() + "");
                }
                holder.tv_good_total_price.setText(changGoodTotalPriceText("共" + entity.getOrder_goods().size() + "件商品, 总金额: ¥" + StringUtil.formatAmount(entity.getOrder_amount(), 2)));
                holder.tv_overtime.setVisibility(View.GONE);
                holder.tv_right_one_btn.setVisibility(View.GONE);
                holder.tv_right_two_btn.setVisibility(View.GONE);
                holder.tv_right_three_btn.setVisibility(View.GONE);
                holder.tv_right_four_btn.setVisibility(View.GONE);
                if (entity.getIs_pay() == 1) {
                    holder.tv_overtime.setVisibility(View.VISIBLE);
                    if (null != holder.countDownTimer) {
                        holder.countDownTimer.cancel();
                    }
                    holder.countDownTimer = new CountDownTimer(entity.getTime_out() * 1000 - System.currentTimeMillis(), 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            TimeMode timeMode = TimeUtil.parseRemainTime(millisUntilFinished);
                            if (timeMode.getDay() > 0) {
                                timeMode.setHour(timeMode.getHour() + timeMode.getDay() * 24);
                            }
                            holder.tv_overtime.setText(timeMode.getHour() + "小时后订单关闭");
                        }

                        @Override
                        public void onFinish() {
                            holder.tv_overtime.setText("订单已关闭");
                            holder.tv_right_two_btn.setVisibility(View.GONE);
                            holder.tv_right_one_btn.setVisibility(View.GONE);
                        }
                    }.start();
                    mCountDownMap.put(holder.countDownTimer.hashCode(), holder.countDownTimer);

                    holder.tv_right_one_btn.setVisibility(View.VISIBLE);
                    holder.tv_right_one_btn.setText("去支付");
                    holder.tv_right_one_btn.setOnClickListener(nowPayListener);
                }
                if (entity.getIs_cancel() == 1) {
                    holder.tv_right_two_btn.setVisibility(View.VISIBLE);
                    holder.tv_right_two_btn.setText(entity.getState_brief().equalsIgnoreCase("待发货") ? "申请退款" : "取消");
                    holder.tv_right_two_btn.setOnClickListener(cancelOrder);
                }
                if (entity.getIs_receiving() == 1) {
                    holder.tv_right_one_btn.setVisibility(View.VISIBLE);
                    holder.tv_right_one_btn.setText("确认收货");
                    holder.tv_right_one_btn.setOnClickListener(comfirmGood);

                    holder.tv_right_four_btn.setVisibility(View.VISIBLE);
                    holder.tv_right_four_btn.setText("查看物流");
                    holder.tv_right_four_btn.setOnClickListener(seeLogistics);
                }
                if (entity.getIs_invoice() == 1) {
                    holder.tv_right_three_btn.setVisibility(View.VISIBLE);
                    holder.tv_right_three_btn.setText("申请开票");
                    holder.tv_right_three_btn.setOnClickListener(applyInvoice);
                }
                if (entity.getIs_extend_time() == 1) {
                    holder.tv_right_two_btn.setVisibility(View.VISIBLE);
                    holder.tv_right_two_btn.setText("延长收货");
                    holder.tv_right_two_btn.setOnClickListener(extendReceiving);
                }
                if (entity.getIs_comment() == 1) {
                    holder.tv_right_one_btn.setVisibility(View.VISIBLE);
                    holder.tv_right_one_btn.setText("去评价");
                    holder.tv_right_one_btn.setOnClickListener(commentGood);

                    if (entity.getInvoice_type() == 1) {
                        holder.tv_right_two_btn.setVisibility(View.VISIBLE);
                        holder.tv_right_two_btn.setText("查看发票");
                        holder.tv_right_two_btn.setOnClickListener(seeInvoice);
                    }
                }
                if (entity.getIs_complete() == 1) {
                    holder.tv_right_one_btn.setVisibility(View.VISIBLE);
                    holder.tv_right_one_btn.setText("再次购买");
                    holder.tv_right_one_btn.setOnClickListener(againBuyListener);

                    if (entity.getInvoice_type() == 1) {
                        holder.tv_right_two_btn.setVisibility(View.VISIBLE);
                        holder.tv_right_two_btn.setText("查看发票");
                        holder.tv_right_two_btn.setOnClickListener(seeInvoice);
                    }
                }
                if (entity.getIs_delete() == 1) {
                    holder.tv_right_two_btn.setVisibility(View.VISIBLE);
                    holder.tv_right_two_btn.setText("删除订单");
                    holder.tv_right_two_btn.setOnClickListener(delOrder);
                }
                break;
        }
        holder.rl_one_good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtil.overlay(mContext, OrderDetailActivity.class, entity);
            }
        });
        holder.rl_more_good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtil.overlay(mContext, OrderDetailActivity.class, entity);
            }
        });
        holder.rv_good_list.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return holder.rl_more_good.onTouchEvent(event);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtil.overlay(mContext, OrderDetailActivity.class, entity);
            }
        });
    }

    private SpannableStringBuilder changGoodTotalPriceText(String value) {
        SpannableStringBuilder sp1 = new SpannableStringBuilder();
        sp1.append(value);

        sp1.setSpan(new AbsoluteSizeSpan(ConvertUtils.dp2px(12)), 0, value.indexOf(":") + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp1.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 0, value.indexOf(":") + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        sp1.setSpan(new AbsoluteSizeSpan(ConvertUtils.dp2px(16)), value.indexOf(":") + 1, value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp1.setSpan(new ForegroundColorSpan(Color.parseColor("#FF681D")), value.indexOf(":") + 1, value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp1;
    }

    /**
     * 清空资源
     */
    public void cancelAllTimers() {
        if (mCountDownMap == null) {
            return;
        }
        for (int i = 0, length = mCountDownMap.size(); i < length; i++) {
            CountDownTimer cdt = mCountDownMap.get(mCountDownMap.keyAt(i));
            if (cdt != null) {
                cdt.cancel();
            }
        }
    }

    public interface OrderListOperationListener {
        void onCancelOrderClick(OrderEntity entity);

        void onConfirmGoodClick(String orderId);

        void extendReceiving(String orderId);

        void applyInvoice(OrderEntity entity);

        void onDelOrderClick(OrderEntity entity);
    }
}
