package com.qubuyer.business.home.holder;

import android.view.View;
import android.widget.LinearLayout;

import com.qubuyer.R;
import com.qubuyer.customview.ViewPagerWrapContentHeight;

import androidx.recyclerview.widget.RecyclerView;

public class HomeCategoryVH extends RecyclerView.ViewHolder {
    public LinearLayout ll_container;
    public ViewPagerWrapContentHeight vp_category;
    public LinearLayout ll_indicator;

    public HomeCategoryVH(View itemView) {
        super(itemView);
        ll_container = itemView.findViewById(R.id.ll_container);
        vp_category = itemView.findViewById(R.id.vp_category);
        ll_indicator = itemView.findViewById(R.id.ll_indicator);
    }
}
