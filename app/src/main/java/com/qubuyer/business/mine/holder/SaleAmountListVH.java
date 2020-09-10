package com.qubuyer.business.mine.holder;

import android.view.View;
import android.widget.TextView;

import com.qubuyer.R;

import androidx.recyclerview.widget.RecyclerView;

public class SaleAmountListVH extends RecyclerView.ViewHolder {
    public TextView tv_good_name;
    public TextView tv_good_price;
    public TextView tv_time;
    public TextView tv_anticipated_income;

    public SaleAmountListVH(View itemView) {
        super(itemView);
        tv_good_name = itemView.findViewById(R.id.tv_good_name);
        tv_good_price = itemView.findViewById(R.id.tv_good_price);
        tv_time = itemView.findViewById(R.id.tv_time);
        tv_anticipated_income = itemView.findViewById(R.id.tv_anticipated_income);
    }
}
