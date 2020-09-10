package com.qubuyer.business.mine.holder;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.qubuyer.R;
import com.qubuyer.customview.ImageViewAutoLoad;

public class MineCommentVH extends RecyclerView.ViewHolder {
    public ImageViewAutoLoad iv_user_headimg;
    public TextView tv_user_name;
    public TextView tv_time;
    public TextView tv_content;
    public RecyclerView rv_image_list;
    public RelativeLayout rl_good_container;
    public ImageViewAutoLoad iv_img;
    public TextView tv_title;
    public TextView tv_good_price;
    public TextView tv_dis_price;

    public MineCommentVH(View itemView) {
        super(itemView);
        iv_user_headimg = itemView.findViewById(R.id.iv_user_headimg);
        tv_user_name = itemView.findViewById(R.id.tv_user_name);
        tv_time = itemView.findViewById(R.id.tv_time);
        tv_content = itemView.findViewById(R.id.tv_content);
        rv_image_list = itemView.findViewById(R.id.rv_image_list);

        rl_good_container = itemView.findViewById(R.id.rl_good_container);
        iv_img = itemView.findViewById(R.id.iv_img);
        tv_title = itemView.findViewById(R.id.tv_title);
        tv_good_price = itemView.findViewById(R.id.tv_good_price);
        tv_dis_price = itemView.findViewById(R.id.tv_dis_price);
    }
}
