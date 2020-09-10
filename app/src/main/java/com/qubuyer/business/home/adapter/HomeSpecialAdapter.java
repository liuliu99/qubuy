package com.qubuyer.business.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qubuyer.R;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.business.home.holder.HomeSpecialGoodInfoVH;

import java.util.List;


/**
 * @author Susong
 * @date 创建时间:2019/6/4
 * @description 首页推荐adapter
 * & @version
 */
public class HomeSpecialAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<HomeGoodEntity> mData;
    private OnHomeSpecialListener mListener;

    public HomeSpecialAdapter(Context context, List<HomeGoodEntity> list, OnHomeSpecialListener listener) {
        mContext = context;
        mData = list;
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_special_good_info, parent, false);
        return new HomeSpecialGoodInfoVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HomeSpecialGoodInfoVH) {
            setCommonInfo((HomeSpecialGoodInfoVH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        if (null != mData) {
            return mData.size();
        }
        return 0;
    }

    private void setCommonInfo(HomeSpecialGoodInfoVH holder, int position) {
        if (null == mData || mData.isEmpty()) return;
        HomeGoodEntity entity = mData.get(position);
        holder.iv_photo.setUri(mContext, entity.getOriginal_img());
//        holder.iv_photo.setDrawableId(mContext, R.mipmap.ic_logo);
        holder.tv_price.setText("¥" + entity.getShop_price());
        holder.tv_rebate.setText("折让" + entity.getRestrore_price());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                NavigationUtil.overlay(mContext, GoodDetailActivity.class, entity.getGoods_id());
                if (null != mListener) {
                    mListener.onClickListener();
                }
            }
        });
    }

    public interface OnHomeSpecialListener {
        void onClickListener();
    }
}
