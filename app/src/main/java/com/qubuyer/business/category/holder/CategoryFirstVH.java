package com.qubuyer.business.category.holder;

import android.view.View;
import android.widget.TextView;

import com.qubuyer.R;

import androidx.recyclerview.widget.RecyclerView;

public class CategoryFirstVH extends RecyclerView.ViewHolder {
    public View v_line;
    public TextView tv_title;

    public CategoryFirstVH(View itemView) {
        super(itemView);
        v_line = itemView.findViewById(R.id.v_line);
        tv_title = itemView.findViewById(R.id.tv_title);
    }
}
