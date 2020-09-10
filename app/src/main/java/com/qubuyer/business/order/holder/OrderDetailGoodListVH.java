package com.qubuyer.business.order.holder;

import android.view.View;
import android.widget.TextView;

import com.qubuyer.R;
import com.qubuyer.customview.ImageViewAutoLoad;

import androidx.recyclerview.widget.RecyclerView;

public class OrderDetailGoodListVH extends RecyclerView.ViewHolder {
    public ImageViewAutoLoad iv_good_img;
    public TextView tv_good_name;
    public TextView tv_good_spec;
    public TextView tv_good_price;
    public TextView tv_refund;
    public TextView tv_add_shop_cart;
    public TextView tv_good_num;

    public OrderDetailGoodListVH(View itemView) {
        super(itemView);
        iv_good_img = itemView.findViewById(R.id.iv_good_img);
        tv_good_name = itemView.findViewById(R.id.tv_good_name);
        tv_good_spec = itemView.findViewById(R.id.tv_good_spec);
        tv_good_price = itemView.findViewById(R.id.tv_good_price);
        tv_refund = itemView.findViewById(R.id.tv_refund);
        tv_add_shop_cart = itemView.findViewById(R.id.tv_add_shop_cart);
        tv_good_num = itemView.findViewById(R.id.tv_good_num);
    }
}
