package com.qubuyer.business.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qubuyer.R;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.business.good.activity.GoodDetailActivity;
import com.qubuyer.business.home.holder.HomeGoodInfoVH;
import com.qubuyer.utils.NavigationUtil;
import com.qubuyer.utils.StringUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


/**
 * @author Susong
 * @date 创建时间2019/3/20
 * @description 限时购adapter
 * @version
 */
public class LimitBuyAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<HomeGoodEntity> mData;

    public LimitBuyAdapter(Context context, List<HomeGoodEntity> list) {
        mContext = context;
        mData = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_good_info, parent, false);
        return new HomeGoodInfoVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HomeGoodInfoVH) {
            setCommonInfo((HomeGoodInfoVH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void setCommonInfo(HomeGoodInfoVH holder, int position) {
        if (null == mData || mData.isEmpty()) return;
        HomeGoodEntity entity = mData.get(position);
        holder.iv_photo.setUri(mContext, entity.getOriginal_img());
//        holder.iv_photo.setDrawableId(mContext, R.mipmap.ic_logo);
        holder.tv_title.setText(entity.getGoods_name());
        holder.tv_price.setText("¥" + entity.getShop_price());
        holder.tv_rebate.setText("折让" + entity.getRestrore_price());
        holder.tv_real_price.setText("¥" + entity.getDiff_price());
        if (entity.getStore_count() > 0) {
            holder.iv_sell_out.setVisibility(View.GONE);
            holder.ll_real_price.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_home_good_real_price_bg));
            holder.tv_real_price_title.setTextColor(Color.parseColor("#FF681D"));
            holder.tv_real_price.setTextColor(Color.parseColor("#FF681D"));
            holder.ll_now_buy.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_home_good_now_buy_bg));
            holder.tv_now_buy.setText("马上抢");
            holder.ll_now_buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavigationUtil.overlay(mContext, GoodDetailActivity.class, entity.getGoods_id());
                }
            });
        } else {
            holder.iv_sell_out.setVisibility(View.VISIBLE);
            holder.ll_real_price.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_home_good_real_price_sell_out_bg));
            holder.tv_real_price_title.setTextColor(Color.parseColor("#999999"));
            holder.tv_real_price.setTextColor(Color.parseColor("#999999"));
            holder.ll_now_buy.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_home_good_now_buy_sell_out_bg));
            holder.tv_now_buy.setText("到货通知");
            holder.ll_now_buy.setOnClickListener(null);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtil.overlay(mContext, GoodDetailActivity.class, entity.getGoods_id());
            }
        });
    }
}
