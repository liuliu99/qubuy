package com.qubuyer.business.category.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qubuyer.R;
import com.qubuyer.bean.category.CategoryFirstEntity;
import com.qubuyer.business.category.holder.CategoryFirstVH;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Susong
 * @date 创建时间2019/3/11
 * @description 分类一级adapter
 * @version
 */
public class CategoryFirstAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private RecyclerView mRecyclerView;
    private List<CategoryFirstEntity> mData;
    private int mSelectedPos = -1;
    private OnCategoryFirstListener mListener;

    public CategoryFirstAdapter(Context context, RecyclerView recyclerView, OnCategoryFirstListener listener) {
        mContext = context;
        mRecyclerView = recyclerView;
        mListener = listener;
        mData = new ArrayList<>(0);
    }

    public void setData(List<CategoryFirstEntity> data) {
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_category_first, parent, false);
        return new CategoryFirstVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CategoryFirstVH) {
            setCommonInfo((CategoryFirstVH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void setCommonInfo(CategoryFirstVH holder, int position) {
        CategoryFirstEntity entity = mData.get(position);
        holder.tv_title.setText(entity.getName());
        if (entity.isSelected()) {
            holder.v_line.setVisibility(View.VISIBLE);
            holder.tv_title.setTextColor(Color.parseColor("#FF681D"));
        } else {
            holder.v_line.setVisibility(View.GONE);
            holder.tv_title.setTextColor(Color.parseColor("#333333"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectedPos != position) {
                    CategoryFirstVH categoryFirstVH = (CategoryFirstVH) mRecyclerView.findViewHolderForLayoutPosition(mSelectedPos);
                    if (null != categoryFirstVH) {
                        categoryFirstVH.v_line.setVisibility(View.GONE);
                        categoryFirstVH.tv_title.setTextColor(Color.parseColor("#333333"));
                    } else {
                        notifyItemChanged(mSelectedPos);
                    }
                    mData.get(mSelectedPos).setSelected(false);
                    mSelectedPos = position;
                    mData.get(mSelectedPos).setSelected(true);
                    holder.v_line.setVisibility(View.VISIBLE);
                    holder.tv_title.setTextColor(Color.parseColor("#FF681D"));
                    if (null != mListener) {
                        mListener.onCategoryFirstClick(mData.get(mSelectedPos), position);
                    }
                }
            }
        });
    }

    public interface OnCategoryFirstListener {
        void onCategoryFirstClick(CategoryFirstEntity entity, int postion);
    }
}
