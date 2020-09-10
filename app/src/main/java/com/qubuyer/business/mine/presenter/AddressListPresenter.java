package com.qubuyer.business.mine.presenter;

import com.blankj.utilcode.util.ToastUtils;
import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.mine.MineAddressEntitiy;
import com.qubuyer.business.mine.model.AddressListModel;
import com.qubuyer.business.mine.model.IAddressListModel;
import com.qubuyer.business.mine.view.IAddressListView;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;

import java.util.List;
import java.util.Map;

/**
 * @author jiangtianming
 * @date 2018/12/19
 * @description 我的收货地址列表Presenter类
 */
public class AddressListPresenter extends WrapperPresenter<IAddressListView> implements IAddressListPresenter, HttpCallback {

    private IAddressListModel addressListModel;

    public AddressListPresenter() {
        this.addressListModel = new AddressListModel(this);
        attachModel(addressListModel);
    }

    /**
     * 获取列表
     */
    @Override
    public void requestAdressList(String page, String page_size) {
        addressListModel.loadAddressList(page, page_size, this);
    }

    /**
     * 删除列表单个
     */
    @Override
    public void delAddress(String addressId) {
        addressListModel.delAddress(addressId, this);
    }

    @Override
    public void setDefAddress(String addressId) {
        addressListModel.setDefAddress(addressId,this);
    }

    @Override
    public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL) {
            if (requestUrl.equals(NetConstant.ADDRESS_LIST_URL)) {
                List<MineAddressEntitiy> addressListEntitiy = (List<MineAddressEntitiy>) serverResponse.getResult();
                mView.setAddressView(addressListEntitiy);
            } else if (requestUrl.equals(NetConstant.ADDRESS_DEL_URL)) {
                mView.loadListView();
            } else if (requestUrl.equals(NetConstant.ADDRESS_DEFULT_URL)) {
                mView.loadListView();
            }
        }else{
            ToastUtils.showShort(serverResponse.getMessage());
        }
    }
}
