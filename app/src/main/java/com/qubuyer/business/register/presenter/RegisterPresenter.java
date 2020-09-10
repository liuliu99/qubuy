package com.qubuyer.business.register.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.register.RegisterEntity;
import com.qubuyer.business.register.model.IRegisterModel;
import com.qubuyer.business.register.model.RegisterModel;
import com.qubuyer.business.register.view.IRegisterView;
import com.qubuyer.utils.SessionUtil;
import com.qubyer.okhttputil.helper.HttpManager;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

public class RegisterPresenter extends WrapperPresenter<IRegisterView> implements IRegisterPresenter {
    private IRegisterModel mModel;

    public RegisterPresenter() {
        mModel = new RegisterModel(this);
        attachModel(mModel);
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
        mView.onShowVerificationcodeResultToView(serverResponse.getCode() == 200);
    }

    @Override
    public void register(String username, String password, String mobile_code, String invitation_code) {
        mView.showLoading();
        mModel.register(username, password, mobile_code, invitation_code);
    }

    @Override
    public void onRegister(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            RegisterEntity entity = (RegisterEntity) serverResponse.getResult();
            SessionUtil.getInstance().setToken(entity.getToken());
            HttpManager.getInstance().init(entity.getToken(), SessionUtil.getInstance().getTokenOverduedListener());
            mView.onShowResiterResultToView(entity);
        } else {
            mView.onShowResiterResultToView(null);
        }
    }
}
