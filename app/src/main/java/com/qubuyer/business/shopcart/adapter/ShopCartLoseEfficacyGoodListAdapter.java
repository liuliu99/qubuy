package com.qubuyer.business.shopcart.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ConvertUtils;
import com.qubuyer.R;
import com.qubuyer.bean.shopcart.ShopCartGoodEntity;
import com.qubuyer.business.shopcart.holder.ShopCartGoodListVH;
import com.qubuyer.business.shopcart.holder.ShopCartGoodLoseEfficacyListVH;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * @author Susong
 * @date 创建时间2019/3/21
 * @description 购物车失效商品列表adapter
 */
public class ShopCartLoseEfficacyGoodListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<ShopCartGoodEntity> mData;
    private ShopCartLoseEfficacyOperationListener mListener;

    public ShopCartLoseEfficacyGoodListAdapter(Context context, ShopCartLoseEfficacyOperationListener listener) {
        mContext = context;
        mData = new ArrayList<>();
        mListener = listener;
    }

    public void setData(List<ShopCartGoodEntity> data) {
        if (data != null && !data.isEmpty()) {
            int previousSize = mData.size();
            mData.clear();
            notifyItemRangeRemoved(0, previousSize);
            mData.addAll(data);
            notifyItemRangeInserted(0, mData.size());
        }
    }

    public void addAll(List<ShopCartGoodEntity> contents) {
        if (contents != null) {
            int size = mData.size();
            this.mData.addAll(contents);
            notifyItemRangeInserted(size, contents.size());
            notifyItemRangeChanged(size, contents.size());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_shop_cart_lose_efficacy_good_list, parent, false);
        return new ShopCartGoodLoseEfficacyListVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ShopCartGoodListVH) {
            setCommonInfo((ShopCartGoodLoseEfficacyListVH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void setCommonInfo(ShopCartGoodLoseEfficacyListVH holder, int position) {
        ShopCartGoodEntity entity = mData.get(position);
        if (position == 0) {
            holder.rl_lose_efficacy.setVisibility(View.VISIBLE);
        } else {
            holder.rl_lose_efficacy.setVisibility(View.GONE);
        }
        holder.iv_good_img.setDrawableId(mContext, R.mipmap.ic_logo);
        holder.tv_good_title.setText(entity.getGoods_name());
        holder.tv_number_spec.setText("数量: " + entity.getGoods_num() + " 规格: " + entity.getSpec_key_name());
        holder.tv_good_price.setText(changeGoodPriceText("¥" + entity.getGoods_price()));
        holder.tv_lose_efficacy_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onClearAllGood();
                }
            }
        });
        holder.tv_see_similarity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private SpannableStringBuilder changeGoodPriceText(String value) {
        SpannableStringBuilder sp1 = new SpannableStringBuilder();
        sp1.append(value);

        sp1.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), 0, value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp1.setSpan(new AbsoluteSizeSpan(ConvertUtils.dp2px(11)), 0, value.indexOf("¥") + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp1.setSpan(new AbsoluteSizeSpan(ConvertUtils.dp2px(14)), value.indexOf("¥") + 1, value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp1;
    }

    public interface ShopCartLoseEfficacyOperationListener {
        void onClearAllGood();
    }
}
