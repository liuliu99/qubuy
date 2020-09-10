package com.qubuyer.business.good.holder;

import android.view.View;
import android.widget.TextView;

import com.qubuyer.R;
import com.qubuyer.customview.ImageViewAutoLoad;

import androidx.recyclerview.widget.RecyclerView;

public class SearchGoodHistoryListVH extends RecyclerView.ViewHolder {
    public TextView tv_text;
    public ImageViewAutoLoad iv_delete;

    public SearchGoodHistoryListVH(View itemView) {
        super(itemView);
        tv_text = itemView.findViewById(R.id.tv_text);
        iv_delete = itemView.findViewById(R.id.iv_delete);
    }
}
