package com.qubuyer.business.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ConvertUtils;
import com.qubuyer.R;
import com.qubuyer.bean.category.CategoryFirstEntity;
import com.qubuyer.bean.category.CategorySecondEntity;
import com.qubuyer.bean.category.SecondCategoryGoodEntity;
import com.qubuyer.bean.home.HomeCategoryEntity;
import com.qubuyer.business.category.activity.SecondCategoryActivity;
import com.qubuyer.business.home.holder.HomeCategorySecondVH;
import com.qubuyer.utils.NavigationUtil;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Susong
 * @date 创建时间2019/3/9
 * @description 首页分类Adapter
 */
public class HomeCategoryAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<HomeCategoryEntity> mData;

    public HomeCategoryAdapter(Context context, List<HomeCategoryEntity> data) {
        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_category_second, parent, false);
        return new HomeCategorySecondVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HomeCategorySecondVH) {
            setCommonInfo((HomeCategorySecondVH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        if (null != mData) {
            return mData.size();
        }
        return 0;
    }

    private void setCommonInfo(HomeCategorySecondVH holder, int position) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        if (position == 0) {
//            layoutParams.setMargins(ConvertUtils.dp2px(15), ConvertUtils.dp2px(10), ConvertUtils.dp2px(5), ConvertUtils.dp2px(10));
//        } else if (position == mData.size() - 1) {
//            layoutParams.setMargins(ConvertUtils.dp2px(5), ConvertUtils.dp2px(10), ConvertUtils.dp2px(15), ConvertUtils.dp2px(10));
//        } else {
//            layoutParams.setMargins(ConvertUtils.dp2px(5), ConvertUtils.dp2px(10), ConvertUtils.dp2px(5), ConvertUtils.dp2px(10));
//        }
//        holder.ll_container.setLayoutParams(layoutParams);
        HomeCategoryEntity entity = mData.get(position);
        holder.iv_img.setUri(mContext, entity.getIco());
        holder.tv_name.setText(entity.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap map = new HashMap();
                CategoryFirstEntity categoryFirstEntity = new CategoryFirstEntity();
                categoryFirstEntity.setId(entity.getId());
                categoryFirstEntity.setName(entity.getName());
                map.put("first_category_entity", categoryFirstEntity);
                CategorySecondEntity secondCategoryGoodEntity = new CategorySecondEntity();
                secondCategoryGoodEntity.setId(entity.getId());
                map.put("second_category_entity", secondCategoryGoodEntity);
                NavigationUtil.overlay(mContext, SecondCategoryActivity.class, map);
            }
        });
    }
}
