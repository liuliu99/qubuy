package com.qubuyer.business.home.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qubuyer.R;
import com.qubuyer.customview.EnhanceTabLayout;
import com.qubuyer.customview.ImageViewAutoLoad;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class HomeSaleTopVH extends RecyclerView.ViewHolder {
    public RelativeLayout rl_container;
    public LinearLayout ll_more;
    public TextView tv_sale_totoal_amount;
    public TextView tv_yesterday_sale_amount;
    public LinearLayout ll_money;
    public TextView tv_rebate;
    public TextView tv_bonus;
    public TextView tv_brokerage;

    public LinearLayout ll_container;
    public LinearLayout ll_rule;
    public EnhanceTabLayout etl_tab;
    public ViewPager vp_page;

    public HomeSaleTopVH(View itemView) {
        super(itemView);
        rl_container = itemView.findViewById(R.id.rl_container);
        ll_more = itemView.findViewById(R.id.ll_more);
        tv_sale_totoal_amount = itemView.findViewById(R.id.tv_sale_totoal_amount);
        tv_yesterday_sale_amount = itemView.findViewById(R.id.tv_yesterday_sale_amount);
        ll_money = itemView.findViewById(R.id.ll_money);
        tv_rebate = itemView.findViewById(R.id.tv_rebate);
        tv_bonus = itemView.findViewById(R.id.tv_bonus);
        tv_brokerage = itemView.findViewById(R.id.tv_brokerage);

        ll_container = itemView.findViewById(R.id.ll_container);
        ll_rule = itemView.findViewById(R.id.ll_rule);
        etl_tab = itemView.findViewById(R.id.etl_tab);
        vp_page = itemView.findViewById(R.id.vp_page);
    }
}
