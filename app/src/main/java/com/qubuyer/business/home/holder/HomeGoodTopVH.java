package com.qubuyer.business.home.holder;

import android.view.View;
import android.widget.FrameLayout;

import com.qubuyer.R;
import com.qubuyer.customview.EnhanceTabLayout;

import androidx.recyclerview.widget.RecyclerView;

public class HomeGoodTopVH extends RecyclerView.ViewHolder {
    public EnhanceTabLayout etl_tab;

    public HomeGoodTopVH(View itemView) {
        super(itemView);
        etl_tab = itemView.findViewById(R.id.etl_tab);
    }
}
