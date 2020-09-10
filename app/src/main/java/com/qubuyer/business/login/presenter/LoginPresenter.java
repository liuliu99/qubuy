package com.qubuyer.business.login.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.login.LoginEntity;
import com.qubuyer.bean.login.ThirdLoginEntity;
import com.qubuyer.business.login.model.ILoginModel;
import com.qubuyer.business.login.model.LoginModel;
import com.qubuyer.business.login.view.ILoginView;
import com.qubuyer.utils.SessionUtil;
import com.qubyer.okhttputil.helper.HttpManager;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

public class LoginPresenter extends WrapperPresenter<ILoginView> implements ILoginPresenter {
    private ILoginModel mModel;

    public LoginPresenter() {
        mModel = new LoginModel(this);
        attachModel(mModel);
    }

    @Override
    public void login(String phone, String password) {
        mView.showLoading();
        mModel.login(phone, password);
    }

    @Override
    public void loginWithPhoneCode(String phone, String code) {
        mView.showLoading();
        mModel.loginWithPhoneCode(phone, code);
    }

    @Override
    public void onLogin(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            LoginEntity entity = (LoginEntity) serverResponse.getResult();
            SessionUtil.getInstance().setToken(entity.getToken());
            HttpManager.getInstance().init(entity.getToken(), SessionUtil.getInstance().getTokenOverduedListener());
            mView.onShowLoginResultToView((LoginEntity) serverResponse.getResult());
        } else {
            mView.onShowLoginResultToView(null);
        }
    }

    @Override
    public void wecahtOrQQLogin(String oauth, String openId) {
        mView.showLoading();
        mModel.wecahtOrQQLogin(oauth, openId);
    }

    @Override
    public void onWechatOrQQLogin(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        switch (serverResponse.getCode()) {
            case 101: //用户未绑定
                mView.showWechatOrQQLoginNoBindView((ThirdLoginEntity) serverResponse.getResult());
                break;
            case 102: //去设置密码
                mView.showWechatOrQQLoginToSetPwdView((ThirdLoginEntity) serverResponse.getResult());
                break;
            case 100: //登陆成功
            case 200: //登录成功
                ThirdLoginEntity entity = (ThirdLoginEntity) serverResponse.getResult();
                SessionUtil.getInstance().setToken(entity.getToken());
                HttpManager.getInstance().init(entity.getToken(), SessionUtil.getInstance().getTokenOverduedListener());
                mView.showWechatOrQQLoginSuccessView((ThirdLoginEntity) serverResponse.getResult());
                break;
            case 110: //绑定失败
                mView.showWechatOrQQLoginBindFailView((ThirdLoginEntity) serverResponse.getResult());
                break;
            case 105: //注册成功
                mView.showWechatOrQQLoginRegisterSuccessView((ThirdLoginEntity) serverResponse.getResult());
                break;
        }
    }

    @Override
    public void getVerificationcode(String phone) {
        mView.showLoading();
        mModel.getVerificationcode(phone);
    }

    @Override
    public void onGetVerificationcode(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        mView.onShowVerificationcodeResultToView(serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL);
    }
}
