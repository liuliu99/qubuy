package com.qubuyer.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;


public class CountDownTimerUtils extends CountDownTimer {
    private TextView mButton;

    private Context mContext;

    public CountDownTimerUtils(TextView button, Context context) {
        //控件，定时总时间,间隔时间
        super(60000, 1000);
        this.mButton = button;
        this.mContext = context;
    }

    public CountDownTimerUtils(TextView button, long millisInFuture, long countDownInterval) {
        //控件，定时总时间,间隔时间
        super(millisInFuture, countDownInterval);
        this.mButton = button;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mButton.setText("重新发送" + millisUntilFinished / 1000 + "s");//设置倒计时时间
    }

    @Override
    public void onFinish() {
        mButton.setEnabled(true);//重新获得点击
        mButton.setText("获取验证码");
//        mButton.setBackground(mContext.getResources().getDrawable(R.drawable.shape_login_get_code_input_bg));
        mButton.setTextColor(Color.parseColor("#FF681D"));
    }
}
