package com.qubuyer.business.order.holder;

import android.view.View;
import android.widget.TextView;

import com.qubuyer.R;
import com.qubuyer.customview.ImageViewAutoLoad;

import androidx.recyclerview.widget.RecyclerView;

public class OrderListGoodListVH extends RecyclerView.ViewHolder {
    public ImageViewAutoLoad iv_img;

    public OrderListGoodListVH(View itemView) {
        super(itemView);
        iv_img = itemView.findViewById(R.id.iv_img);
    }
}
