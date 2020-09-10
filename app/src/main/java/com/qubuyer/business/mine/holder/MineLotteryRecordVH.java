package com.qubuyer.business.mine.holder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.qubuyer.R;
import com.qubuyer.customview.ImageViewAutoLoad;

public class MineLotteryRecordVH extends RecyclerView.ViewHolder {
    public ImageViewAutoLoad iv_image;
    public TextView tv_content;
    public TextView tv_get_lottery;

    public MineLotteryRecordVH(View itemView) {
        super(itemView);
        iv_image = itemView.findViewById(R.id.iv_image);
        tv_content = itemView.findViewById(R.id.tv_content);
        tv_get_lottery = itemView.findViewById(R.id.tv_get_lottery);
    }
}
