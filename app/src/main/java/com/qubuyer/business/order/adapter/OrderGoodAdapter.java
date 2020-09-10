package com.qubuyer.business.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qubuyer.R;
import com.qubuyer.bean.order.OrderEntity;
import com.qubuyer.business.order.holder.OrderListGoodListVH;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Susong
 * @date 创建时间2019/3/29
 * @description 订单列表页商品列表adapter
 */
public class OrderGoodAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<OrderEntity.SplitBean.OrderGoodBean> mData;
    private OnGoodListGoodListListener mListener;

    public OrderGoodAdapter(Context context, List<OrderEntity.SplitBean.OrderGoodBean> data, OnGoodListGoodListListener listener) {
        mContext = context;
        mData = data;
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_order_list_good_list, parent, false);
        return new OrderListGoodListVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OrderListGoodListVH) {
            setCommonInfo((OrderListGoodListVH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        if (null != mData) {
            return mData.size();
        }
        return 0;
    }

    private void setCommonInfo(OrderListGoodListVH holder, int position) {
        OrderEntity.SplitBean.OrderGoodBean entity = mData.get(position);
        if (null != entity.getGoods()) {
            holder.iv_img.setUri(mContext, entity.getGoods().getOriginal_img());
        } else {
            holder.iv_img.setDrawableId(mContext, R.mipmap.ic_logo);
        }
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    mListener.onGoodListGoodListClick();
//                }
//            }
//        });
    }

    public interface OnGoodListGoodListListener{
        void onGoodListGoodListClick();
    }
}
