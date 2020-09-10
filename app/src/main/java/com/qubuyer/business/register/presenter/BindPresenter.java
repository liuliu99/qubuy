package com.qubuyer.business.register.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.register.RegisterEntity;
import com.qubuyer.business.register.model.BindModel;
import com.qubuyer.business.register.model.IBindModel;
import com.qubuyer.business.register.view.IBindView;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.utils.SessionUtil;
import com.qubyer.okhttputil.helper.HttpManager;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

public class BindPresenter extends WrapperPresenter<IBindView> implements IBindPresenter {
    private IBindModel mModel;

    public BindPresenter() {
        mModel = new BindModel(this);
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
        mView.onShowVerificationcodeResultToView(serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL);
    }

    @Override
    public void bindPhone(String mobile, String code, String openid, String oauth) {
        mView.showLoading();
        mModel.bindPhone(mobile, code, openid, oauth);
    }

    @Override
    public void onBindPhone(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == AppConstant.CODE_GO_SET_PWD && null != serverResponse.getResult()) {
            mView.onShowBindPhoneSetPwdToView((RegisterEntity) serverResponse.getResult());
        } else if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            RegisterEntity entity = (RegisterEntity) serverResponse.getResult();
            SessionUtil.getInstance().setToken(entity.getToken());
            HttpManager.getInstance().init(entity.getToken(), SessionUtil.getInstance().getTokenOverduedListener());
            mView.onShowBindPhoneResultToView(entity);
        } else {
            mView.onShowBindPhoneResultToView(null);
        }
    }
}
