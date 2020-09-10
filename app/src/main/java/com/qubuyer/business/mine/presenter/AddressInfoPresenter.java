package com.qubuyer.business.mine.presenter;

import com.blankj.utilcode.util.ToastUtils;
import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.address.CityData;
import com.qubuyer.bean.address.DistrictData;
import com.qubuyer.bean.address.ProvinceData;
import com.qubuyer.bean.mine.MineAddressEntitiy;
import com.qubuyer.business.mine.model.AddressInfoModel;
import com.qubuyer.business.mine.model.IAddressInfoModel;
import com.qubuyer.business.mine.view.IAddressInfoView;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.ServerResponse;
import com.qubyer.okhttputil.utils.HttpConstant;
import com.ywp.addresspickerlib.YwpAddressBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author jiangtianming
 * @date 2018/12/19
 * @description 地址添加和修改Presenter类
 */
public class AddressInfoPresenter extends WrapperPresenter<IAddressInfoView> implements IAddressInfoPresenter, HttpCallback {

    private IAddressInfoModel addressInfoModel;

    public AddressInfoPresenter() {
        this.addressInfoModel = new AddressInfoModel(this);
        attachModel(addressInfoModel);
    }

    /**
     * 新增新的地址
     *
     * @param info
     */
    @Override
    public void requestNewAddress(MineAddressEntitiy info) {
        addressInfoModel.addNewAddress(info, this);
    }

    /**
     * 请求修改地址
     *
     * @param info
     */
    @Override
    public void requestUpdateAddress(MineAddressEntitiy info) {
        addressInfoModel.updateAddress(info, this);
    }

    @Override
    public void delAddress(String addressId) {
        addressInfoModel.delAddress(addressId, this);
    }

    @Override
    public void getAddressInfo(String addressId) {
        addressInfoModel.getAddressInfo(addressId, this);
    }

    @Override
    public void getAddressSelectList() {
        mView.showLoading();
        addressInfoModel.getAddressSelectList(this);
    }

    @Override
    public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
        if (serverResponse.getCode() == HttpConstant.Status.SERVER_STATUS_SUCCESSFUL) {
            if (requestUrl.equals(NetConstant.ADDRESS_ADD_URL)) {
                ToastUtils.showShort("操作成功");
                mView.oprateSucess();
            } else if (requestUrl.equals(NetConstant.ADDRESS_EDIT_URL)) {
                ToastUtils.showShort("操作成功");
                mView.oprateSucess();
            } else if (requestUrl.equals(NetConstant.ADDRESS_DEL_URL)) {
                ToastUtils.showShort("操作成功");
                mView.oprateSucess();
            } else if (requestUrl.equals(NetConstant.ADDRESS_INFO_URL)) {
                MineAddressEntitiy entitiy = (MineAddressEntitiy) serverResponse.getResult();
                mView.setAddressDeatailView(entitiy);
            } else if (requestUrl.equals(NetConstant.ADDRESS_SELECT_LIST_URL)) {
                List<ProvinceData> entitiys = (List<ProvinceData>) serverResponse.getResult();
                List<YwpAddressBean.AddressItemBean> province = new ArrayList<>();
                List<YwpAddressBean.AddressItemBean> city = new ArrayList<>();
                List<YwpAddressBean.AddressItemBean> district = new ArrayList<>();
                for (ProvinceData provinceData : entitiys) {
                    YwpAddressBean.AddressItemBean pbean = new YwpAddressBean.AddressItemBean();
                    pbean.setI(provinceData.getId() + "");
                    pbean.setN(provinceData.getName());
                    pbean.setP(provinceData.getParent_id() + "");
                    province.add(pbean);
                    if (null != provinceData.getChilder()) {
                        for (CityData cityData : provinceData.getChilder()) {
                            YwpAddressBean.AddressItemBean cbean = new YwpAddressBean.AddressItemBean();
                            cbean.setI(cityData.getId() + "");
                            cbean.setN(cityData.getName());
                            cbean.setP(cityData.getParent_id() + "");
                            city.add(cbean);
                            if (null != cityData.getChilder()) {
                                for (DistrictData districtData : cityData.getChilder()) {
                                    YwpAddressBean.AddressItemBean dbean = new YwpAddressBean.AddressItemBean();
                                    dbean.setI(districtData.getId() + "");
                                    dbean.setN(districtData.getName());
                                    dbean.setP(districtData.getParent_id() + "");
                                    district.add(dbean);
                                }
                            }
                        }
                    }
                }
                YwpAddressBean bean = new YwpAddressBean();
                bean.setProvince(province);
                bean.setCity(city);
                bean.setDistrict(district);
                mView.setAddressSelectView(bean);
            }
        } else {
            ToastUtils.showShort(serverResponse.getMessage());
        }
        mView.hideLoading();
    }
}
