package com.qubuyer.business.mine.holder;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.qubuyer.R;
import com.qubuyer.customview.ImageViewAutoLoad;

public class MineBrowseFootprintVH extends RecyclerView.ViewHolder {
    public SwipeMenuLayout sml_container;
    public RelativeLayout rl_container;
    public ImageViewAutoLoad iv_img;
    public TextView tv_title;
    public TextView tv_good_price;
    public TextView tv_dis_price;
    public ImageViewAutoLoad iv_add_shop_cart;
    public Button btn_delete;

    public MineBrowseFootprintVH(View itemView) {
        super(itemView);
        sml_container = itemView.findViewById(R.id.sml_container);
        rl_container = itemView.findViewById(R.id.rl_container);
        iv_img = itemView.findViewById(R.id.iv_img);
        tv_title = itemView.findViewById(R.id.tv_title);
        tv_good_price = itemView.findViewById(R.id.tv_good_price);
        tv_dis_price = itemView.findViewById(R.id.tv_dis_price);
        iv_add_shop_cart = itemView.findViewById(R.id.iv_add_shop_cart);
        btn_delete = itemView.findViewById(R.id.btn_delete);
    }
}
