package com.qubuyer.business.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ConvertUtils;
import com.qubuyer.R;
import com.qubuyer.bean.order.OrderLogisticsEntity;
import com.qubuyer.business.order.holder.OrderLogisticsListVH;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


/**
 * @date 创建时间:2019/4/15
 * @author Susong
 * @description 物流列表adapter
 & @version
 */
public class OrderLogisticsListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<OrderLogisticsEntity.TracesBean> mData;

    public OrderLogisticsListAdapter(Context context) {
        mContext = context;
        mData = new ArrayList<>();
    }

    public void setData(List<OrderLogisticsEntity.TracesBean> data) {
        if (data != null) {
            int previousSize = mData.size();
            mData.clear();
            notifyItemRangeRemoved(0, previousSize);
            mData.addAll(data);
            notifyItemRangeInserted(0, mData.size());
        }
    }

    public List<OrderLogisticsEntity.TracesBean> getData() {
        return mData;
    }

    public void addAll(List<OrderLogisticsEntity.TracesBean> contents) {
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_order_logistics_list, parent, false);
        return new OrderLogisticsListVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OrderLogisticsListVH) {
            setItemTicket((OrderLogisticsListVH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void setItemTicket(OrderLogisticsListVH holder, int position) {
        OrderLogisticsEntity.TracesBean entity = mData.get(position);
        if (position == 0) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ConvertUtils.dp2px(14), ConvertUtils.dp2px(14));
            holder.v_ball.setLayoutParams(layoutParams);
            holder.v_ball.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_logistics_status_current));

            holder.v_line_vertical_one.setVisibility(View.INVISIBLE);
            holder.v_line_vertical_two.setVisibility(View.VISIBLE);
        } else if (position == mData.size() - 1) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ConvertUtils.dp2px(6), ConvertUtils.dp2px(6));
            holder.v_ball.setLayoutParams(layoutParams);
            holder.v_ball.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_logistics_status_before));

            holder.v_line_vertical_one.setVisibility(View.VISIBLE);
            holder.v_line_vertical_two.setVisibility(View.GONE);
            holder.v_line_horizontal.setVisibility(View.INVISIBLE);
        } else {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ConvertUtils.dp2px(6), ConvertUtils.dp2px(6));
            holder.v_ball.setLayoutParams(layoutParams);
            holder.v_ball.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_logistics_status_before));

            holder.v_line_vertical_one.setVisibility(View.VISIBLE);
            holder.v_line_vertical_two.setVisibility(View.VISIBLE);
        }
        holder.tv_status_desc.setText(entity.getAcceptStation());
        holder.tv_time.setText(entity.getAcceptTime());
    }
}
