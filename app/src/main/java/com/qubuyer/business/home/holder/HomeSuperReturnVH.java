package com.qubuyer.business.home.holder;

import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.qubuyer.R;
import com.qubuyer.customview.ViewPagerWrapContentHeight;

public class HomeSuperReturnVH extends RecyclerView.ViewHolder {
    public LinearLayout ll_container;
    public LinearLayout ll_more;
    public ViewPagerWrapContentHeight vp_super_return;
    public LinearLayout ll_indicator;

    public HomeSuperReturnVH(View itemView) {
        super(itemView);
        ll_container = itemView.findViewById(R.id.ll_container);
        ll_more = itemView.findViewById(R.id.ll_more);
        vp_super_return = itemView.findViewById(R.id.vp_super_return);
        ll_indicator = itemView.findViewById(R.id.ll_indicator);
    }
}
