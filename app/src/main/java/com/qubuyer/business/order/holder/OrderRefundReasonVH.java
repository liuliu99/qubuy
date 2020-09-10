package com.qubuyer.business.order.holder;

import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.qubuyer.R;
import com.qubuyer.customview.ImageViewAutoLoad;

import androidx.recyclerview.widget.RecyclerView;

public class OrderRefundReasonVH extends RecyclerView.ViewHolder {
    public RadioButton cb_default;
    public TextView tv_title;

    public OrderRefundReasonVH(View itemView) {
        super(itemView);
        cb_default = itemView.findViewById(R.id.cb_default);
        tv_title = itemView.findViewById(R.id.tv_title);
    }
}
