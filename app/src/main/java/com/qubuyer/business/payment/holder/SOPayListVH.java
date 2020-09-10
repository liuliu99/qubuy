package com.qubuyer.business.payment.holder;

import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.qubuyer.R;
import com.qubuyer.customview.ImageViewAutoLoad;

import androidx.recyclerview.widget.RecyclerView;

public class SOPayListVH extends RecyclerView.ViewHolder {
    public ImageViewAutoLoad iv_pay_type;
    public TextView tv_title;
    public RadioButton iv_pay_select;

    public SOPayListVH(View itemView) {
        super(itemView);
        iv_pay_type = itemView.findViewById(R.id.iv_pay_type);
        tv_title = itemView.findViewById(R.id.tv_title);
        iv_pay_select = itemView.findViewById(R.id.iv_pay_select);
    }
}
