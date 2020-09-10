package com.qubuyer.business.shopcart.holder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qubuyer.R;
import com.qubuyer.customview.AddSubUtils;
import com.qubuyer.customview.ImageViewAutoLoad;

import androidx.recyclerview.widget.RecyclerView;

public class ShopCartGoodLoseEfficacyListVH extends RecyclerView.ViewHolder {
    public RelativeLayout rl_lose_efficacy;
    public TextView tv_lose_efficacy_clear;
    public ImageViewAutoLoad iv_good_img;
    public TextView tv_good_title;
    public TextView tv_number_spec;
    public TextView tv_good_price;
    public TextView tv_see_similarity;

    public ShopCartGoodLoseEfficacyListVH(View itemView) {
        super(itemView);
        rl_lose_efficacy = itemView.findViewById(R.id.rl_lose_efficacy);
        tv_lose_efficacy_clear = itemView.findViewById(R.id.tv_lose_efficacy_clear);
        iv_good_img = itemView.findViewById(R.id.iv_good_img);
        tv_good_title = itemView.findViewById(R.id.tv_good_title);
        tv_number_spec = itemView.findViewById(R.id.tv_number_spec);
        tv_good_price = itemView.findViewById(R.id.tv_good_price);
        tv_see_similarity = itemView.findViewById(R.id.tv_see_similarity);
    }
}
