package com.qubuyer.business.mine.holder;

import android.view.View;
import android.widget.TextView;

import com.qubuyer.R;
import com.qubuyer.customview.ImageViewAutoLoad;

import androidx.recyclerview.widget.RecyclerView;

public class RebateOrderDetailGoodListVH extends RecyclerView.ViewHolder {
    public ImageViewAutoLoad iv_good_img;
    public TextView tv_good_name;
    public TextView tv_good_spec;
    public TextView tv_good_price;
    public TextView tv_good_num;

    public RebateOrderDetailGoodListVH(View itemView) {
        super(itemView);
        iv_good_img = itemView.findViewById(R.id.iv_good_img);
        tv_good_name = itemView.findViewById(R.id.tv_good_name);
        tv_good_spec = itemView.findViewById(R.id.tv_good_spec);
        tv_good_price = itemView.findViewById(R.id.tv_good_price);
        tv_good_num = itemView.findViewById(R.id.tv_good_num);
    }
}
