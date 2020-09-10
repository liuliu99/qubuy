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
import com.qubuyer.business.register.presenter.ForgetPresenter;
import com.qubuyer.business.register.view.IForgetView;
import com.qubuyer.customview.ImageViewAutoLoad;
import com.qubuyer.utils.ForgetCountDownTimerUtils;

import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.OnClick;

public class ForgetPwdActivity extends BaseActivity<ForgetPresenter> implements IForgetView {

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

    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @BindView(R.id.iv_pwd_delete)
    ImageViewAutoLoad iv_pwd_delete;

    @BindView(R.id.et_pwd_second)
    EditText et_pwd_second;
    @BindView(R.id.iv_pwd_second_delete)
    ImageViewAutoLoad iv_pwd_second_delete;

    @BindView(R.id.tv_confirm)
    TextView tv_confirm;

    private ForgetCountDownTimerUtils mCountDownTimer;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_forget_pwd;
    }

    @Override
    protected ForgetPresenter createP(Context context) {
        return new ForgetPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        BarUtils.setStatusBarLightMode(this, true);
        setTitle("忘记密码");
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
//                    tv_get_code.setBackground(ContextCompat.getDrawable(ForgetPwdActivity.this, R.drawable.shape_login_get_code_input_bg));
                    tv_get_code.setEnabled(true);
                    tv_get_code.setTextColor(Color.parseColor("#FF681D"));
                } else {
//                    tv_get_code.setBackground(ContextCompat.getDrawable(ForgetPwdActivity.this, R.drawable.shape_login_get_code_normal_bg));
                    tv_get_code.setEnabled(false);
                    tv_get_code.setTextColor(Color.parseColor("#999999"));
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

        mCountDownTimer = new ForgetCountDownTimerUtils(tv_get_code, this);
        tv_get_code.setEnabled(false);
        tv_get_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_get_code.setEnabled(false);//设置不可点击
//                tv_get_code.setBackground(ContextCompat.getDrawable(ForgetPwdActivity.this, R.drawable.shape_login_get_code_normal_bg));
                tv_get_code.setTextColor(Color.parseColor("#999999"));
                mCountDownTimer.start();
                mPresenter.getVerificationcode(et_phone.getText().toString());
            }
        });


        et_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    iv_pwd_delete.setVisibility(View.VISIBLE);
                } else {
                    iv_pwd_delete.setVisibility(View.GONE);
                }
                checkInput();
            }
        });
        iv_pwd_delete.setVisibility(View.GONE);
        iv_pwd_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_pwd.setText("");
            }
        });

        et_pwd_second.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    iv_pwd_second_delete.setVisibility(View.VISIBLE);
                } else {
                    iv_pwd_second_delete.setVisibility(View.GONE);
                }
                checkInput();
            }
        });
        iv_pwd_second_delete.setVisibility(View.GONE);
        iv_pwd_second_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_pwd_second.setText("");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
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

    @OnClick({R.id.tv_confirm})
    public void onClickWithButterKnife(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm:
                String phone = et_phone.getText().toString();
                String code = et_code.getText().toString();
                String pwd = et_pwd.getText().toString();
                String pwd1 = et_pwd_second.getText().toString();
                mPresenter.findPwd(phone, pwd, code, pwd1);
                break;
        }
    }

    private void checkInput() {
        tv_confirm.setEnabled(false);
        tv_confirm.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_login_btn_normal_bg));
        if (TextUtils.isEmpty(et_phone.getText())) return;
        if (TextUtils.isEmpty(et_code.getText())) return;
        if (TextUtils.isEmpty(et_pwd.getText())) return;
        if (TextUtils.isEmpty(et_pwd_second.getText())) return;
        tv_confirm.setEnabled(true);
        tv_confirm.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_login_btn_input_bg));
        tv_confirm.setTextColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    public void onShowVerificationcodeResultToView(boolean success) {
        if (success) {
            ToastUtils.showShort("验证码获取成功");
        }
    }

    @Override
    public void onShowFindPwdResultToView(boolean result) {
        if (result) {
            ToastUtils.showShort("密码修改成功");
            finish();
        }
    }
}
