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
import com.qubuyer.bean.register.RegisterEntity;
import com.qubuyer.business.register.presenter.SetPwdPresenter;
import com.qubuyer.business.register.view.ISetPwdView;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.customview.AbsToolbar;
import com.qubuyer.customview.ImageViewAutoLoad;
import com.qubuyer.utils.CountDownTimerUtils;

import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.OnClick;

public class SetPwdActivity extends BaseActivity<SetPwdPresenter> implements ISetPwdView {

    @BindView(R.id.iv_back)
    ImageViewAutoLoad iv_back;

    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @BindView(R.id.iv_pwd_delete)
    ImageViewAutoLoad iv_pwd_delete;

    @BindView(R.id.et_pwd_second)
    EditText et_pwd_second;
    @BindView(R.id.iv_pwd_second_delete)
    ImageViewAutoLoad iv_pwd_second_delete;

    @BindView(R.id.et_invitation_code)
    EditText et_invitation_code;
    @BindView(R.id.iv_invitation_code_delete)
    ImageViewAutoLoad iv_invitation_code_delete;

    @BindView(R.id.tv_login)
    TextView tv_login;

    private String mToken;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_set_pwd;
    }

    @Override
    protected SetPwdPresenter createP(Context context) {
        return new SetPwdPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        BarUtils.setStatusBarLightMode(this, true);
        if (null != getIntent() && null != getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY)) {
            mToken = (String) getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY);
        }
        initView();
    }

    private void initView() {
        et_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && s.length() >= 11) {
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

        et_invitation_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && s.length() >= 11) {
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

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public AbsToolbar toolbar() {
        return null;
    }

    @OnClick({R.id.iv_back,R.id.tv_login})
    public void onClickByButterKnfie(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_login:
                String pwd = et_pwd.getText().toString();
                String secondPwd = et_pwd_second.getText().toString();
                if (TextUtils.isEmpty(pwd)) {
                    ToastUtils.showShort("请输入密码");
                    return;
                }
                if (TextUtils.isEmpty(secondPwd)) {
                    ToastUtils.showShort("请确认密码");
                    return;
                }
                if (!pwd.equalsIgnoreCase(secondPwd)) {
                    ToastUtils.showShort("两次密码输入不一致, 请重新输入");
                    return;
                }
                mPresenter.setPwd(mToken, "S00330", pwd, secondPwd);
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
        if (TextUtils.isEmpty(et_pwd.getText())) return;
        if (TextUtils.isEmpty(et_pwd_second.getText())) return;
        if (TextUtils.isEmpty(et_invitation_code.getText())) return;
        tv_login.setEnabled(true);
        tv_login.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_login_btn_input_bg));
        tv_login.setTextColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    public void onShowSetPwdResultToView(RegisterEntity entity) {
        if (null != entity && !TextUtils.isEmpty(entity.getToken())) {
            finish();
        }
    }
}
