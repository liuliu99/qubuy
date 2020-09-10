package com.qubuyer.business.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qubuyer.R;
import com.qubuyer.bean.mine.SaleAmountEntity;
import com.qubuyer.business.mine.holder.SaleAmountListVH;
import com.qubuyer.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * @author Susong
 * @date 创建时间2019/4/3
 * @description 销售总额adapter
 */
public class SaleAmountAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<SaleAmountEntity.ListBean> mData;
    private int mType;

    public SaleAmountAdapter(Context context, int type) {
        mContext = context;
        mType = type;
        mData = new ArrayList<>();
    }

    public void setData(List<SaleAmountEntity.ListBean> data) {
        if (data != null) {
            int previousSize = mData.size();
            mData.clear();
            notifyItemRangeRemoved(0, previousSize);
            mData.addAll(data);
            notifyItemRangeInserted(0, mData.size());
        }
    }

    public List<SaleAmountEntity.ListBean> getData() {
        return mData;
    }

    public void addAll(List<SaleAmountEntity.ListBean> contents) {
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_sale_amount_list, parent, false);
        return new SaleAmountListVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SaleAmountListVH) {
            setItemTicket((SaleAmountListVH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void setItemTicket(SaleAmountListVH holder, int position) {
        SaleAmountEntity.ListBean entity = mData.get(position);
        holder.tv_good_name.setText(null != entity.getOrder() && null != entity.getOrder().getOrder_goods() && !entity.getOrder().getOrder_goods().isEmpty() ? entity.getOrder().getOrder_goods().get(0).getGoods_name() : "");
        holder.tv_good_price.setText(null != entity.getOrder() && null != entity.getOrder().getOrder_goods() && !entity.getOrder().getOrder_goods().isEmpty() ? StringUtil.formatAmount(entity.getOrder().getOrder_goods().get(0).getGoods_price(), 2) : "");
        holder.tv_time.setText("");
        StringBuilder stringBuilder = new StringBuilder();
        if (mType == 1) {
            stringBuilder.append("收益:¥");
        } else {
            stringBuilder.append("待结算,预计收益:¥");
        }
        stringBuilder.append(StringUtil.formatAmount(entity.getDistribut_money(), 2));
        holder.tv_anticipated_income.setText(stringBuilder.toString());
    }
}
