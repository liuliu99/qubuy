package com.qubuyer.business.category.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qubuyer.R;
import com.qubuyer.bean.category.CategoryFirstEntity;
import com.qubuyer.bean.category.CategorySecondEntity;
import com.qubuyer.business.category.holder.CategorySecondVH;
import com.qubuyer.constant.NetConstant;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Susong
 * @date 创建时间2019/3/13
 * @description 二级分类Adapter
 * @version
 */
public class CategorySecondAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<CategorySecondEntity> mData;
    private OnSecondCategoryListener mListener;

    public CategorySecondAdapter(Context context, List<CategorySecondEntity> data, OnSecondCategoryListener listener) {
        mContext = context;
        mData = data;
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_category_second, parent, false);
        return new CategorySecondVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CategorySecondVH) {
            setCommonInfo((CategorySecondVH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        if (null != mData) {
            return mData.size();
        }
        return 0;
    }

    private void setCommonInfo(CategorySecondVH holder, int position) {
        CategorySecondEntity entity = mData.get(position);
        holder.iv_img.setUri(mContext, entity.getImage());
        holder.tv_name.setText(entity.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onSecondCategoryClick(entity);
                }
            }
        });
    }

    public interface OnSecondCategoryListener {
        void onSecondCategoryClick(CategorySecondEntity entity);
    }
}
