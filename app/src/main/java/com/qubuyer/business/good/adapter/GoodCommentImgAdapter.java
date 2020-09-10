package com.qubuyer.business.good.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qubuyer.R;
import com.qubuyer.business.good.holder.GoodCommentImgVH;
import com.qubuyer.constant.AppConstant;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Susong
 * @date 创建时间:2019/3/25
 * @description 商品评论图片adapter
 * & @version
 */
public class GoodCommentImgAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<String> mData;
    private OnCommentImgAdapterListener mListener;

    public GoodCommentImgAdapter(Context context, List<String> data, OnCommentImgAdapterListener listener) {
        mContext = context;
        mData = data;
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_good_comment_img, parent, false);
        return new GoodCommentImgVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GoodCommentImgVH) {
            setCommonInfo((GoodCommentImgVH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        if (null != mData && !mData.isEmpty()) {
            return mData.size();
        }
        return 0;
    }

    private void setCommonInfo(GoodCommentImgVH holder, int position) {
        String entity = mData.get(position);
        holder.iv_img.setUri(mContext, entity);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onImgClick(position);
                }
            }
        });
    }

    public interface OnCommentImgAdapterListener{
        void onImgClick(int position);
    }
}
