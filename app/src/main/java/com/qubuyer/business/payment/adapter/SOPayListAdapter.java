package com.qubuyer.business.payment.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.qubuyer.R;
import com.qubuyer.bean.payment.PayListEntity;
import com.qubuyer.business.payment.holder.SOPayListVH;
import com.qubuyer.business.payment.view.SOPayListView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * @date 创建时间:2019/1/22
 * @author Susong
 * @description 提交订单支付列表adapter
 & @version
 */
public class SOPayListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<PayListEntity> mData;
    private SOPayListView.OnSOPayListListener mListener;
    private RecyclerView mRecyclerView;
    private int mSelectedPos = -1;

    public SOPayListAdapter(Context context, RecyclerView recyclerView, SOPayListView.OnSOPayListListener listener) {
        mContext = context;
        mRecyclerView = recyclerView;
        mListener = listener;
        mData = new ArrayList<>();
    }

    public void setData(List<PayListEntity> data) {
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_so_pay_list, parent, false);
        return new SOPayListVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SOPayListVH) {
            setCommonInfo((SOPayListVH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void cancelAllCheck() {
        SOPayListVH soPayListVH = (SOPayListVH) mRecyclerView.findViewHolderForLayoutPosition(mSelectedPos);
        if (null != soPayListVH) {
            soPayListVH.iv_pay_select.setChecked(false);
        } else {
            notifyItemChanged(mSelectedPos);
        }
        mData.get(mSelectedPos).setSelected(false);
        mSelectedPos = -1;
    }

    private void setCommonInfo(SOPayListVH holder, int position) {
        PayListEntity entity = mData.get(position);
        if (!TextUtils.isEmpty(entity.getTypeImg())) {
            holder.iv_pay_type.setUri(mContext, entity.getTypeImg());
        } else {
            switch (entity.getType()) {
                case 1:
                    holder.iv_pay_type.setDrawableId(mContext, R.drawable.icon_so_pay_wechat);
                    break;
                case 2:
                    holder.iv_pay_type.setDrawableId(mContext, R.drawable.icon_so_pay_alipay);
                    break;
                case 3:
                    holder.iv_pay_type.setDrawableId(mContext, R.drawable.icon_so_pay_balance);
                    break;
            }
        }
        holder.tv_title.setText(entity.getTypeContent());
        if (entity.isSelected()) {
            holder.iv_pay_select.setChecked(true);
        } else {
            holder.iv_pay_select.setChecked(false);
        }
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectedPos == -1) {
                    mSelectedPos = position;
                    mData.get(mSelectedPos).setSelected(true);
                    holder.iv_pay_select.setChecked(true);
                } else {
                    if (mSelectedPos != position) {
                        SOPayListVH soPayListVH = (SOPayListVH) mRecyclerView.findViewHolderForLayoutPosition(mSelectedPos);
                        if (null != soPayListVH) {
                            soPayListVH.iv_pay_select.setChecked(false);
                        } else {
                            notifyItemChanged(mSelectedPos);
                        }
                        mData.get(mSelectedPos).setSelected(false);
                        mSelectedPos = position;
                        mData.get(mSelectedPos).setSelected(true);
                        holder.iv_pay_select.setChecked(true);
                    }
                }
                if (null != mListener) {
                    mListener.onSOPayListClickListener(mData.get(mSelectedPos));
                }
            }
        };
        holder.iv_pay_select.setOnClickListener(onClickListener);
        holder.itemView.setOnClickListener(onClickListener);
    }
}
