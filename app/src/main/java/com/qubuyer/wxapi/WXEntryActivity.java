package com.qubuyer.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.qubuyer.R;
import com.qubuyer.base.AM;
import com.qubuyer.constant.AppConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import java.util.Map;

/**
 * 微信登录页面
 *
 * @author jiangtianming
 * @date 2019/1/4
 * @description
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler, HttpCallback {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AM.mWxApi.handleIntent(this.getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        AM.mWxApi.handleIntent(intent, this);//必须调用此句话
    }

    //微信发送的请求将回调到onReq方法
    @Override
    public void onReq(BaseReq req) {
    }

    //发送到微信请求的响应结果
    @Override
    public void onResp(BaseResp resp) {
        ToastUtils.setBgResource(R.drawable.shape_toast_bg);
        ToastUtils.setMsgColor(getResources().getColor(R.color.white));
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        if (resp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) { //分享
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    ToastUtils.showShort("分享成功!");
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    ToastUtils.showShort("分享取消!");
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    ToastUtils.showShort("分享拒绝!");
                    break;
            }
            // 关闭页面
            finish();
        } else if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) { //登录
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    //发送成功
                    SendAuth.Resp sendResp = (SendAuth.Resp) resp;
                    String code = sendResp.code;
                    SPUtils.getInstance().put(AppConstant.WECHAT_OPEN_ID, code);
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    //发送取消
                    ToastUtils.showShort("发送取消");
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    //发送被拒绝
                    ToastUtils.showShort("发送被拒绝");
                    finish();
                    break;
                default:
                    //发送返回
                    break;
            }
        }
    }

    @Override
    public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
//        if (serverResponse.getStatus() == 0) {
//            LoginInfoEntitiy loginInfo = (LoginInfoEntitiy) serverResponse.getResult();
//            SPUtils.getInstance().put("wx_code", "");
//            if (!StringUtils.isEmpty(loginInfo.getWechat_user_token())) {
//                NavigationUtil.overlay(this, LoginActivity.class);
//                SPUtils.getInstance().put("wechat_token", loginInfo.getWechat_user_token());
//                finish();
//            } else {
//                DataManager.getInstance().setLoginInfo(loginInfo);
//                if (loginInfo != null) {
//                    HttpManager.getInstance().init(loginInfo.getToken(), DataManager.getInstance().getTokenOverduedListener());
//                }
//                EventBus.getDefault().post(new LoginResultEvent());
//                finish();
//            }
//        }
    }
}