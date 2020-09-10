package com.qubuyer.business.register.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.register.RegisterEntity;
import com.qubuyer.business.register.model.ISetPwdModel;
import com.qubuyer.business.register.model.SetPwdModel;
import com.qubuyer.business.register.view.ISetPwdView;
import com.qubuyer.utils.SessionUtil;
import com.qubyer.okhttputil.helper.HttpManager;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

public class SetPwdPresenter extends WrapperPresenter<ISetPwdView> implements ISetPwdPresenter {
    private ISetPwdModel mModel;

    public SetPwdPresenter() {
        mModel = new SetPwdModel(this);
        attachModel(mModel);
    }

    @Override
    public void setPwd(String token, String pushId, String password, String password2) {
        mView.showLoading();
        mModel.setPwd(token, pushId, password, password2);
    }

    @Override
    public void onSetPwd(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            RegisterEntity entity = (RegisterEntity) serverResponse.getResult();
            SessionUtil.getInstance().setToken(entity.getToken());
            HttpManager.getInstance().init(entity.getToken(), SessionUtil.getInstance().getTokenOverduedListener());
            mView.onShowSetPwdResultToView((RegisterEntity) serverResponse.getResult());
        } else {
            mView.onShowSetPwdResultToView(null);
        }
    }
}
