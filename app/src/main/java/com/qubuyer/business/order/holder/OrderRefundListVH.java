package com.qubuyer.business.order.holder;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qubuyer.R;
import com.qubuyer.customview.ImageViewAutoLoad;

import androidx.recyclerview.widget.RecyclerView;

public class OrderRefundListVH extends RecyclerView.ViewHolder {
    public TextView tv_order_no;
    public TextView tv_order_status;
    public RelativeLayout rl_one_good;
    public ImageViewAutoLoad iv_good_img;
    public TextView tv_good_name;
    public TextView tv_good_price;
    public TextView tv_good_num;
    public RelativeLayout rl_more_good;
    public RecyclerView rv_good_list;
    public TextView tv_good_total_price;
    public TextView tv_overtime;
    public TextView tv_right_two_btn;
    public TextView tv_right_one_btn;
    public CountDownTimer countDownTimer;

    public OrderRefundListVH(View itemView) {
        super(itemView);
        tv_order_no = itemView.findViewById(R.id.tv_order_no);
        tv_order_status = itemView.findViewById(R.id.tv_order_status);
        rl_one_good = itemView.findViewById(R.id.rl_one_good);
        iv_good_img = itemView.findViewById(R.id.iv_good_img);
        tv_good_name = itemView.findViewById(R.id.tv_good_name);
        tv_good_price = itemView.findViewById(R.id.tv_good_price);
        tv_good_num = itemView.findViewById(R.id.tv_good_num);
        rl_more_good = itemView.findViewById(R.id.rl_more_good);
        rv_good_list = itemView.findViewById(R.id.rv_good_list);
        tv_good_total_price = itemView.findViewById(R.id.tv_good_total_price);
        tv_overtime = itemView.findViewById(R.id.tv_overtime);
        tv_right_two_btn = itemView.findViewById(R.id.tv_right_two_btn);
        tv_right_one_btn = itemView.findViewById(R.id.tv_right_one_btn);
    }
}
