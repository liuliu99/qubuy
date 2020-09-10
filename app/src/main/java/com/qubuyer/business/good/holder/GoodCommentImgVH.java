package com.qubuyer.business.good.holder;

import android.view.View;

import com.qubuyer.R;
import com.qubuyer.customview.ImageViewAutoLoad;

import androidx.recyclerview.widget.RecyclerView;

public class GoodCommentImgVH extends RecyclerView.ViewHolder {
    public ImageViewAutoLoad iv_img;

    public GoodCommentImgVH(View itemView) {
        super(itemView);
        iv_img = itemView.findViewById(R.id.iv_img);
    }
}
