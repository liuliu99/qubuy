package com.qubuyer.business.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qubuyer.R;
import com.qubuyer.bean.order.OrderRefundEntity;
import com.qubuyer.business.good.activity.GoodDetailActivity;
import com.qubuyer.business.order.activity.OrderRefundDetailActivity;
import com.qubuyer.business.order.holder.OrderRefundListVH;
import com.qubuyer.utils.NavigationUtil;
import com.qubuyer.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * @author Susong
 * @date 创建时间2019/4/1
 * @description 订单退款列表adapter
 * @version
 */
public class OrderRefundListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<OrderRefundEntity> mData;
    private int mOrderStatus;

    public OrderRefundListAdapter(Context context) {
        mContext = context;
        mData = new ArrayList<>();
    }

    public void setData(List<OrderRefundEntity> data) {
        if (data != null) {
            int previousSize = mData.size();
            mData.clear();
            notifyItemRangeRemoved(0, previousSize);
            mData.addAll(data);
            notifyItemRangeInserted(0, mData.size());
        }
    }

    public List<OrderRefundEntity> getData() {
        return mData;
    }

    public void addAll(List<OrderRefundEntity> contents) {
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_order_refund_list, parent, false);
        return new OrderRefundListVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OrderRefundListVH) {
            setItemTicket((OrderRefundListVH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void setItemTicket(OrderRefundListVH holder, int position) {
        OrderRefundEntity entity = mData.get(position);
        holder.tv_order_no.setText("订单编号:" + entity.getOrder_sn());

        holder.iv_good_img.setUri(mContext, entity.getOriginal_img_full());
        holder.tv_good_name.setText(entity.getGoods_name());
        holder.tv_good_price.setText(StringUtil.formatAmount(entity.getShop_price(), 2));
        holder.tv_good_num.setText("x" + entity.getGoods_num() + "");
        holder.tv_right_one_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtil.overlay(mContext, OrderRefundDetailActivity.class, entity);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtil.overlay(mContext, GoodDetailActivity.class, entity.getGoods_id());
            }
        });
    }
}
