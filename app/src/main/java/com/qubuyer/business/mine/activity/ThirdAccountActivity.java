package com.qubuyer.business.mine.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alipay.sdk.app.AuthTask;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.qubuyer.R;
import com.qubuyer.base.AM;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.mine.MineCardEntity;
import com.qubuyer.bean.mine.UserEntity;
import com.qubuyer.bean.payment.AliPayAuthResultEntity;
import com.qubuyer.business.mine.presenter.MineSettingPresenter;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.constant.NetConstant;
import com.qubuyer.utils.SessionUtil;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author Susong
 * @date 创建时间:2019/4/12
 * @description 第三方账户activity
 * & @version
 */
public class ThirdAccountActivity extends BaseActivity {
    @BindView(R.id.rl_wechat_account)
    RelativeLayout rl_wechat_account;
    @BindView(R.id.tv_wechat_account)
    TextView tv_wechat_account;

    @BindView(R.id.rl_alipay_account)
    RelativeLayout rl_alipay_account;
    @BindView(R.id.tv_alipay_account)
    TextView tv_alipay_account;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_third_account;
    }

    @Override
    protected MineSettingPresenter createP(Context context) {
        return new MineSettingPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setTitle("第三方账户");
    }

    @Override
    public void onResume() {
        super.onResume();
        String openId = SPUtils.getInstance().getString(AppConstant.WECHAT_OPEN_ID);
        if (!TextUtils.isEmpty(openId)) {
            SPUtils.getInstance().remove(AppConstant.WECHAT_OPEN_ID);
            bindThirdAccount(openId, "wx");
        }
        setUserInfoToView();
    }

    @Override
    protected void initData() {
        setUserInfoToView();
    }

    private void setUserInfoToView() {
        UserEntity entity = SessionUtil.getInstance().getUserInfo();
        if (SessionUtil.getInstance().isLogined() && null != entity) {
            if (entity.getIs_bind_wx() == 1) {
                rl_wechat_account.setEnabled(false);
                tv_wechat_account.setText("已绑定");
            } else {
                rl_wechat_account.setEnabled(true);
                tv_wechat_account.setText("未绑定");
            }
            if (entity.getIs_bind_alipay() == 1) {
                rl_alipay_account.setEnabled(false);
                tv_alipay_account.setText("已绑定");
            } else {
                rl_alipay_account.setEnabled(true);
                tv_alipay_account.setText("未绑定");
            }
        } else {
            ToastUtils.showShort("用户信息异常");
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

    @OnClick({R.id.rl_wechat_account, R.id.rl_alipay_account})
    public void onClickWithButterKnife(View view) {
        switch (view.getId()) {
            case R.id.rl_wechat_account: //绑定微信账号
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "snsapi_login_lnint";
                AM.mWxApi.sendReq(req);
                break;
            case R.id.rl_alipay_account: //绑定支付宝账号
                getAlipayBindParam();
                break;
        }
    }

    private void getAlipayBindParam(){
        HttpInvoker.createBuilder(NetConstant.GET_ALIPAY_AUTH_URL)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                doResponseError(serverResponse.getCode(), serverResponse.getMessage());
                if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL) {
                    String result = (String) serverResponse.getResult();
                    ThreadUtils.executeByCached(new ThreadUtils.SimpleTask<Object>() {
                        @Nullable
                        @Override
                        public Object doInBackground() {
                            // 构造AuthTask 对象
                            AuthTask authTask = new AuthTask(ThirdAccountActivity.this);
                            // 调用授权接口，获取授权结果
                           return authTask.authV2(result, true);
                        }

                        @Override
                        public void onSuccess(@Nullable Object result) {
                            AliPayAuthResultEntity authResult = new AliPayAuthResultEntity((Map<String, String>) result, true);
                            String resultStatus = authResult.getResultStatus();

                            // 判断resultStatus 为“9000”且result_code为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                            if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                                bindThirdAccount(authResult.getAlipayOpenId(), "alipay");
                            } else {
                                // 其他状态值则为授权失败
                                ToastUtils.showShort(authResult.getMemo());
                            }
                        }
                    });
                }
            }
        });
    }

    private void bindThirdAccount(String openid, String oauth){
        Map<String, String> params = new HashMap<>();
        params.put("openid", openid);
        params.put("oauth", oauth);
        HttpInvoker.createBuilder(NetConstant.BIND_THIRD_ACCOUNT_URL)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setParams(params)
                .setClz(MineCardEntity.class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                doResponseError(serverResponse.getCode(), serverResponse.getMessage());
                if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL) {
                    ToastUtils.showShort("绑定成功");
                    finish();
                }
            }
        });
    }
}
