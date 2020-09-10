package com.qubuyer.business.shopcart.holder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qubuyer.R;
import com.qubuyer.customview.AddSubUtils;
import com.qubuyer.customview.ImageViewAutoLoad;

import androidx.recyclerview.widget.RecyclerView;

public class ShopCartGoodListVH extends RecyclerView.ViewHolder {
    public RelativeLayout rl_container;
    public FrameLayout fl_statu;
    public CheckBox cb_statu;
    public ImageViewAutoLoad iv_good_img;
    public TextView tv_good_title;
    public TextView tv_number_spec;
    public TextView tv_good_price;
    public AddSubUtils asu_count;
    public TextView btn_collect;
    public TextView btn_delete;

    public ShopCartGoodListVH(View itemView) {
        super(itemView);
        rl_container = itemView.findViewById(R.id.rl_container);
        fl_statu = itemView.findViewById(R.id.fl_statu);
        cb_statu = itemView.findViewById(R.id.cb_statu);
        iv_good_img = itemView.findViewById(R.id.iv_good_img);
        tv_good_title = itemView.findViewById(R.id.tv_good_title);
        tv_number_spec = itemView.findViewById(R.id.tv_number_spec);
        tv_good_price = itemView.findViewById(R.id.tv_good_price);
        asu_count = itemView.findViewById(R.id.asu_count);
        btn_collect = itemView.findViewById(R.id.btn_collect);
        btn_delete = itemView.findViewById(R.id.btn_delete);
    }
}
