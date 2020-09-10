package com.qubuyer.business.good.holder;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qubuyer.R;
import com.qubuyer.customview.ImageViewAutoLoad;

import androidx.recyclerview.widget.RecyclerView;

public class SOGoodListVH extends RecyclerView.ViewHolder {
    public ImageViewAutoLoad iv_good_img;
    public TextView tv_good_title;
    public TextView tv_number_spec;
    public TextView tv_good_price;
    public TextView tv_good_count;

    public SOGoodListVH(View itemView) {
        super(itemView);
        iv_good_img = itemView.findViewById(R.id.iv_good_img);
        tv_good_title = itemView.findViewById(R.id.tv_good_title);
        tv_number_spec = itemView.findViewById(R.id.tv_number_spec);
        tv_good_price = itemView.findViewById(R.id.tv_good_price);
        tv_good_count = itemView.findViewById(R.id.tv_good_count);
    }
}
