package com.qubuyer.business.good.holder;

import android.view.View;
import android.widget.TextView;

import com.qubuyer.R;
import com.qubuyer.customview.ImageViewAutoLoad;
import com.xingliuhua.xlhratingbar.XLHRatingBar;

import androidx.recyclerview.widget.RecyclerView;

public class GoodCommentVH extends RecyclerView.ViewHolder {
    public ImageViewAutoLoad iv_user_headimg;
    public TextView tv_user_name;
    public XLHRatingBar tv_rating_bar;
    public TextView tv_time;
    public TextView tv_content;
    public RecyclerView rv_image_list;

    public GoodCommentVH(View itemView) {
        super(itemView);
        iv_user_headimg = itemView.findViewById(R.id.iv_user_headimg);
        tv_user_name = itemView.findViewById(R.id.tv_user_name);
        tv_rating_bar = itemView.findViewById(R.id.tv_rating_bar);
        tv_time = itemView.findViewById(R.id.tv_time);
        tv_content = itemView.findViewById(R.id.tv_content);
        rv_image_list = itemView.findViewById(R.id.rv_image_list);
    }
}
