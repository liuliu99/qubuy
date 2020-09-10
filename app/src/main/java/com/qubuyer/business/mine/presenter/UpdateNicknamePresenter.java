package com.qubuyer.business.mine.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.business.mine.model.IUpdateNicknameModel;
import com.qubuyer.business.mine.model.UpdateNicknameModel;
import com.qubuyer.business.mine.view.IUpdateNicknameView;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

public class UpdateNicknamePresenter extends WrapperPresenter<IUpdateNicknameView> implements IUpdateNicknamePresenter {
    private IUpdateNicknameModel mModel;

    public UpdateNicknamePresenter() {
        mModel = new UpdateNicknameModel(this);
        attachModel(mModel);
    }

    @Override
    public void updateNickname(String nickname) {
        mView.showLoading();
        mModel.updateNickname(nickname);
    }

    @Override
    public void onUpdateNickName(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        mView.onShoUpdateNicknameResultToView(serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL);
    }
}
