package com.qubuyer.business.good.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.qubuyer.R;
import com.xingliuhua.xlhratingbar.IRatingView;

public class GoodDetailCommentRatingView implements IRatingView {
    ViewGroup mViewGroup;

    @Override
    public void setCurrentState(int state, int position, int starNums) {
        TextView textView = mViewGroup.findViewById(R.id.tv_state);
        textView.setVisibility(View.GONE);
        ImageView ivStar = mViewGroup.findViewById(R.id.iv_star);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ConvertUtils.dp2px(13), ConvertUtils.dp2px(13));
        layoutParams.setMargins(0, 0, ConvertUtils.dp2px(7), 0);
        ivStar.setEnabled(false);
        ivStar.setLayoutParams(layoutParams);
        switch (state) {
            case IRatingView.STATE_NONE:
            case IRatingView.STATE_HALF:
                ivStar.setImageResource(R.drawable.icon_good_comment_rating_normal);
                break;
            case IRatingView.STATE_FULL:
                ivStar.setImageResource(R.drawable.icon_good_comment_rating_selected);
                break;
        }
    }

    @Override
    public ViewGroup getRatingView(Context context, int position, int starNums) {
        View inflate = View.inflate(context, R.layout.rating, null);
        mViewGroup = (ViewGroup) inflate;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;
        mViewGroup.setLayoutParams(layoutParams);
        return mViewGroup;
    }
}
