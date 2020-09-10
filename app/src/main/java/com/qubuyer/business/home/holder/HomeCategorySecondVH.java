package com.qubuyer.business.home.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qubuyer.R;
import com.qubuyer.customview.ImageViewAutoLoad;

import androidx.recyclerview.widget.RecyclerView;

public class HomeCategorySecondVH extends RecyclerView.ViewHolder {
    public LinearLayout ll_container;
    public ImageViewAutoLoad iv_img;
    public TextView tv_name;

    public HomeCategorySecondVH(View itemView) {
        super(itemView);
        ll_container = itemView.findViewById(R.id.ll_container);
        iv_img = itemView.findViewById(R.id.iv_img);
        tv_name = itemView.findViewById(R.id.tv_name);
    }
}
