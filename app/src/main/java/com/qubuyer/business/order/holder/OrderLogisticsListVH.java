package com.qubuyer.business.order.holder;

import android.view.View;
import android.widget.TextView;

import com.qubuyer.R;

import androidx.recyclerview.widget.RecyclerView;

public class OrderLogisticsListVH extends RecyclerView.ViewHolder {
    public View v_ball;
    public View v_line_vertical_one;
    public View v_line_vertical_two;
    public TextView tv_status_desc;
    public TextView tv_time;
    public View v_line_horizontal;

    public OrderLogisticsListVH(View itemView) {
        super(itemView);
        v_ball = itemView.findViewById(R.id.v_ball);
        v_line_vertical_one = itemView.findViewById(R.id.v_line_vertical_one);
        v_line_vertical_two = itemView.findViewById(R.id.v_line_vertical_two);
        tv_status_desc = itemView.findViewById(R.id.tv_status_desc);
        tv_time = itemView.findViewById(R.id.tv_time);
        v_line_horizontal = itemView.findViewById(R.id.v_line_horizontal);
    }
}
