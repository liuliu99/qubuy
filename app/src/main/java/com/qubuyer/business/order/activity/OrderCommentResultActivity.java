package com.qubuyer.business.order.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.event.GoToMainEvent;
import com.qubuyer.business.main.MainActivity;
import com.qubuyer.business.order.presenter.OrderCommentListPresenter;
import com.qubuyer.utils.EventBusUtil;

import butterknife.BindView;


/**
 * @author Susong
 * @date 创建时间2019/4/2
 * @description 发表评论结果页
 */
public class OrderCommentResultActivity extends BaseActivity {
    @BindView(R.id.tv_back_main_page)
    TextView tv_back_main_page;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_order_comment_result;
    }

    @Override
    protected OrderCommentListPresenter createP(Context context) {
        return null;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setTitle("评价成功");
        tv_back_main_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBusUtil.sendEvent(new GoToMainEvent());
                ActivityUtils.finishOtherActivities(MainActivity.class);
            }
        });
    }

    @Override
    protected void initData() {
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        dissmissLoadingDialog();
    }
}
