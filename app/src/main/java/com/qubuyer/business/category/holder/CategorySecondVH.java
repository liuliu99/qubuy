package com.qubuyer.business.category.holder;

import android.view.View;
import android.widget.TextView;

import com.qubuyer.R;
import com.qubuyer.customview.ImageViewAutoLoad;

import androidx.recyclerview.widget.RecyclerView;

public class CategorySecondVH extends RecyclerView.ViewHolder {
    public ImageViewAutoLoad iv_img;
    public TextView tv_name;

    public CategorySecondVH(View itemView) {
        super(itemView);
        iv_img = itemView.findViewById(R.id.iv_img);
        tv_name = itemView.findViewById(R.id.tv_name);
    }
}
