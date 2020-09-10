package com.qubuyer.business.register.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.register.RegisterEntity;
import com.qubuyer.business.main.MainActivity;
import com.qubuyer.business.register.presenter.RegisterPresenter;
import com.qubuyer.business.register.view.IRegisterView;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.constant.NetConstant;
import com.qubuyer.customview.AbsToolbar;
import com.qubuyer.customview.BrowserActivity;
import com.qubuyer.customview.ImageViewAutoLoad;
import com.qubuyer.utils.CountDownTimerUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements IRegisterView {

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

    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @BindView(R.id.iv_pwd_show)
    ImageViewAutoLoad iv_pwd_show;

    @BindView(R.id.et_invitation_code)
    EditText et_invitation_code;
    @BindView(R.id.iv_invitation_code_delete)
    ImageViewAutoLoad iv_invitation_code_delete;

    @BindView(R.id.tv_login)
    TextView tv_login;

    @BindView(R.id.tv_agreement)
    TextView tv_agreement;

    private CountDownTimerUtils mCountDownTimer;

    private boolean mIsClickHidePwd;
    private boolean mIsHidePwd;

    private String mScanInviteCode;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_register;
    }

    @Override
    protected RegisterPresenter createP(Context context) {
        return new RegisterPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        if (null != getIntent() && null != getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY)) {
            mScanInviteCode = (String) getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY);
        }
        setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        BarUtils.setStatusBarLightMode(this, true);
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
//                    tv_get_code.setBackground(ContextCompat.getDrawable(RegisterActivity.this, R.drawable.shape_login_get_code_input_bg));
                    tv_get_code.setEnabled(true);
                    tv_get_code.setTextColor(Color.parseColor("#FF681D"));
                } else {
//                    tv_get_code.setBackground(ContextCompat.getDrawable(RegisterActivity.this, R.drawable.shape_login_get_code_normal_bg));
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
//                tv_get_code.setBackground(ContextCompat.getDrawable(RegisterActivity.this, R.drawable.shape_login_get_code_normal_bg));
                tv_get_code.setTextColor(Color.parseColor("#CCCCCC"));
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
                if (!TextUtils.isEmpty(s) && !mIsClickHidePwd) {
                    iv_pwd_show.setDrawableId(RegisterActivity.this, R.drawable.icon_login_show_pwd);
                } else if (TextUtils.isEmpty(s) && mIsClickHidePwd) {
                    mIsClickHidePwd = false;
                    iv_pwd_show.setDrawableId(RegisterActivity.this, R.drawable.icon_login_hide_pwd);
                    et_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    iv_pwd_show.setDrawableId(RegisterActivity.this, R.drawable.icon_login_hide_pwd);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkInput();
            }
        });
        iv_pwd_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsClickHidePwd = true;
                if (!mIsHidePwd) {
                    mIsHidePwd = true;
                    iv_pwd_show.setDrawableId(RegisterActivity.this, R.drawable.icon_login_hide_pwd);
                    et_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_pwd.setSelection(et_pwd.getText().length());
                } else {
                    mIsHidePwd = false;
                    iv_pwd_show.setDrawableId(RegisterActivity.this, R.drawable.icon_login_show_pwd);
                    et_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_pwd.setSelection(et_pwd.getText().length());
                }
            }
        });

        if (!TextUtils.isEmpty(mScanInviteCode)) {
            et_invitation_code.setText(mScanInviteCode);
            et_invitation_code.setEnabled(false);
            iv_invitation_code_delete.setVisibility(View.GONE);
        } else {
            et_invitation_code.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!TextUtils.isEmpty(s)) {
                        iv_invitation_code_delete.setVisibility(View.VISIBLE);
                    } else {
                        iv_invitation_code_delete.setVisibility(View.GONE);
                    }
                    checkInput();
                }
            });
            iv_invitation_code_delete.setVisibility(View.GONE);
            iv_invitation_code_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    et_invitation_code.setText("");
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public AbsToolbar toolbar() {
        return null;
    }

    @OnClick({R.id.iv_back, R.id.tv_agreement, R.id.tv_login})
    public void onClickByButterKnfie(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_agreement:
                Intent intent = new Intent(this, BrowserActivity.class);
                intent.putExtra(BrowserActivity.KEY_URL, NetConstant.BASE_URL + "/home/html/helpCenterDetail?id=43");
                startActivity(intent);
                break;
            case R.id.tv_login:
                String phone = et_phone.getText().toString();
                String code = et_code.getText().toString();
                String pwd = et_pwd.getText().toString();
                String invitation_code = et_invitation_code.getText().toString();
                mPresenter.register(phone, pwd, code, !TextUtils.isEmpty(mScanInviteCode) ? mScanInviteCode : invitation_code);
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

    private void checkInput() {
        tv_login.setEnabled(false);
        tv_login.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_login_btn_normal_bg));
        if (TextUtils.isEmpty(et_phone.getText())) return;
        if (TextUtils.isEmpty(et_code.getText())) return;
        if (TextUtils.isEmpty(et_pwd.getText())) return;
        if (TextUtils.isEmpty(mScanInviteCode) && TextUtils.isEmpty(et_invitation_code.getText())) return;
        tv_login.setEnabled(true);
        tv_login.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_login_btn_input_bg));
        tv_login.setTextColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    public void onShowVerificationcodeResultToView(boolean success) {
        if (success) {
            ToastUtils.showShort("验证码获取成功");
        }
    }

    @Override
    public void onShowResiterResultToView(RegisterEntity result) {
        if (null != result && !TextUtils.isEmpty(result.getToken())) {
//            EventBus.getDefault().post(new LoginResultEvent());
            ActivityUtils.finishOtherActivities(MainActivity.class);
        }
    }
}
