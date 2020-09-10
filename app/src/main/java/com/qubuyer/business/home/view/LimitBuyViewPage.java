package com.qubuyer.business.home.view;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.qubuyer.R;
import com.qubuyer.bean.TimeMode;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.business.home.adapter.LimitBuyAdapter;
import com.qubuyer.customview.GoodItemDecoration;
import com.qubuyer.customview.HomeGoodItemDecoration;
import com.qubuyer.utils.TimeUtil;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Susong
 * @date 创建时间2019/3/20
 * @description 二级分类商品列表页
 * @version
 */
public class LimitBuyViewPage{
    //当前上下文
    private Context mContext;
    private View mView;
    private TextView tv_title;
    private TextView tv_count_down_title;
    private TextView tv_hour;
    private TextView tv_minute;
    private TextView tv_second;
    private RecyclerView rv_list;
    private LimitBuyAdapter mAdapter;
    private List<HomeGoodEntity> mData;
    private long mStartTime;
    private long mEndTime;
    private long mCurrentTime;

    private CountDownTimer mCountDownTimer;

    public LimitBuyViewPage(Context mContext, List<HomeGoodEntity> list, long startTime, long endTime, long currentTime) {
        this.mContext = mContext;
        mData = list;
        mStartTime = startTime;
        mEndTime = endTime;
        mCurrentTime = currentTime;
    }

    public View getView() {
        if (mView == null) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.layout_activity_limitbuy_page, null);
            tv_title = mView.findViewById(R.id.tv_title);
            tv_count_down_title = mView.findViewById(R.id.tv_count_down_title);
            tv_hour = mView.findViewById(R.id.tv_hour);
            tv_minute = mView.findViewById(R.id.tv_minute);
            tv_second = mView.findViewById(R.id.tv_second);
            rv_list = mView.findViewById(R.id.rv_list);
            long diffTime;
            String statusString;
            if (mStartTime > mCurrentTime) {
                statusString = "距开始";
                diffTime = mStartTime - mCurrentTime;
            } else if (mStartTime < mCurrentTime && mEndTime > mCurrentTime) {
                statusString = "距结束";
                diffTime = mEndTime - mCurrentTime;
                tv_title.setText("抢购中 先下单先得哦");
            } else {
                diffTime = 0;
                statusString = "已结束";
            }
            tv_count_down_title.setText(statusString);
            mCountDownTimer = new CountDownTimer(diffTime, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    TimeMode timeMode = TimeUtil.parseRemainTime(millisUntilFinished);
                    if (timeMode.getDay() > 0) {
                        timeMode.setHour(timeMode.getHour() + timeMode.getDay() * 24);
                    }
                    tv_hour.setText(timeMode.getHour() + "");
                    tv_minute.setText(timeMode.getMinute() + "");
                    tv_second.setText(timeMode.getSecond() + "");
                }

                @Override
                public void onFinish() {
                    tv_hour.setText("00");
                    tv_minute.setText("00");
                    tv_second.setText("00");
                }
            };
            mCountDownTimer.start();
            initRecyclerView();
        }
        return mView;
    }

    private void initRecyclerView() {
        mAdapter = new LimitBuyAdapter(mContext, mData);
        rv_list.addItemDecoration(new GoodItemDecoration());
//        mRecyclerView.addItemDecoration(new DecorationLLM(mContext, DecorationLLM.VERTICAL_LIST, R.drawable.shape_recyclerview_divider, DensityUtil.dip2px(mContext, 10)));
        rv_list.setLayoutManager(new GridLayoutManager(mContext, 2));
        rv_list.setAdapter(mAdapter);
    }

    public void destory() {
        if (null != mCountDownTimer) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }
}


