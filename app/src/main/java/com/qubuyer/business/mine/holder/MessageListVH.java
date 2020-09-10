package com.qubuyer.business.mine.holder;

import android.view.View;
import android.widget.TextView;

import com.qubuyer.R;

import androidx.recyclerview.widget.RecyclerView;

public class MessageListVH extends RecyclerView.ViewHolder {
    public TextView tv_title;
    public TextView tv_time;

    public MessageListVH(View itemView) {
        super(itemView);
        tv_title = itemView.findViewById(R.id.tv_title);
        tv_time = itemView.findViewById(R.id.tv_time);
    }
}
