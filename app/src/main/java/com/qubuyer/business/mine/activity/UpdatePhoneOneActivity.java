package com.qubuyer.business.mine.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.mine.UserEntity;
import com.qubuyer.constant.NetConstant;
import com.qubuyer.utils.CountDownTimerUtils;
import com.qubuyer.utils.NavigationUtil;
import com.qubuyer.utils.SessionUtil;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author Susong
 * @date 创建时间2019/4/12
 * @description 修改手机号activity
 */
public class UpdatePhoneOneActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.et_ver_code)
    EditText et_ver_code;
    @BindView(R.id.tv_get_ver_code)
    TextView tv_get_ver_code;

    private UserEntity mEntity;
    private CountDownTimerUtils mCountDownTimer;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_update_phone_one;
    }

    @Override
    protected WrapperPresenter createP(Context context) {
        return null;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setTitle("更换手机");
        mEntity = SessionUtil.getInstance().getUserInfo();
        if (SessionUtil.getInstance().isLogined() && null != mEntity) {
            tv_title.setText("你正在更换手机号码: " + mEntity.getMobile());
        } else {
            ToastUtils.showShort("用户信息异常");
        }
        mCountDownTimer = new CountDownTimerUtils(tv_get_ver_code, this);
    }

    @Override
    protected void initData() {
    }

    @OnClick({R.id.tv_get_ver_code, R.id.tv_save})
    public void onClickWithButterKnfie(View view) {
        switch (view.getId()) {
            case R.id.tv_get_ver_code:
                tv_get_ver_code.setEnabled(false);//设置不可点击
                tv_get_ver_code.setTextColor(Color.parseColor("#CCCCCC"));
                mCountDownTimer.start();
                getVerCode();
                break;
            case R.id.tv_save:
                if (TextUtils.isEmpty(et_ver_code.getText())) {
                    ToastUtils.showShort("请输入短信验证码");
                    return;
                }
                updatePhone();
                break;
        }
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        dissmissLoadingDialog();
    }

    private void getVerCode() {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", mEntity.getMobile());
        HttpInvoker.createBuilder(NetConstant.GET_FIND_PWD_VERIFICATION_CODE_POST)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {

            }
        });
    }

    private void updatePhone() {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", mEntity.getMobile());
        params.put("code", et_ver_code.getText().toString());
        HttpInvoker.createBuilder(NetConstant.UPDATE_USER_PHONE_ONE_URL)
                .setParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL) {
                    NavigationUtil.overlay(UpdatePhoneOneActivity.this, UpdatePhoneTwoActivity.class);
                }
            }
        });
    }
}
