package com.qubuyer.business.home.holder;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.qubuyer.R;
import com.qubuyer.customview.EnhanceTabLayout;

import androidx.recyclerview.widget.RecyclerView;

public class HomeGoodFootVH extends RecyclerView.ViewHolder {
    public LinearLayout ll_container;

    public HomeGoodFootVH(View itemView) {
        super(itemView);
        ll_container = itemView.findViewById(R.id.ll_container);
    }
}
