package com.qubuyer.business.login.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.tabs.TabLayout;
import com.qubuyer.R;
import com.qubuyer.base.AM;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.login.LoginEntity;
import com.qubuyer.bean.login.ThirdLoginEntity;
import com.qubuyer.bean.login.WechatLoginEntity;
import com.qubuyer.business.login.adapter.LoginPagerAdapter;
import com.qubuyer.business.login.presenter.LoginPresenter;
import com.qubuyer.business.login.view.ILoginView;
import com.qubuyer.business.login.view.LoginAccountViewPage;
import com.qubuyer.business.login.view.LoginBaseViewPage;
import com.qubuyer.business.login.view.LoginPhoneViewPage;
import com.qubuyer.business.register.activity.BindingActivity;
import com.qubuyer.business.register.activity.SetPwdActivity;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.constant.NetConstant;
import com.qubuyer.customview.AbsToolbar;
import com.qubuyer.customview.BrowserActivity;
import com.qubuyer.customview.EnhanceTabLayout;
import com.qubuyer.customview.ViewPagerWrapContentHeight;
import com.qubuyer.utils.NavigationUtil;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginView {

    @BindView(R.id.etl_tab)
    EnhanceTabLayout etl_tab;
    @BindView(R.id.vp_page)
    ViewPagerWrapContentHeight vp_page;
    @BindView(R.id.tv_protocol)
    TextView tv_protocol;

    private LoginPagerAdapter mViewPagerAdapter;

    //选中的绑定第三方类型 0:微信 1:QQ
    private int mSelectedBindType = -1;
    //选中的第三方openid
    private String mSelectedBindOpenId;
    private BaseUiListener mListener;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_login;
    }

    @Override
    protected LoginPresenter createP(Context context) {
        return new LoginPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        BarUtils.setStatusBarLightMode(this, true);
        mListener = new BaseUiListener();
        initTabAndViewPager();
        SpanUtils.with(tv_protocol).append("未注册的手机号验证后将自动创建趣买买账号，登录即代表您已经同意").setFontSize(ConvertUtils.dp2px(13)).setForegroundColor(ContextCompat.getColor(this, R.color.black))
                .append("《趣买买用户隐私政策》").setFontSize(ConvertUtils.dp2px(13)).setForegroundColor(ContextCompat.getColor(this, android.R.color.holo_blue_light))
                .create();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mSelectedBindType == 0) {
            String openId = SPUtils.getInstance().getString(AppConstant.WECHAT_OPEN_ID);
            if (!TextUtils.isEmpty(openId)) {
                mSelectedBindOpenId = openId;
                mPresenter.wecahtOrQQLogin("wx", openId);
                SPUtils.getInstance().remove(AppConstant.WECHAT_OPEN_ID);
            }
        }
    }

    @Override
    public AbsToolbar toolbar() {
        return null;
    }

    @OnClick({R.id.iv_close, R.id.ll_wechat_login, R.id.ll_qq_login, R.id.tv_protocol})
    public void onClickByButterKnfie(View v) {
        switch (v.getId()) {
            case R.id.iv_close: //关闭页面
                finish();
                break;
            case R.id.ll_wechat_login: //微信登录
                mSelectedBindType = 0;
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "snsapi_login_lnint";
                AM.mWxApi.sendReq(req);
//                loginWechat();
                break;
            case R.id.ll_qq_login: //QQ登录
                mSelectedBindType = 1;
                //如果session不可用，则登录，否则说明已经登录
                AM.mTencent.login(this, "all", new BaseUiListener());
                break;
            case R.id.tv_protocol: //协议
                Intent intent = new Intent(this, BrowserActivity.class);
                intent.putExtra(BrowserActivity.KEY_URL, NetConstant.BASE_URL + "/home/html/helpCenterDetail?id=44");
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mViewPagerAdapter) {
            mViewPagerAdapter.getPageList().get(0).destory();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    private void initTabAndViewPager() {
        ArrayList<String> titleList = new ArrayList<>();
        titleList.add("账号密码登录");
        titleList.add("手机快捷登录");
        List<LoginBaseViewPage> pageList = new ArrayList<>();
        LoginBaseViewPage page2 = new LoginAccountViewPage(this);
        page2.getView();
        LoginBaseViewPage page1 = new LoginPhoneViewPage(this);
        page1.getView();
        pageList.add(page2);
        pageList.add(page1);
        for (int i = 0; i < titleList.size(); i++) {
            etl_tab.addTab(titleList.get(i));
        }
        mViewPagerAdapter = new LoginPagerAdapter(this, pageList);
        vp_page.setAdapter(mViewPagerAdapter);
        vp_page.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(etl_tab.getTabLayout()));
        etl_tab.setupWithViewPager(vp_page);
        vp_page.setCurrentItem(0);
    }

    @Override
    public void onShowLoginResultToView(LoginEntity result) {

    }

    @Override
    public void showWechatOrQQLoginSuccessView(ThirdLoginEntity entity) {
        finish();
    }

    @Override
    public void showWechatOrQQLoginNoBindView(ThirdLoginEntity result) {
        HashMap map = new HashMap();
        map.put("bind_type", mSelectedBindType);
        map.put("bind_openid", result.getOpenid());
        NavigationUtil.forward(this, BindingActivity.class, map);
    }

    @Override
    public void showWechatOrQQLoginToSetPwdView(ThirdLoginEntity result) {
        NavigationUtil.forward(this, SetPwdActivity.class);
    }

    @Override
    public void showWechatOrQQLoginBindFailView(ThirdLoginEntity result) {

    }

    @Override
    public void showWechatOrQQLoginRegisterSuccessView(ThirdLoginEntity result) {

    }

    @Override
    public void onShowVerificationcodeResultToView(boolean result) {

    }

    private class BaseUiListener implements IUiListener {
        //这个类需要实现三个方法 onComplete（）：登录成功需要做的操作写在这里
        // onError onCancel 方法具体内容自己搜索
        public void onComplete(Object response) {
            if (null == response) {
                ToastUtils.showShort("返回为空, 登录失败");
                return;
            }
            JSONObject jb = (JSONObject) response;
            if (jb.length() == 0) {
                ToastUtils.showShort("返回为空, 登录失败");
                return;
            }
            try {
                String openID = jb.getString("openid");  //openid用户唯一标识
                String access_token = jb.getString("access_token");
                String expires = jb.getString("expires_in");
                if (!TextUtils.isEmpty(access_token) && !TextUtils.isEmpty(expires) && !TextUtils.isEmpty(openID)) {
                    mSelectedBindOpenId = openID;
                    AM.mTencent.setOpenId(openID);
                    AM.mTencent.setAccessToken(access_token, expires);
                    mPresenter.wecahtOrQQLogin("qq", openID);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onError(UiError uiError) {
            ToastUtils.showShort(uiError.errorMessage);
        }

        @Override
        public void onCancel() {

        }
    }

    private void loginWechat() {
        Platform platform = ShareSDK.getPlatform(Wechat.NAME);
        //回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
        platform.setPlatformActionListener(new PlatformActionListener() {

            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {
            }

            @Override
            public void onComplete(Platform platform, int action, HashMap<String, Object> arg2) {
                // TODO Auto-generated method stub
                //输出所有授权信息
                long loginTime = System.currentTimeMillis();
                //用户资源都保存到res
                //通过打印res数据看看有哪些数据是你想要的
                PlatformDb platDB = platform.getDb();//获取数平台数据DB
                //通过DB获取各种数据
                String token = platDB.getToken();
                String userGender = platDB.getUserGender();
                String userIcon = platDB.getUserIcon();
                String userId = platDB.getUserId();
                String userName = platDB.getUserName();
                String exportData = platDB.exportData();
                WechatLoginEntity weiXinBean = GsonUtils.fromJson(exportData, WechatLoginEntity .class);
                mSelectedBindOpenId = weiXinBean.getOpenid();
                mPresenter.wecahtOrQQLogin("wx",  weiXinBean.getUnionid());
                SPUtils.getInstance().remove(AppConstant.WECHAT_OPEN_ID);
            }

            @Override
            public void onCancel(Platform arg0, int arg1) {

            }
        });
        platform.showUser(null);
        platform.removeAccount(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, mListener);
        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Constants.REQUEST_LOGIN) {
                Tencent.handleResultData(data, mListener);
            }
        }
    }
}
