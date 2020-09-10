package com.qubuyer.business.home.holder;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qubuyer.R;
import com.qubuyer.customview.ImageViewAutoLoad;

import androidx.recyclerview.widget.RecyclerView;

public class HomeGoodInfoVH extends RecyclerView.ViewHolder {
    public RelativeLayout fl_container;
    public ImageViewAutoLoad iv_photo;
    public TextView tv_title;
    public TextView tv_price;
    public TextView tv_rebate;
    public LinearLayout ll_real_price;
    public TextView tv_real_price_title;
    public TextView tv_real_price;
    public LinearLayout ll_now_buy;
    public TextView tv_now_buy;
    public ImageViewAutoLoad iv_sell_out;

    public HomeGoodInfoVH(View itemView) {
        super(itemView);
        fl_container = itemView.findViewById(R.id.fl_container);
        iv_photo = itemView.findViewById(R.id.iv_photo);
        tv_title = itemView.findViewById(R.id.tv_title);
        tv_price = itemView.findViewById(R.id.tv_price);
        tv_rebate = itemView.findViewById(R.id.tv_rebate);
        ll_real_price = itemView.findViewById(R.id.ll_real_price);
        tv_real_price_title = itemView.findViewById(R.id.tv_real_price_title);
        tv_real_price = itemView.findViewById(R.id.tv_real_price);
        ll_now_buy = itemView.findViewById(R.id.ll_now_buy);
        tv_now_buy = itemView.findViewById(R.id.tv_now_buy);
        iv_sell_out = itemView.findViewById(R.id.iv_sell_out);
    }
}
