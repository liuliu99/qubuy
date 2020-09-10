package com.qubuyer.business.mine.holder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.qubuyer.R;
import com.qubuyer.customview.ImageViewAutoLoad;

public class MineFansVH extends RecyclerView.ViewHolder {
    public ImageViewAutoLoad iv_img;
    public TextView tv_name;
    public TextView tv_id;
    public TextView tv_price;
    public TextView tv_time;

    public MineFansVH(View itemView) {
        super(itemView);
        iv_img = itemView.findViewById(R.id.iv_img);
        tv_name = itemView.findViewById(R.id.tv_name);
        tv_id = itemView.findViewById(R.id.tv_id);
        tv_price = itemView.findViewById(R.id.tv_price);
        tv_time = itemView.findViewById(R.id.tv_time);
    }
}
