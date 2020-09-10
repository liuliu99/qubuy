package com.qubuyer.business.mine.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qubuyer.R;
import com.qubuyer.bean.mine.RebateOrderEntity;
import com.qubuyer.business.good.activity.GoodDetailActivity;
import com.qubuyer.business.mine.holder.RebateOrderDetailGoodListVH;
import com.qubuyer.utils.NavigationUtil;
import com.qubuyer.utils.StringUtil;

import java.util.List;

/**
 * @date 创建时间:2019/4/8
 * @author Susong
 * @description 折让订单详情商品列表adapter
 & @version
 */
public class RebateOrderDetailGoodListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<RebateOrderEntity> mData;

    public RebateOrderDetailGoodListAdapter(Context context, List<RebateOrderEntity> data) {
        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rebate_order_detail_good_list, parent, false);
        return new RebateOrderDetailGoodListVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RebateOrderDetailGoodListVH) {
            setCommonInfo((RebateOrderDetailGoodListVH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        if (null != mData) {
            return mData.size();
        }
        return 0;
    }

    private void setCommonInfo(RebateOrderDetailGoodListVH holder, int position) {
        RebateOrderEntity entity = mData.get(position);
        holder.iv_good_img.setUri(mContext, entity.getOriginal_img_full());
        holder.tv_good_name.setText(entity.getGoods_name());
        holder.tv_good_spec.setText(!TextUtils.isEmpty(entity.getSpec_key_name()) ? entity.getSpec_key_name() : "");
        holder.tv_good_price.setText("¥" + StringUtil.formatAmount(entity.getMember_goods_price(), 2));
        holder.tv_good_num.setText("x" + entity.getGoods_num());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtil.overlay(mContext, GoodDetailActivity.class, entity.getGoods_id());
            }
        });
    }
}
