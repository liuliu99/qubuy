package com.qubuyer.business.mine.holder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.qubuyer.R;

public class IncomeListVH extends RecyclerView.ViewHolder {
    public TextView tv_title;
    public TextView tv_time;
    public TextView tv_price;

    public IncomeListVH(View itemView) {
        super(itemView);
        tv_title = itemView.findViewById(R.id.tv_title);
        tv_time = itemView.findViewById(R.id.tv_time);
        tv_price = itemView.findViewById(R.id.tv_price);
    }
}
