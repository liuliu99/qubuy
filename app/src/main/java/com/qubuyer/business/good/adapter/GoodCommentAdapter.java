package com.qubuyer.business.good.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qubuyer.R;
import com.qubuyer.bean.good.GoodCommentEntity;
import com.qubuyer.business.good.holder.GoodCommentVH;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.constant.NetConstant;
import com.qubuyer.customview.GoodCommentItemDecoration;
import com.qubuyer.business.good.view.GoodDetailCommentRatingView;
import com.qubuyer.utils.ImageUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Susong
 * @date 创建时间2019/3/25
 * @description 商品评论列表adapter
 * @version
 */
public class GoodCommentAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<GoodCommentEntity> mData;
    private OnCommentAdapterListener mListener;

    public GoodCommentAdapter(Context context, OnCommentAdapterListener listener) {
        mContext = context;
        mListener = listener;
        mData = new ArrayList<>(0);
    }

    public void setData(List<GoodCommentEntity> data) {
        if (data != null) {
            int previousSize = mData.size();
            mData.clear();
            notifyItemRangeRemoved(0, previousSize);
            mData.addAll(data);
            notifyItemRangeChanged(0, mData.size());
        }
    }

    public List<GoodCommentEntity> getData() {
        return mData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_good_comment, parent, false);
        return new GoodCommentVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GoodCommentVH) {
            setCommonInfo((GoodCommentVH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void setCommonInfo(GoodCommentVH holder, int position) {
        GoodCommentEntity entity = mData.get(position);
        ImageUtil.loadCircleImage(mContext, entity.getHead_img() + AppConstant.PICTURE_SUFFIX, holder.iv_user_headimg);
//        holder.iv_user_headimg.setDrawableIdRoundCircleImage(mContext, R.mipmap.ic_logo, 0);
        holder.tv_user_name.setText(entity.getUsername());
        holder.tv_rating_bar.setRatingViewClassName(GoodDetailCommentRatingView.class.getName());
        holder.tv_rating_bar.setNumStars(5);
        holder.tv_rating_bar.setRating(entity.getGoods_rank());
        holder.tv_rating_bar.setEnabled(false);
        holder.tv_time.setText(entity.getAdd_time_format());
        holder.tv_content.setText(entity.getContent());
        holder.rv_image_list.setLayoutManager(new GridLayoutManager(mContext, 4));
        holder.rv_image_list.addItemDecoration(new GoodCommentItemDecoration());
        holder.rv_image_list.setAdapter(new GoodCommentImgAdapter(mContext, entity.getImg_full(), new GoodCommentImgAdapter.OnCommentImgAdapterListener() {
            @Override
            public void onImgClick(int position1) {
                if (null != mListener) {
                    mListener.onImgClick(position, position1);
                }
            }
        }));
    }

    public interface OnCommentAdapterListener{
        void onImgClick(int firstPosition, int secondPosition);
    }
}
