package com.qubuyer.business.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qubuyer.R;
import com.qubuyer.bean.mine.RebateOrderEntity;
import com.qubuyer.business.mine.activity.RebateOrderDetailActivity;
import com.qubuyer.business.mine.holder.RebateOrderListVH;
import com.qubuyer.utils.NavigationUtil;
import com.qubuyer.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * @author Susong
 * @date 创建时间2019/4/3
 * @description 折让订单adapter
 */
public class RebateOrderAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<RebateOrderEntity> mData;
    private int mType;

    public RebateOrderAdapter(Context context, int type) {
        mContext = context;
        mType = type;
        mData = new ArrayList<>();
    }

    public void setData(List<RebateOrderEntity> data) {
        if (data != null) {
            int previousSize = mData.size();
            mData.clear();
            notifyItemRangeRemoved(0, previousSize);
            mData.addAll(data);
            notifyItemRangeInserted(0, mData.size());
        }
    }

    public List<RebateOrderEntity> getData() {
        return mData;
    }

    public void addAll(List<RebateOrderEntity> contents) {
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rebate_order_list, parent, false);
        return new RebateOrderListVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RebateOrderListVH) {
            setItemTicket((RebateOrderListVH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void setItemTicket(RebateOrderListVH holder, int position) {
        RebateOrderEntity entity = mData.get(position);
        holder.tv_good_name.setText(entity.getGoods_name());
        holder.tv_good_price.setText(StringUtil.formatAmount(entity.getTotal_amount(), 2));
        holder.tv_order_no.setText("订单号: " + entity.getOrder_sn());
        holder.tv_time.setText(entity.getAdd_time());
        StringBuilder stringBuilder = new StringBuilder();
        if (mType == 1) {
            stringBuilder.append("折让:¥");
        } else {
            stringBuilder.append("预计最高收益:¥");
        }
        stringBuilder.append(StringUtil.formatAmount(entity.getExpect_price(), 2));
        holder.tv_anticipated_income.setText(stringBuilder.toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mType == 2) {
                    NavigationUtil.overlay(mContext, RebateOrderDetailActivity.class, entity.getRec_id());
                }
            }
        });
    }
}
