package com.qubuyer.business.home.holder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.qubuyer.R;
import com.qubuyer.customview.ImageViewAutoLoad;

public class HomeSuperReturnSecondVH extends RecyclerView.ViewHolder {
    public ImageViewAutoLoad iv_img;
    public TextView tv_name;
    public TextView tv_real_price;
    public TextView tv_shop_price;
    public TextView tv_rebate;

    public HomeSuperReturnSecondVH(View itemView) {
        super(itemView);
        iv_img = itemView.findViewById(R.id.iv_img);
        tv_name = itemView.findViewById(R.id.tv_name);
        tv_real_price = itemView.findViewById(R.id.tv_real_price);
        tv_shop_price = itemView.findViewById(R.id.tv_shop_price);
        tv_rebate = itemView.findViewById(R.id.tv_rebate);
    }
}
