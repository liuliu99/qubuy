package com.qubuyer.business.home.holder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.qubuyer.R;
import com.qubuyer.customview.ImageViewAutoLoad;

public class HomeSpecialGoodInfoVH extends RecyclerView.ViewHolder {
    public ImageViewAutoLoad iv_photo;
    public TextView tv_price;
    public TextView tv_rebate;

    public HomeSpecialGoodInfoVH(View itemView) {
        super(itemView);
        iv_photo = itemView.findViewById(R.id.iv_photo);
        tv_price = itemView.findViewById(R.id.tv_price);
        tv_rebate = itemView.findViewById(R.id.tv_rebate);
    }
}
