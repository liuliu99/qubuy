package com.qubuyer.business.good.presenter;

import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.good.GoodSOResultEntity;
import com.qubuyer.bean.good.SOGoodInfoEntity;
import com.qubuyer.bean.payment.CalcOrderPriceResultEntity;
import com.qubuyer.business.good.model.ISOModel;
import com.qubuyer.business.good.model.SOModel;
import com.qubuyer.business.good.view.ISOView;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

public class SOPresenter extends WrapperPresenter<ISOView> implements ISOPresenter {
    private ISOModel mModel;

    public SOPresenter() {
        mModel = new SOModel(this);
        attachModel(mModel);
    }

    public void calcOrderPrice(int type, String address_id, String goods_id, String goods_num, String item_id){
        mView.showLoading();
        mModel.calcOrderPrice(type, address_id, goods_id, goods_num, item_id);
    }

    public void onCalcOrderPrice(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowCalcOrderPriceResultToView((CalcOrderPriceResultEntity) serverResponse.getResult());
        } else {
            mView.onShowCalcOrderPriceResultToView(null);
        }
    }

    @Override
    public void getSOInfo(int type, String goodId, int count, String itemId) {
        mView.showLoading();
        mModel.getSOInfo(type, goodId, count, itemId);
    }

    @Override
    public void onGetSOInfo(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowSOGoodInfoToView((SOGoodInfoEntity) serverResponse.getResult());
        } else {
            mView.onShowSOGoodInfoToView(null);
        }
    }

    @Override
    public void submitOrder(int type, String address_id, String invoice_type, String invoice_title, String invoice_taxpayer, String invoice_name, String invoice_desc, String invoice_email, String user_note, String goods_id, String goods_num, String item_id, String invoice_mobile) {
        mView.showLoading();
        mModel.submitOrder(type, address_id, invoice_type, invoice_title, invoice_taxpayer, invoice_name, invoice_desc, invoice_email, user_note, goods_id, goods_num, item_id, invoice_mobile);
    }

    @Override
    public void onSubmitOrder(ServerResponse serverResponse) {
        mView.hideLoading();
        mView.doResponseError(serverResponse.getCode(), serverResponse.getMessage());
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && null != serverResponse.getResult()) {
            mView.onShowSubmitOrderResultToView((GoodSOResultEntity) serverResponse.getResult());
        } else {
            mView.onShowSubmitOrderResultToView(null);
        }
    }
}
