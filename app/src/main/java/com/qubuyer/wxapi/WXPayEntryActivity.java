package com.qubuyer.wxapi;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.qubuyer.R;
import com.qubuyer.bean.event.WechatPayStatusEvent;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.utils.DialogUtil;
import com.qubuyer.utils.EventBusUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wechat_pay_result);
        api = WXAPIFactory.createWXAPI(this, AppConstant.WECHAT_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
                EventBusUtil.sendEvent(new WechatPayStatusEvent(1));
                finish();
                return;
            }
            if (resp.errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
                EventBusUtil.sendEvent(new WechatPayStatusEvent(2));
                ToastUtils.showShort(R.string.wechat_cancel_operation);
                finish();
                return;
            }
            DialogUtil.getMessageDialog(this, "", resp.errStr, "确定", false, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EventBusUtil.sendEvent(new WechatPayStatusEvent(2));
                    finish();
                }
            }).show();
        }
    }
}