package com.qubuyer.business.order.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qubuyer.R;
import com.qubuyer.bean.order.OrderEntity;
import com.qubuyer.bean.order.OrderRefundEntity;
import com.qubuyer.business.good.activity.GoodDetailActivity;
import com.qubuyer.business.order.activity.OrderRefundDetailActivity;
import com.qubuyer.business.order.holder.OrderDetailGoodListVH;
import com.qubuyer.business.order.holder.OrderListGoodListVH;
import com.qubuyer.utils.NavigationUtil;
import com.qubuyer.utils.StringUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Susong
 * @date 创建时间2019/3/29
 * @description 订单列表页商品列表adapter
 */
public class OrderDetailGoodListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<OrderEntity.SplitBean.OrderGoodBean> mData;
    private OrderEntity mOrderEntity;
    private OrderDetailGoodListListener mListener;

    public OrderDetailGoodListAdapter(Context context, List<OrderEntity.SplitBean.OrderGoodBean> data, OrderEntity orderEntity, OrderDetailGoodListListener listener) {
        mContext = context;
        mData = data;
        mOrderEntity = orderEntity;
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_order_detail_good_list, parent, false);
        return new OrderDetailGoodListVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OrderDetailGoodListVH) {
            setCommonInfo((OrderDetailGoodListVH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        if (null != mData) {
            return mData.size();
        }
        return 0;
    }

    private void setCommonInfo(OrderDetailGoodListVH holder, int position) {
        OrderEntity.SplitBean.OrderGoodBean entity = mData.get(position);
        holder.iv_good_img.setUri(mContext, entity.getOriginal_img_full());
        holder.tv_good_name.setText(entity.getGoods_name());
        if (!TextUtils.isEmpty(entity.getSpec_key_name())) {
            holder.tv_good_spec.setVisibility(View.VISIBLE);
            holder.tv_good_spec.setText(entity.getSpec_key_name() + "");
        } else {
            holder.tv_good_spec.setVisibility(View.GONE);
        }
        holder.tv_good_price.setText("¥" + StringUtil.formatAmount(entity.getMember_goods_price(), 2));
        holder.tv_refund.setVisibility(View.GONE);
        if (entity.getIs_return() > 0) {
            holder.tv_refund.setVisibility(View.VISIBLE);
            holder.tv_refund.setText("售后详情");
            holder.tv_refund.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderRefundEntity entity1 = new OrderRefundEntity();
                    entity1.setId(entity.getIs_return());
                    NavigationUtil.overlay(mContext, OrderRefundDetailActivity.class, entity1);
                }
            });
            holder.tv_good_num.setVisibility(View.GONE);
        } else if (entity.getIs_return() == 0) {
            holder.tv_refund.setVisibility(View.VISIBLE);
            holder.tv_refund.setText("申请售后");
            holder.tv_refund.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        mListener.onRefundListener(entity);
                    }
                }
            });
            holder.tv_good_num.setVisibility(View.GONE);
        }
        holder.tv_add_shop_cart.setVisibility(View.GONE);
        if (null != mOrderEntity) {
            if (mOrderEntity.getIs_complete() == 1) {
                holder.tv_add_shop_cart.setVisibility(View.VISIBLE);
                holder.tv_add_shop_cart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != mListener) {
                            mListener.onAddShopCartListener(entity);
                        }
                    }
                });
                holder.tv_good_num.setVisibility(View.GONE);
            }
        }
        holder.tv_good_num.setText("x" + entity.getGoods_num());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtil.overlay(mContext, GoodDetailActivity.class, entity.getGoods_id());
            }
        });
    }

    public interface OrderDetailGoodListListener{
        void onRefundListener(OrderEntity.SplitBean.OrderGoodBean enetity);
        void onAddShopCartListener(OrderEntity.SplitBean.OrderGoodBean enetity);
    }
}
