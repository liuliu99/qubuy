package com.qubuyer.business.home.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qubuyer.R;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.business.home.activity.SecondGoodListActivity;
import com.qubuyer.business.home.holder.HomeDailyDiscountSecondVH;
import com.qubuyer.business.home.holder.HomeSuperReturnSecondVH;
import com.qubuyer.utils.NavigationUtil;

import java.util.List;

/**
 * @date 创建时间:2019/6/4
 * @author Susong
 * @description 首页每日折扣Adapter
 & @version
 */
public class HomeDailyDiscountAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<HomeGoodEntity> mData;

    public HomeDailyDiscountAdapter(Context context, List<HomeGoodEntity> data) {
        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_dailydiscount_second, parent, false);
        return new HomeDailyDiscountSecondVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HomeDailyDiscountSecondVH) {
            setCommonInfo((HomeDailyDiscountSecondVH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        if (null != mData) {
            return mData.size();
        }
        return 0;
    }

    private void setCommonInfo(HomeDailyDiscountSecondVH holder, int position) {
        HomeGoodEntity entity = mData.get(position);
        holder.iv_img.setUri(mContext, entity.getOriginal_img());
        holder.tv_name.setText(entity.getGoods_name());
        holder.tv_real_price.setText("¥" + entity.getDiff_price());
        holder.tv_shop_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        holder.tv_shop_price.setText("¥" + entity.getShop_price());
        holder.tv_rebate.setText("折让" + entity.getRestrore_price());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtil.overlay(mContext, SecondGoodListActivity.class, 6);
            }
        });
    }
}
