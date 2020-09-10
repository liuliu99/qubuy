package com.qubuyer.business.home.holder;

import android.view.View;
import android.widget.FrameLayout;

import com.qubuyer.R;
import com.youth.banner.Banner;

import androidx.recyclerview.widget.RecyclerView;

public class HomeBannerVH extends RecyclerView.ViewHolder {
    public FrameLayout fl_container;
    public Banner banner;

    public HomeBannerVH(View itemView) {
        super(itemView);
        fl_container = itemView.findViewById(R.id.fl_container);
        banner = itemView.findViewById(R.id.banner);
    }
}
