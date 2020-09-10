package com.qubuyer.business.register.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.register.RegisterEntity;
import com.qubuyer.business.register.presenter.BindPresenter;
import com.qubuyer.business.register.view.IBindView;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.customview.AbsToolbar;
import com.qubuyer.customview.ImageViewAutoLoad;
import com.qubuyer.utils.CountDownTimerUtils;
import com.qubuyer.utils.NavigationUtil;

import java.util.HashMap;

import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.OnClick;

public class BindingActivity extends BaseActivity<BindPresenter> implements IBindView {

    @BindView(R.id.iv_back)
    ImageViewAutoLoad iv_back;

    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.iv_phone_delete)
    ImageViewAutoLoad iv_phone_delete;

    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.iv_code_delete)
    ImageViewAutoLoad iv_code_delete;
    @BindView(R.id.tv_get_code)
    TextView tv_get_code;

    @BindView(R.id.tv_login)
    TextView tv_login;

    private CountDownTimerUtils mCountDownTimer;

    //选中的绑定第三方类型 0:微信 1:QQ
    private int mSelectedBindType = -1;
    //选中的第三方openid
    private String mSelectedBindOpenId;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_binding;
    }

    @Override
    protected BindPresenter createP(Context context) {
        return new BindPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        BarUtils.setStatusBarLightMode(this, true);
        if (null != getIntent() && null != getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY)) {
            HashMap map = (HashMap) getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY);
            mSelectedBindType = (int) map.get("bind_type");
            mSelectedBindOpenId = (String) map.get("bind_openid");
        }
        initView();
    }

    private void initView() {
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && s.length() >= 11) {
                    iv_phone_delete.setVisibility(View.VISIBLE);
                } else {
                    iv_phone_delete.setVisibility(View.GONE);
                }
                if (s.length() >= 11) {
                    tv_get_code.setBackground(ContextCompat.getDrawable(BindingActivity.this, R.drawable.shape_login_get_code_input_bg));
                    tv_get_code.setEnabled(true);
                    tv_get_code.setTextColor(Color.parseColor("#878787"));
                } else {
                    tv_get_code.setBackground(ContextCompat.getDrawable(BindingActivity.this, R.drawable.shape_login_get_code_normal_bg));
                    tv_get_code.setEnabled(false);
                    tv_get_code.setTextColor(Color.parseColor("#CCCCCC"));
                }
                checkInput();
            }
        });
        iv_phone_delete.setVisibility(View.GONE);
        iv_phone_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_phone.setText("");
            }
        });


        et_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    iv_code_delete.setVisibility(View.VISIBLE);
                } else {
                    iv_code_delete.setVisibility(View.GONE);
                }
                checkInput();
            }
        });
        iv_code_delete.setVisibility(View.GONE);
        iv_code_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_code.setText("");
            }
        });


        mCountDownTimer = new CountDownTimerUtils(tv_get_code, this);
        tv_get_code.setEnabled(false);
        tv_get_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_get_code.setEnabled(false);//设置不可点击
                tv_get_code.setBackground(ContextCompat.getDrawable(BindingActivity.this, R.drawable.shape_login_get_code_normal_bg));
                tv_get_code.setTextColor(Color.parseColor("#CCCCCC"));
                mCountDownTimer.start();
                mPresenter.getVerificationcode(et_phone.getText().toString());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public AbsToolbar toolbar() {
        return null;
    }

    @OnClick({R.id.iv_back})
    public void onClickByButterKnfie(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @OnClick({R.id.tv_login})
    public void onClickWithButterKnife(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                String phone = et_phone.getText().toString();
                String code = et_code.getText().toString();
                mPresenter.bindPhone(phone, code, mSelectedBindOpenId, mSelectedBindType == 0 ? "wx" : "qq");
                break;
        }
    }

    private void checkInput() {
        tv_login.setEnabled(false);
        tv_login.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_login_btn_normal_bg));
        if (TextUtils.isEmpty(et_phone.getText())) return;
        if (TextUtils.isEmpty(et_code.getText())) return;
        tv_login.setEnabled(true);
        tv_login.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_login_btn_input_bg));
        tv_login.setTextColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    public void onShowVerificationcodeResultToView(boolean result) {
        if (result) {
            ToastUtils.showShort("验证码获取成功");
        }
    }

    @Override
    public void onShowBindPhoneSetPwdToView(RegisterEntity entity) {
        if (null != entity && !TextUtils.isEmpty(entity.getToken())) {
            NavigationUtil.forward(this, SetPwdActivity.class, entity.getToken());
        }
    }

    @Override
    public void onShowBindPhoneResultToView(RegisterEntity entity) {
        if (null != entity && !TextUtils.isEmpty(entity.getToken())) {
            finish();
        }
    }
}
