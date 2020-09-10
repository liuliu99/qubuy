package com.qubuyer.business.register.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.business.register.model.ForgetModel;
import com.qubuyer.business.register.model.IForgetModel;
import com.qubuyer.business.register.view.IForgetView;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

public class ForgetPresenter extends WrapperPresenter<IForgetView> implements IForgetPresenter {
    private IForgetModel mModel;

    public ForgetPresenter() {
        mModel = new ForgetModel(this);
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
    public void findPwd(String mobile, String password, String code, String password2) {
        mView.showLoading();
        mModel.findPwd(mobile, password, code, password2);
    }

    @Override
    public void onFindPwd(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        mView.onShowFindPwdResultToView(serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL);
    }
}
