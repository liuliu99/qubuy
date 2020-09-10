package com.qubuyer.business.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qubuyer.R;
import com.qubuyer.bean.order.OrderRefundReasonEntity;
import com.qubuyer.business.order.holder.OrderRefundReasonVH;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * @author Susong
 * @date 创建时间2019/3/31
 * @description 订单退款原因adapter
 * @version
 */
public class OrderRefundReasonAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<OrderRefundReasonEntity> mData;
    private OnRefundReasonListListener mListener;
    private RecyclerView mRecyclerView;
    private int mSelectedPos = -1;

    public OrderRefundReasonAdapter(Context context, RecyclerView recyclerView, OnRefundReasonListListener listener) {
        mContext = context;
        mRecyclerView = recyclerView;
        mListener = listener;
        mData = new ArrayList<>();
    }

    public void setData(List<OrderRefundReasonEntity> data) {
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).isSelected()) {
                    mSelectedPos = i;
                }
            }
            int previousSize = mData.size();
            mData.clear();
            notifyItemRangeRemoved(0, previousSize);
            mData.addAll(data);
            notifyItemRangeChanged(0, mData.size());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_refund_reason, parent, false);
        return new OrderRefundReasonVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OrderRefundReasonVH) {
            setCommonInfo((OrderRefundReasonVH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void setCommonInfo(OrderRefundReasonVH holder, int position) {
        OrderRefundReasonEntity entity = mData.get(position);
        holder.tv_title.setText(entity.getName());
        if (entity.isSelected()) {
            holder.cb_default.setChecked(true);
        } else {
            holder.cb_default.setChecked(false);
        }
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectedPos == -1) {
                    mSelectedPos = position;
                    mData.get(mSelectedPos).setSelected(true);
                    holder.cb_default.setChecked(true);
                } else {
                    if (mSelectedPos != position) {
                        OrderRefundReasonVH orderRefundReasonVH = (OrderRefundReasonVH) mRecyclerView.findViewHolderForLayoutPosition(mSelectedPos);
                        if (null != orderRefundReasonVH) {
                            orderRefundReasonVH.cb_default.setChecked(false);
                        } else {
                            notifyItemChanged(mSelectedPos);
                        }
                        mData.get(mSelectedPos).setSelected(false);
                        mSelectedPos = position;
                        mData.get(mSelectedPos).setSelected(true);
                        holder.cb_default.setChecked(true);
                    }
                }
                if (null != mListener) {
                    mListener.onRefundReasonClickListener(mData.get(mSelectedPos));
                }
            }
        };
        holder.cb_default.setOnClickListener(onClickListener);
        holder.itemView.setOnClickListener(onClickListener);
    }

    public interface OnRefundReasonListListener {
        void onRefundReasonClickListener(OrderRefundReasonEntity entity);
    }
}
