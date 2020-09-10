package com.qubuyer.business.home.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.qubuyer.R;
import com.qubuyer.customview.EnhanceTabLayout;

public class HomeSpecialVH extends RecyclerView.ViewHolder {
    public LinearLayout ll_container;
    public LinearLayout ll_choiceness;
    public LinearLayout ll_limit;
    public LinearLayout ll_special;
    public LinearLayout ll_firstpublish;
    public RecyclerView rv_choiceness;
    public RecyclerView rv_limit;
    public RecyclerView rv_special;
    public RecyclerView rv_firstpublish;

    public HomeSpecialVH(View itemView) {
        super(itemView);
        ll_container = itemView.findViewById(R.id.ll_container);
        ll_choiceness = itemView.findViewById(R.id.ll_choiceness);
        ll_limit = itemView.findViewById(R.id.ll_limit);
        ll_special = itemView.findViewById(R.id.ll_special);
        ll_firstpublish = itemView.findViewById(R.id.ll_firstpublish);
        rv_choiceness = itemView.findViewById(R.id.rv_choiceness);
        rv_limit = itemView.findViewById(R.id.rv_limit);
        rv_special = itemView.findViewById(R.id.rv_special);
        rv_firstpublish = itemView.findViewById(R.id.rv_firstpublish);
    }
}
