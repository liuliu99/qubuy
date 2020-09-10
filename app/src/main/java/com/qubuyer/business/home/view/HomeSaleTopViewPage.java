package com.qubuyer.business.home.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qubuyer.R;
import com.qubuyer.bean.home.HomeSaleTopEntity;
import com.qubuyer.constant.NetConstant;
import com.qubuyer.customview.BrowserActivity;
import com.qubuyer.customview.ImageViewAutoLoad;
import com.qubuyer.utils.ImageUtil;
import com.qubuyer.utils.StringUtil;

import java.util.List;

/**
 * @author Susong
 * @date 创建时间2019/3/9
 * @description 销售排行榜ViewPage
 */
public class HomeSaleTopViewPage {
    //当前上下文
    private Context mContext;
    private View mView;
    private LinearLayout ll_container;
    private ImageViewAutoLoad iv_second_head_img;
    private TextView tv_second_user_name;
    private TextView tv_second_user_sale_amount;
    private TextView tv_second_user_award_amount;

    private ImageViewAutoLoad iv_first_head_img;
    private TextView tv_first_user_name;
    private TextView tv_first_user_sale_amount;
    private TextView tv_first_user_award_amount;

    private ImageViewAutoLoad iv_third_head_img;
    private TextView tv_third_user_name;
    private TextView tv_third_user_sale_amount;
    private TextView tv_third_user_award_amount;

    private LinearLayout ll_activity_rules;

    private List<HomeSaleTopEntity.UserBeanXXX.WeekMonthYearBean> mData;

    public HomeSaleTopViewPage(Context mContext, List<HomeSaleTopEntity.UserBeanXXX.WeekMonthYearBean> data) {
        this.mContext = mContext;
        this.mData = data;
    }

    public View getView() {
        if (mView == null) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.layout_fragment_home_sale_top_page, null);
            ll_container = mView.findViewById(R.id.ll_container);
            ll_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(mContext, BrowserActivity.class);
//                    intent.putExtra(BrowserActivity.KEY_URL, NetConstant.BASE_URL + "/home/html/leaderboard");
//                    mContext.startActivity(intent);
                }
            });
            iv_second_head_img = mView.findViewById(R.id.iv_second_head_img);
            tv_second_user_name = mView.findViewById(R.id.tv_second_user_name);
            tv_second_user_sale_amount = mView.findViewById(R.id.tv_second_user_sale_amount);
            tv_second_user_award_amount = mView.findViewById(R.id.tv_second_user_award_amount);
            iv_first_head_img = mView.findViewById(R.id.iv_first_head_img);
            tv_first_user_name = mView.findViewById(R.id.tv_first_user_name);
            tv_first_user_sale_amount = mView.findViewById(R.id.tv_first_user_sale_amount);
            tv_first_user_award_amount = mView.findViewById(R.id.tv_first_user_award_amount);
            iv_third_head_img = mView.findViewById(R.id.iv_third_head_img);
            tv_third_user_name = mView.findViewById(R.id.tv_third_user_name);
            tv_third_user_sale_amount = mView.findViewById(R.id.tv_third_user_sale_amount);
            tv_third_user_award_amount = mView.findViewById(R.id.tv_third_user_award_amount);
            ll_activity_rules = mView.findViewById(R.id.ll_activity_rules);
            ll_activity_rules.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(mContext, BrowserActivity.class);
//                    intent.putExtra(BrowserActivity.KEY_URL, NetConstant.BASE_URL + "/home/html/leaderboard");
//                    mContext.startActivity(intent);
                }
            });
        }
        setDataToView();
        return mView;
    }

    private void setDataToView() {
        if (mData.size() >= 1) {
            HomeSaleTopEntity.UserBeanXXX.WeekMonthYearBean entity = mData.get(0);
            if (null != entity.getUser()) {
                ImageUtil.loadCircleImage(mContext, entity.getUser().getHead_pic(), iv_first_head_img);
                tv_first_user_name.setText(entity.getUser().getNickname());
            }
            tv_first_user_sale_amount.setText("销售额:" + StringUtil.formatAmount(entity.getMoney(), 0) + "元");
            tv_first_user_award_amount.setText("奖励:" + StringUtil.formatAmount(entity.getBonus(), 0) + "元");
        }
        if (mData.size() >= 2) {
            HomeSaleTopEntity.UserBeanXXX.WeekMonthYearBean entity = mData.get(1);
            if (null != entity.getUser()) {
                ImageUtil.loadCircleImage(mContext, entity.getUser().getHead_pic(), iv_second_head_img);
                tv_second_user_name.setText(entity.getUser().getNickname());
            }
            tv_second_user_sale_amount.setText("销售额:" + StringUtil.formatAmount(entity.getMoney(), 0) + "元");
            tv_second_user_award_amount.setText("奖励:" + StringUtil.formatAmount(entity.getBonus(), 0) + "元");
        }
        if (mData.size() >= 3) {
            HomeSaleTopEntity.UserBeanXXX.WeekMonthYearBean entity = mData.get(2);
            if (null != entity.getUser()) {
                ImageUtil.loadCircleImage(mContext, entity.getUser().getHead_pic(), iv_third_head_img);
                tv_third_user_name.setText(entity.getUser().getNickname());
            }
            tv_third_user_sale_amount.setText("销售额:" + StringUtil.formatAmount(entity.getMoney(), 0) + "元");
            tv_third_user_award_amount.setText("奖励:" + StringUtil.formatAmount(entity.getBonus(), 0) + "元");
        }
    }
}


